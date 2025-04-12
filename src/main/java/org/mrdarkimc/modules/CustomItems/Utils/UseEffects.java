package org.mrdarkimc.modules.CustomItems.Utils;

import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.scheduler.BukkitRunnable;
import org.mrdarkimc.modules.SoundModule.SoundDispatcher;

public class UseEffects extends BukkitRunnable {
    Particle particle;
    Location loc;
    double radiusMin;
    double radiusMax;
    int amount;

    public UseEffects(Particle particle, Location loc, int radius, int amount) {
        this.particle = particle;
        this.loc = loc;
        this.radiusMin = 2;
        this.radiusMax = radius;
        this.amount = amount;
    }

    @Override
    public void run() {
//        if (radiusMin >= radiusMax)
//            cancel();
        double anglestep = 2 * Math.PI/amount;
        for (int i = 0; i < amount; i++) {
            double angle = i * anglestep;
            double offsetX = radiusMin * Math.cos(angle);
            double offsetZ = radiusMin * Math.sin(angle);
            loc.getWorld().spawnParticle(Particle.FLAME, loc.getX() + offsetX, loc.getY(), loc.getZ() + offsetZ, 0, offsetX/2.5, 0, offsetZ/2.5);
        }
        SoundDispatcher.playSoundToPlayerInRadius(loc, (int) (radiusMax+10), Sound.ENTITY_GHAST_SHOOT, 0.25F, 1);
//        radiusMin = radiusMin+0.5;

    }
}
