package org.mrdarkimc.Commands;

import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.mrdarkimc.SatanicDesign;
import org.mrdarkimc.SatanicLib.Utils;
import org.mrdarkimc.modules.CustomItems.ItemsModule;
import org.mrdarkimc.modules.SoundModule.SoundDispatcher;

public class CommandService {
    public static void playSoundInRadius(String... commandArgs) {
        World world = Bukkit.getWorld(commandArgs[1]);
        int x = Integer.parseInt(commandArgs[2]);
        int y = Integer.parseInt(commandArgs[3]);
        int z = Integer.parseInt(commandArgs[4]);

        int radius = Integer.parseInt(commandArgs[5]);

        Sound sound = Sound.valueOf(commandArgs[6]);
        float volume = Float.parseFloat(commandArgs[7]);
        float pitch = Float.parseFloat(commandArgs[8]);
        Location loc = new Location(world, x, y, z);

        SoundDispatcher.playSoundToPlayerInRadius(loc, radius, sound, volume, pitch);
    }

    //usage /sutil tp
    public static void teleport(String... commandArgs) {
        //check time (2 минуты)
        World world = Bukkit.getWorld(commandArgs[1]);
        int x = Integer.parseInt(commandArgs[2]);
        int y = Integer.parseInt(commandArgs[3]);
        int z = Integer.parseInt(commandArgs[4]);
    }

    public static void sendmessage(String... commandArgs) {
        //usage /sutil sendmessage player_name message
        String playername = commandArgs[1];
        Player player = Bukkit.getPlayer(playername);
        if (player==null)
            return;
        if (commandArgs.length < 3) {
            player.sendMessage(" ");
            return;
        }
        StringBuilder builder = new StringBuilder();
        for (int i = 1; i < commandArgs.length; i++) {
            builder.append(commandArgs[i]);
            builder.append(" ");
        }
        String message = PlaceholderAPI.setPlaceholders(player,builder.toString());
        message = Utils.translateHex(message);
        player.sendMessage(message);
    }
    public static ItemStack giveItem(String... commandArgs) {
        //usage /sutil give <player> <itemid>
        Player player = Bukkit.getPlayer(commandArgs[1]);
        if (player==null)
            return null;
        String item = commandArgs[2];
        ItemStack stack = null;
        switch (item){
            case "reveal_dust":
                stack = SatanicDesign.getInstance().getModules().getItems().getRevealDust();
                break;
            case "desorient":
                stack = SatanicDesign.getInstance().getModules().getItems().getDesorient();
                break;
        }
        if (stack!=null){
            player.getInventory().addItem(stack);
        }else {
            Bukkit.getLogger().info(" ");
            Bukkit.getLogger().info("[SatanicDesign] Игрок " + player + " не смог получить предмет: " + item);
            Bukkit.getLogger().info(" ");
        }
        return stack;
    }

    public static void randomTask(String... commandArgs) {
        //usage /sutil randomtask <task name> <player>
        //check time (2 минуты)
        String task = commandArgs[1];
        String playername = commandArgs[2];
        Player player = Bukkit.getPlayer(playername);
        if (player == null) {
            Bukkit.getLogger().info("[SatanicDesign] Рандом таск: не найден игрок" + playername + " Таск: " + task);
            return;
        }
        var randomizer = SatanicDesign.getInstance().getModules().getRandomizer();
        randomizer.executeTask(task, player);
    }
}
