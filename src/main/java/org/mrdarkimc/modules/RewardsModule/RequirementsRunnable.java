package org.mrdarkimc.modules.RewardsModule;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.List;
import java.util.Objects;


public class RequirementsRunnable extends BukkitRunnable {
    long delay;
    Location loc;
    int radius;
    List<String> commands;

    @Override
    public void run() {
        loc.getWorld().getNearbyEntities(loc, radius, radius, radius).stream()
                .filter(e -> e instanceof Player)
                .map(e -> ((Player) e).getPlayer())
                .filter(Objects::nonNull)
                .forEach(p -> {
                    commands.forEach(cmd -> Bukkit.dispatchCommand(Bukkit.getConsoleSender(), cmd.replace("{player}", p.getName())));

                });
    }
    
}
