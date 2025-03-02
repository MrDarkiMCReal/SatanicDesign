package org.mrdarkimc;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.mrdarkimc.Utils.SoundDispatcher;

public class CommandList implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if (commandSender instanceof Player player){
            if (!player.hasPermission("redmc.admin"))
                return false;
        }
        // /design rSound world 10 5 5 5 sound 0.2 2
        switch (strings[0]){
            case "rSound":
                World world = Bukkit.getWorld(strings[1]);
                int x = Integer.parseInt(strings[2]);
                int y = Integer.parseInt(strings[3]);
                int z = Integer.parseInt(strings[4]);

                int radius = Integer.parseInt(strings[5]);

                Sound sound = Sound.valueOf(strings[6]);
                float volume = Float.parseFloat(strings[7]);
                float pitch = Float.parseFloat(strings[8]);
                Location loc =  new Location(world,x,y,z);

                SoundDispatcher.playSoundToPlayerInRadius(loc,radius,sound,volume,pitch);
                return true;
            default:
                commandSender.sendMessage("    ");
                commandSender.sendMessage("    SatanicUtils аргументы: ");
                commandSender.sendMessage("  rSound <world> x y z r sound volume pitch");
                commandSender.sendMessage("    ");
                return true;
        }
    }
}
