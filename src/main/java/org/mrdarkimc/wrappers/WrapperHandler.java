package org.mrdarkimc.wrappers;

import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WrapperHandler {
    private static Map<Player, PlayerWrapper> onlinePlayers = new HashMap<>();
    public static void addPlayer(Player player){
        onlinePlayers.put(player, new PlayerWrapper(player));
    }
    public static void removePlayer(Player player){
        onlinePlayers.remove(player);
    }
    public static PlayerWrapper getWPlayer(Player player){
        return onlinePlayers.get(player);
    }
    public static List<PlayerWrapper> getPlayers(){
        return new ArrayList<>(onlinePlayers.values());
    }
}
