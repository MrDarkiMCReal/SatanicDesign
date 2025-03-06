package org.mrdarkimc.modules.DeathCoords;

import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.NotNull;
import org.mrdarkimc.SatanicDesign;
import org.mrdarkimc.SatanicLib.Utils;
import org.mrdarkimc.SatanicLib.messages.KeyedMessage;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ThreadLocalRandom;

public class BackRTP implements CommandExecutor {
    private DeathCoords coords;

    public BackRTP(DeathCoords coords) {
        this.coords = coords;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        //usage /backrtp
        if (commandSender instanceof Player player) {
            if (!coords.getDeaths().containsKey(player))
                return true;
            var task = coords.getDeaths().get(player);
            int x = task.getX();
            int z = task.getZ();
            Bukkit.getLogger().info("[SatanicDesign] Начинаю задачу /backrtp ");
            execute(player,x,z);
            Bukkit.getLogger().info("[SatanicDesign] Команда завершена ");
        }
        return true;
    }
    public void execute(Player player, int x, int z) {
        int bal = Integer.parseInt(PlaceholderAPI.setPlaceholders(player, "%coinsengine_balance_raw_sapphires%"));
        if (bal <= coords.getPrice()) {
            new KeyedMessage(player, "modules.death-coords.deny", Map.of("%diff%", String.valueOf(coords.getPrice() - bal))).send();
            return;
        }
        Bukkit.getLogger().info("[SatanicDesign] У игрока есть деньги. Начинаю поиск локации /backrtp ");
        findLocationAsync(player, x, z);
    }
    public void findLocationAsync(Player player, int x, int z) {
        CompletableFuture.supplyAsync(() -> {
            return findLocation(x, z);
        }).thenAccept(loc -> {
            Bukkit.getLogger().info("[SatanicDesign] Завершено. /backrtp ");
            if (loc == null) {
                teleportFailure(player);
            } else {
                new BukkitRunnable(){

                    @Override
                    public void run() {
                        player.teleport(loc);
                        new KeyedMessage(player, "modules.death-coords.success", Map.of(
                                "%x%", String.valueOf((int) loc.getX()),
                                "%y%", String.valueOf((int) loc.getY()),
                                "%z%", String.valueOf((int) loc.getZ()))).send();
                        String cmd = SatanicDesign.getInstance().getConfig().getString("modules.death-coords.command").replace("%player_name%", player.getName());
                        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), cmd);
                        if (coords.getDeaths().containsKey(player)){
                            var task = coords.getDeaths().remove(player);
                            task.cancel();
                        }
                    }
                }.runTask(SatanicDesign.getInstance());

            }
        });
    }
    void teleportFailure(Player player){
        new KeyedMessage(player,"modules.death-coords.not-found",null).send();

    }

    public Location findLocation(int x, int z) {
        int radius = 500;
        for (int i = 0; i < 20; i++) { // 20 попыток для поиска
            Bukkit.getLogger().info("[SatanicDesign] локация №" + i);
            int xf = ThreadLocalRandom.current().nextInt(x - radius, x + radius);
            int zf = ThreadLocalRandom.current().nextInt(z - radius, z + radius);
            int yf = Bukkit.getWorld("world").getHighestBlockAt(xf, zf).getY();

            if (is3x3Place(xf, yf, zf)) {
                return new Location(Bukkit.getWorld("world"), xf, yf+1, zf); // Возвращаем найденную локацию
            }
        }
        return null;
    }
    public boolean is3x3Place(int x, int y, int z) {
        var blocked = List.of(Material.AIR, Material.LAVA, Material.WATER);
        Bukkit.getWorld("world").getBlockAt(x,y,z);
        for (int dx = -1; dx <= 1; dx++) {
            for (int dz = -1; dz <= 1; dz++) {
                Block block = Bukkit.getWorld("world").getBlockAt(x + dx, y, z + dz);
                Bukkit.getLogger().info("Проверяю блок по коордам " + (x + dx) + (y) + (z + dz));
                Bukkit.getLogger().info("Материал " + block.getType().toString());
                if (block == null || blocked.contains(block.getType())) {
                    return false;  // Если блок не твердый или это пустой блок, локация не подходит
                }
            }
        }

        // Если все блоки в области твердые
        return has3x3WithAirAbove(x,y,z);
    }
    public boolean has3x3WithAirAbove(int x, int y, int z) {
        var allowed = List.of(Material.AIR);
        y=y+1;
        Bukkit.getWorld("world").getBlockAt(x,y,z);
        for (int dx = -1; dx <= 1; dx++) {
            for (int dz = -1; dz <= 1; dz++) {
                Block block = Bukkit.getWorld("world").getBlockAt(x + dx, y, z + dz);
                Bukkit.getLogger().info("Проверяю блок по коордам " + (x + dx) + (y) + (z + dz));
                Bukkit.getLogger().info("Материал " + block.getType().toString());
                if (block == null || block.getType()!=Material.AIR) {
                    return false;  // Если блок не твердый или это пустой блок, локация не подходит
                }
            }
        }

        // Если все блоки в области твердые
        return true;
    }
}
