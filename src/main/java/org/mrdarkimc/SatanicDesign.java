package org.mrdarkimc;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.mrdarkimc.PAPI.StaticPlaceholders;
import org.mrdarkimc.PAPI.dFormat;
import org.mrdarkimc.PAPI.playerformatter.Cache;
import org.mrdarkimc.PAPI.playerformatter.PlayerFormat;
import org.mrdarkimc.SatanicLib.SatanicLib;
import org.mrdarkimc.SatanicLib.Utils;
import org.mrdarkimc.SatanicLib.VaultHandler;
import org.mrdarkimc.SatanicLib.configsetups.Configs;

public class SatanicDesign extends JavaPlugin implements Listener {
    private static SatanicDesign instance;

    public static SatanicDesign getInstance() {
        return instance;
    }

    private Configs config;
    public VaultHandler vaultHandler;

    @Override
    public void onDisable() {
    }

    @Override
    public void onEnable() {
        SatanicLib.setupLib(this);
        Utils.startUp("SatanicDesign private");
        instance = this;
        config = Configs.Defaults.setupConfig();
        setupPapi();
        vaultHandler = new VaultHandler();
        vaultHandler.setupVault();
        getServer().getPluginCommand("sutil").setExecutor(new CommandList());
        getServer().getPluginManager().registerEvents(new Cache(), this);
        ReloadDetector.fillWrapper();
    }

    void setupPapi() {
        Bukkit.getLogger().info(" ");
        Bukkit.getLogger().info("  [SatanicDesign] Загружаю 3 расширения...");
        new dFormat().register();
        new StaticPlaceholders().register();
        new PlayerFormat().register();
        Bukkit.getLogger().info("  [SatanicDesign] Выполнено");
    }

    public FileConfiguration getConfig() {
        return config.get();
    }

}
