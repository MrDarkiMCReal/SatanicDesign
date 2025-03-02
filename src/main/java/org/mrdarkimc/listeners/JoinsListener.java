package org.mrdarkimc.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.mrdarkimc.wrappers.WrapperHandler;

public class JoinsListener implements Listener {
    @EventHandler
    void onjoin(PlayerJoinEvent e) {
        WrapperHandler.addPlayer(e.getPlayer());
    }

    @EventHandler
    void onquit(PlayerQuitEvent e) {
        WrapperHandler.removePlayer(e.getPlayer());
    }
}
