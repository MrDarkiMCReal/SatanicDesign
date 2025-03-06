package org.mrdarkimc.modules.DeathCoords;

import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.mrdarkimc.SatanicDesign;

public class DeathResetter extends BukkitRunnable {
    private DeathCoords coords;

    private Player player;
    private int x;
    private int z;

    public int getX() {
        return x;
    }

    public int getZ() {
        return z;
    }

    private DeathResetter() {
    }
    public static DeathResetter init(DeathCoords coord, Player player, int x, int z){
        var res = new DeathResetter();
        res.coords = coord;
        res.player = player;
        res.x = x;
        res.z = z;
        res.runTaskLater(SatanicDesign.getInstance(),coord.getUpdatetime());
        return res;
    }

    @Override
    public void run() {
        coords.getDeaths().remove(player);
    }
}
