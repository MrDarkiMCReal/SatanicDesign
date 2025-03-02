package org.mrdarkimc.Utils;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.mrdarkimc.SatanicDesign;
import org.mrdarkimc.SatanicLib.messages.KeyedMessage;

import java.util.Map;


public class DeathCoords implements Listener {
    public DeathCoords() {
        Bukkit.getServer().getPluginManager().registerEvents(this, SatanicDesign.getInstance());
    }
    @EventHandler
    void ondeath(PlayerDeathEvent e){
        Location loc = e.getEntity().getLocation();
        if (!e.getEntity().hasPermission("satanic.deathcoords"))
            return;
        new KeyedMessage(e.getEntity(),"modules.death-coords.message", Map.of(
                "%x%",String.valueOf(loc.getX()),
                "%y%",String.valueOf(loc.getY()),
                "%z%",String.valueOf(loc.getZ())
        ));
    }
}
