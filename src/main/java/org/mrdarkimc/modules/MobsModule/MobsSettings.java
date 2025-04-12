package org.mrdarkimc.modules.MobsModule;

import org.bukkit.Bukkit;
import org.bukkit.Location;

import java.util.HashMap;
import java.util.Map;

public class MobsSettings {
    private HashMap<Integer, Integer> maxLevelPerRadius;
    private double damageModifier;
    private double healthModifier;
    private Map<String, Location> worldCenters;

    public double getDamageModifier() {
        return damageModifier;
    }

    public double getHealthModifier() {
        return healthModifier;
    }

    private MobsSettings() {
    }

    public static MobsSettings init() {
        var settings = new MobsSettings();
        settings.initWorlds();
        settings.initLevelMap();
        settings.damageModifier = MobsModule.getConfig().get().getDouble("mobs.levelModifiers.damage");
        settings.healthModifier = MobsModule.getConfig().get().getDouble("mobs.levelModifiers.health");
        return settings;
    }

    public Location getLocationByWorld(String world) {
        return worldCenters.getOrDefault(world, new Location(Bukkit.getWorld(world), 100, 100, 100));
    }

    public void initWorlds() {
        worldCenters = new HashMap<>();
        var section = MobsModule.getConfig().get().getConfigurationSection("center");

        section.getKeys(false).forEach(key -> {
            int x = section.getInt(key + ".x");
            int y = section.getInt(key + ".x");
            int z = section.getInt(key + ".x");
            worldCenters.computeIfAbsent(key, (k) -> new Location(Bukkit.getWorld(k), x, y, z));
        });
    }

    public void initLevelMap() {
        maxLevelPerRadius = new HashMap<>();
        var section = MobsModule.getConfig().get().getConfigurationSection("mobs.maxLevelPerRadius");
        section.getKeys(false).forEach(key -> {
            maxLevelPerRadius.computeIfAbsent(Integer.valueOf(key), (k) -> section.getInt(String.valueOf(k)));
        });
    }

//    public int getMaxLevel(int radius) {
//        Integer key = maxLevelPerRadius.floorKey(radius);
//        if (key == null) {
//            key = maxLevelPerRadius.firstKey();
//        }
//        return maxLevelPerRadius.get(key);
//    }

    public Demon getDemonTypeByRadius(int radius) {
        int key = 2000;
        int minLevel = 1;
        for (Integer integer : maxLevelPerRadius.keySet()) {
            if (radius < integer) {
                key = integer;
                break;
            }
            minLevel = integer;
        }
        int maxLevel = maxLevelPerRadius.getOrDefault(key,100);

        return new Demon(minLevel,maxLevel);
    }

}
