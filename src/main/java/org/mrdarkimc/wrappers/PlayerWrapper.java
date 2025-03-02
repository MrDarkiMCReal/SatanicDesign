package org.mrdarkimc.wrappers;

import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;
import org.mrdarkimc.PAPI.playerformatter.Cache;
import org.mrdarkimc.SatanicDesign;
import org.mrdarkimc.SatanicLib.Utils;
import org.mrdarkimc.SatanicLib.objectManager.interfaces.Reloadable;
import org.mrdarkimc.Utils.BossBarHandler;


public class PlayerWrapper implements Reloadable {

    private Player player;
    private BossBar bossBar;
    public Player getPlayer(){
        return player;
    }

    public BossBar getBossBar() {
        return bossBar;
    }

    public PlayerWrapper(Player player) {
        this.player = player;
        Reloadable.register(this);
        createBossBar();
        bossBar.addPlayer(player);
    }
    public void createBossBar(){
        bossBar = BossBarHandler.createBossBar(this.player);
//        String group = Cache.getGroup(player);
//        String bossbarLine = SatanicDesign.getInstance().getConfig().getString("bossbar." + group,"&cОшибка боссбара.");
//        bossbarLine = PlaceholderAPI.setPlaceholders(player,bossbarLine);
//        bossbarLine = Utils.translateHex(bossbarLine);
//        bossBar = Bukkit.createBossBar(bossbarLine, BarColor.YELLOW, BarStyle.SOLID);
    }
    public void updateBossBar(){
        BossBarHandler.updateBossBarFor(this);
//        String group = Cache.getGroup(player);
//        String bossbarLine = SatanicDesign.getInstance().getConfig().getString("bossbar." + group,"&cОшибка. Не найдена группа игрока или игрок");
//        bossbarLine = PlaceholderAPI.setPlaceholders(player,bossbarLine);
//        bossbarLine = Utils.translateHex(bossbarLine);
//        bossBar.setTitle(bossbarLine);
    }




    @Override
    public void reload() {
    updateBossBar();
    }
}

