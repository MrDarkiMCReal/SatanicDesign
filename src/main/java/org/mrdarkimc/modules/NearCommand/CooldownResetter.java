package org.mrdarkimc.modules.NearCommand;

import org.bukkit.scheduler.BukkitRunnable;

import java.util.UUID;

public class CooldownResetter extends BukkitRunnable {
    private UUID key;
    private CooldownHandler cooldownHandler;
    long starttime;

    public long getTimeStart() {
        return starttime;
    }

    public CooldownResetter(UUID key, CooldownHandler cooldownHandler) {
        this.key = key;
        this.cooldownHandler = cooldownHandler;
        starttime = System.currentTimeMillis();
    }

    @Override
    public void run() {
        cooldownHandler.getCooldowns().remove(key);
    }
}
