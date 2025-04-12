package org.mrdarkimc.modules.MobsModule;

public class Demon {
    private int minLevel;
    private int maxLevel;

    public Demon(int minLevel, int maxLevel) {
        this.minLevel = minLevel;
        this.maxLevel = maxLevel;
    }

    public int getMinLevel() {
        return minLevel;
    }

    public int getMaxLevel() {
        return maxLevel;
    }
}
