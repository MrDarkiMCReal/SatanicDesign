package org.mrdarkimc.config;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.mrdarkimc.SatanicDesign;

import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Config {
    File file;
    FileConfiguration config;
    public void loadConfiguration(){
        this.file = new File(SatanicDesign.getInstance().getDataFolder(), "config.yml");
        if (!this.file.exists()) {
            SatanicDesign.getInstance().saveResource("config.yml", false);
        }
        this.config = YamlConfiguration.loadConfiguration(file);
    }
    public FileConfiguration get(){
        return this.config;
    }
    public static String translateHex(String message) {
        Pattern pattern = Pattern.compile("&#[0-9A-Fa-f]{6}");
        Matcher matcher = pattern.matcher(message);
        while (matcher.find()) {
            String color = message.substring(matcher.start(), matcher.end());
            message = message.replace(color, net.md_5.bungee.api.ChatColor.of(color.replaceAll("&", "")) + "");
            matcher = pattern.matcher(message);
        }
        return ChatColor.translateAlternateColorCodes('&', message);
    }
}
