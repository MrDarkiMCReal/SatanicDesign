package org.mrdarkimc.modules.MobsModule;

import org.bukkit.Bukkit;
import org.mrdarkimc.SatanicDesign;
import org.mrdarkimc.SatanicLib.configsetups.Configs;
import org.mrdarkimc.modules.BaseModule;

public class MobsModule implements BaseModule {
    private static Configs config;

    public static Configs getConfig() {
        return config;
    }
    static {
       config = new Configs("mobs");
    }
    @Override
    public void startModule() {
        Bukkit.getServer().getPluginManager().registerEvents(new MobSpawnListener(), SatanicDesign.getInstance());
    }

    @Override
    public void killModule() {
    }
}
