package org.mrdarkimc.modules.PromoModule;

import org.bukkit.Bukkit;
import org.mrdarkimc.SatanicLib.configsetups.Configs;
import org.mrdarkimc.modules.BaseModule;

public class PromoModules implements BaseModule {
    private static Configs config;

    public static Configs getConfig() {
        return config;
    }

    @Override
    public void startModule() {
        CommandService service = new CommandService(this);
        config = new Configs("promo");
        Bukkit.getPluginCommand("promo").setExecutor(new PromoCommand(service));
    }

    @Override
    public void killModule() {
    config = null;
    }
}
