package org.mrdarkimc.modules.SoundModule;

import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.mrdarkimc.modules.BaseModule;

import java.util.Objects;

public class SoundDispatcher implements BaseModule {
    public void playGlobalSound(World world){

    }
    public static void playSoundToPlayerInRadius(Location location, int radius, Sound sound, float volume, float pitch){
        location.getWorld().getNearbyEntities(location, radius, radius, radius)
                .stream()
                .filter(e -> e instanceof Player)
                .map(e -> ((Player) e).getPlayer())
                .filter(Objects::nonNull)
                .forEach(p -> {
            p.playSound(p, sound,volume,pitch);
        });
    }
    public static void playSoundToPlayer(Player player, Sound sound, float volume, float pitch){
        player.playSound(player.getLocation(),sound,volume,pitch);
    }

    @Override
    public void startModule() {

    }

    @Override
    public void killModule() {

    }
}
