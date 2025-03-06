package org.mrdarkimc.PAPI.playerformatter;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.mrdarkimc.SatanicLib.VaultHandler;

import java.util.HashMap;
import java.util.Map;

public class Cache {
    private static final Map<Player, String> cache = new HashMap<>();
    public static Map<Player,String> getCache(){
        return cache;
    }

    public static String getGroup(Player player) {
        return VaultHandler.getChat().getPrimaryGroup(player);
        //cache.computeIfAbsent(player, k -> VaultHandler.getChat().getPrimaryGroup(player));
    }
}
