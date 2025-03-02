package org.mrdarkimc.Utils;

import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.entity.Player;

public class SoundDispatcher {
    public static void playGlobalSound(World world){

    }
    public static void playSoundToPlayerInRadius(Location location, int radius, Sound sound, float volume, float pitch){
        location.getWorld().getNearbyEntities(location,radius,radius,radius).stream().filter(e -> e instanceof Player).map(e -> ((Player) e).getPlayer()).forEach(p -> {
            p.playSound(p, sound,volume,pitch);
        });
    }
}
