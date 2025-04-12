package org.mrdarkimc.modules.nightvision;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginCommand;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffectType;
import org.jetbrains.annotations.NotNull;
import org.mrdarkimc.SatanicLib.messages.KeyedMessage;
import org.mrdarkimc.modules.BaseModule;


public class NightVisionModule implements BaseModule, CommandExecutor {
    @Override
    public void startModule() {
        PluginCommand cmd = Bukkit.getPluginCommand("nightvision");
        cmd.setExecutor(this);
    }

    @Override
    public void killModule() {
        //todo
    }

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if (commandSender instanceof Player player){
            if (player.hasPotionEffect(PotionEffectType.NIGHT_VISION)){
                player.removePotionEffect(PotionEffectType.NIGHT_VISION);
                new KeyedMessage(player,"modules.nightvision.off", null).send();
                return true;
            }
            player.addPotionEffect(PotionEffectType.NIGHT_VISION.createEffect(Integer.MAX_VALUE, Integer.MAX_VALUE));
            new KeyedMessage(player,"modules.nightvision.on", null).send();
        }
        return true;
    }
}
