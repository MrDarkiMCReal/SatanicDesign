package org.mrdarkimc;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.mrdarkimc.wrappers.WrapperHandler;

public class ReloadDetector {
    public static void fillWrapper(){
        boolean reloaded = false;
        if (Bukkit.getOnlinePlayers().isEmpty())
            return;
        for (Player player : Bukkit.getOnlinePlayers()) {
            if (WrapperHandler.getWPlayer(player) == null) {
                WrapperHandler.addPlayer(player);
                if (!reloaded){
                    reloaded = true;
                }
            }
        }
        if (reloaded){
            Bukkit.getLogger().info("  ");
            Bukkit.getLogger().info("  ");
            Bukkit.getLogger().info("[SatanicDesign] Обранужена перезагрузка через plugman");
            Bukkit.getLogger().info("[SatanicDesign] Перезагрузка через plugman может сломать фичи");
            Bukkit.getLogger().info("[SatanicDesign] Нужно использовать /design reload");
            Bukkit.getLogger().info("  ");
            Bukkit.getLogger().info("  ");
        }
    }
}
