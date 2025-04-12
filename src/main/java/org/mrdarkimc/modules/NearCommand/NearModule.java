package org.mrdarkimc.modules.NearCommand;

import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.mrdarkimc.PAPI.playerformatter.Cache;
import org.mrdarkimc.SatanicDesign;
import org.mrdarkimc.SatanicLib.Utils;
import org.mrdarkimc.SatanicLib.messages.KeyedMessage;
import org.mrdarkimc.modules.BaseModule;

import java.util.*;

public class NearModule implements BaseModule, CommandExecutor {
    String path = "modules.near";
    private RadarService service;

    public static int getCooldownInSeconds() {
        return SatanicDesign.getInstance().getConfig().getInt("modules.near.cooldown", 120);
    }

    @Override
    public void startModule() {
        Bukkit.getLogger().info("Запускаю модуль /near");
        Bukkit.getPluginCommand("near").setExecutor(this);
        service = new RadarService();
        String penus = "123";

    }

    @Override
    public void killModule() {
//todo
    }

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        switch (strings.length) {
            case 0:
                handleEmptyNear((Player) commandSender);
                return true;
            case 1:
                handleRangedNear((Player) commandSender, strings[0]);
                return true;
            case 2:
                handleRangedAndSkipNear((Player) commandSender, strings[0]);
                return true;
            default:
                new KeyedMessage((Player) commandSender, path + ".messages.usage", null).send();
                return true;
        }
    }

    void handleEmptyNear(Player player) {
        String group = Cache.getGroup(player);
        int radius = SatanicDesign.getInstance().getConfig().getInt("modules.near.groups." + group, 100);


        if (service.hasCooldown(player)) {
            service.sendCooldownMessage(player, radius);
            return;
        }
        service.setCooldown(player);
        List<Player> list = service.getNearPlayers(player, radius);
        if (list.isEmpty()) {
            service.notFoundMessage(player, group, radius);
            return;
        }

        String playerlist = service.buildPlayerListMessage(player, list);
        new KeyedMessage(player, path + ".messages.playersInRadius", Map.of("{radius}", String.valueOf(radius))).send();
        player.sendMessage(playerlist);
        player.sendMessage(" ");
    }

    void handleRangedNear(Player player, String range) {
        String group = Cache.getGroup(player);
        int radius = service.findRange(group, range);

        if (service.hasCooldown(player)) {
            service.sendCooldownMessage(player, radius);
            return;
        }
        service.setCooldown(player);
        List<Player> list = service.getNearPlayers(player, radius);
        if (list.isEmpty()) {
            service.notFoundMessage(player, group, radius);
            return;
        }
        String playerlist = service.buildPlayerListMessage(player, list);
        new KeyedMessage(player, path + ".messages.playersInRadius", Map.of("{radius}", String.valueOf(radius))).send();
        player.sendMessage(playerlist);
        player.sendMessage(" ");
    }

    void handleRangedAndSkipNear(Player player, String range) {
        String group = Cache.getGroup(player);
        int radius = service.findRange(group, range);
        if (service.hasCooldown(player)) {
            if (!checkBal(player))
                return;
            service.cooldowns.remove(player.getUniqueId());
            int requiredPrice = SatanicDesign.getInstance().getConfig().getInt(path + ".skipPrice", 15);
            String cmd = SatanicDesign.getInstance().getConfig().getString(path + ".command").replace("%player_name%", player.getName());
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), cmd);
            new KeyedMessage(player,path + ".messages.paid",Map.of("{price}", String.valueOf(requiredPrice))).send();
        }
        service.setCooldown(player);

        List<Player> list = service.getNearPlayers(player, radius);
        if (list.isEmpty()) {
            service.notFoundMessage(player, group, radius);
            return;
        }
        String playerlist = service.buildPlayerListMessage(player, list);
        new KeyedMessage(player, path + ".messages.playersInRadius", Map.of("{radius}", String.valueOf(radius))).send();
        player.sendMessage(playerlist);
        player.sendMessage(" ");
    }

    boolean checkBal(Player player) {
        int bal = Integer.parseInt(PlaceholderAPI.setPlaceholders(player, "%coinsengine_balance_raw_sapphires%"));
        int requiredPrice = SatanicDesign.getInstance().getConfig().getInt(path + ".skipPrice", 15);
        if (bal < requiredPrice) {
            new KeyedMessage(player, path + ".messages.notEnoughMoney", Map.of("%diff%", String.valueOf(requiredPrice - bal))).send();
            return false;
        }
        return true;
    }
}
