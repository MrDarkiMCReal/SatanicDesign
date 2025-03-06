package org.mrdarkimc.modules.DeathCoords;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.mrdarkimc.SatanicDesign;
import org.mrdarkimc.SatanicLib.TagBuilderGetter;
import org.mrdarkimc.SatanicLib.messages.KeyedMessage;
import org.mrdarkimc.modules.BaseModule;

import java.util.HashMap;
import java.util.Map;


public class DeathCoords implements Listener, BaseModule {
    private Map<Player, DeathResetter> deaths;
    String path = "modules.death-coords";
    private int radius;
    private int price;
    private long updatetime;

    public DeathCoords() {
    }

    @EventHandler
    void ondeath(PlayerDeathEvent e) {
        Location loc = e.getEntity().getLocation();

        removeDueNewDeath(e.getEntity());
        if (loc.getWorld().getName().equals("world")) {
            addPlayerToDeathList(e.getEntity());
            sendDeathMessage(e.getEntity(), e.getEntity().getLocation());
            return;
        } else {
            new KeyedMessage(e.getEntity(), "modules.death-coords.message", Map.of(
                    "%x%", String.valueOf((int) loc.getX()),
                    "%y%", String.valueOf((int) loc.getY()),
                    "%z%", String.valueOf((int) loc.getZ())
            )).send();
        }
    }

    public Map<Player, DeathResetter> getDeaths() {
        return deaths;
    }

    public long getUpdatetime() {
        return updatetime;
    }

    @Override
    public void startModule() {
        deaths = new HashMap<>();
        radius = SatanicDesign.getInstance().getConfig().getInt(path + ".radius");
        price = SatanicDesign.getInstance().getConfig().getInt(path + ".price");
        updatetime = SatanicDesign.getInstance().getConfig().getInt(path + ".time_in_ticks");
        Bukkit.getServer().getPluginManager().registerEvents(this, SatanicDesign.getInstance());
        Bukkit.getPluginCommand("backrtp").setExecutor(new BackRTP(this));

    }

    @Override
    public void killModule() {
        HandlerList.unregisterAll(this);

        //todo unregister command executor
    }

    void addPlayerToDeathList(Player player) {
        var loc = player.getLocation();
        int x = (int) loc.getX();
        int z = (int) loc.getZ();
        var resetter = DeathResetter.init(this,player, x, z);
        deaths.put(player,resetter);
    }
    public void removeDueNewDeath(Player player){
        if (deaths.containsKey(player)) {
            deaths.remove(player).cancel();
        }
    }

    void sendDeathMessage(Player player, Location loc) {;
        var cmp = TagBuilderGetter.get(player, "backRTP", Map.of(
                "%x%", String.valueOf((int) loc.getX()),
                "%y%", String.valueOf((int) loc.getY()),
                "%z%", String.valueOf((int) loc.getZ()),
                "%radius%", String.valueOf(radius),
                "%price%", String.valueOf(price)
        ));
        player.spigot().sendMessage(cmp);
    }

    public int getRadius() {
        return radius;
    }



    public int getPrice() {
        return price;
    }
}
