package org.mrdarkimc.Commands;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.mrdarkimc.modules.SoundModule.SoundDispatcher;

public class CommandList implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if (commandSender instanceof Player player){
            if (!player.hasPermission("redmc.admin"))
                return false;
        }
        // /sutil rSound world 10 5 5 5 sound 0.2 2
        switch (strings[0]){
            case "rSound":
                CommandService.playSoundInRadius(strings);
                return true;
            case "back":
                World world = Bukkit.getWorld(strings[1]);
                int x = Integer.parseInt(strings[2]);
                int y = Integer.parseInt(strings[3]);
                int z = Integer.parseInt(strings[4]);
            case "randomtask": //usage /sutil randomtask <task name> <player>
                CommandService.randomTask(strings);
                return true;
            case "sendmessage": //usage /sutil sendmessage <player> <message>
                CommandService.sendmessage(strings);
                return true;
            case "playsound": //usage /sutil playsound <player> sound volume pitch
                if (strings.length < 5)
                    return true;
                Player pl = Bukkit.getPlayer(strings[1]);
                if (pl==null)
                    return true;
                Sound sound = Sound.valueOf(strings[2]);
                if (sound==null)
                    return true;
                float volume = Float.parseFloat(strings[3]);
                float pitch = Float.parseFloat(strings[4]);
                SoundDispatcher.playSoundToPlayer(pl,sound,volume,pitch);
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
