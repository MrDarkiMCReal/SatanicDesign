package org.mrdarkimc.modules.RewardsModule;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.mrdarkimc.SatanicDesign;

public class GlobalRunnable extends BukkitRunnable {
    long delay;
    String command;

    public GlobalRunnable(long delay, String command) {
        this.delay = delay;
        this.command = command;
    }
   public void start(){
        runTaskTimer(SatanicDesign.getInstance(),delay,delay);
    }

    @Override
    public void run() {
        for (Player player : Bukkit.getServer().getOnlinePlayers()) {
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(),command.replace("{player}",player.getName()));
        }
    }
}
