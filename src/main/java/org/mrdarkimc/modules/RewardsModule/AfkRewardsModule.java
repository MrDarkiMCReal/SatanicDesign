package org.mrdarkimc.modules.RewardsModule;

import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;
import org.mrdarkimc.modules.BaseModule;

public class AfkRewardsModule implements BaseModule {
    @Override
    public void startModule() {
        BukkitRunnable vr = new BukkitRunnable(){

            @Override
            public void run() {

            }
        };
        Runnable rn = () -> Bukkit.getLogger().info("Runnable with lambda");
        Runnable rnn = new Runnable() {
            @Override
            public void run() {

            }
        };

    }

    @Override
    public void killModule() {

    }
}
