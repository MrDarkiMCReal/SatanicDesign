package org.mrdarkimc.modules.BossBar;

import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.mrdarkimc.PAPI.playerformatter.Cache;
import org.mrdarkimc.SatanicDesign;
import org.mrdarkimc.SatanicLib.Utils;
import org.mrdarkimc.modules.BaseModule;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BossBarHandler extends BukkitRunnable implements BaseModule, Listener {
    private Map<Player, List<BossBar>> bars;
    String path = "modules.bossbar";
    boolean second_layer;
    int updatetime;
    private static BossBar emptyBossBar = Bukkit.createBossBar(" ", BarColor.YELLOW, BarStyle.SOLID);

    @Override
    public void startModule() {
        Bukkit.getServer().getPluginManager().registerEvents(this, SatanicDesign.getInstance());
        var conf = SatanicDesign.getInstance().getConfig();
        bars = new HashMap<>();
        updatetime = conf.getInt(path + ".update", 100);
        second_layer = conf.getBoolean(path + ".enable_second_layer", false);
        var players = Bukkit.getOnlinePlayers();
        if (!players.isEmpty()){
            players.forEach(this::addplayer);
        }
        runTaskTimer(SatanicDesign.getInstance(), updatetime, updatetime);
    }

    @EventHandler
    void onJoin(PlayerJoinEvent e) {
        addplayer(e.getPlayer());
    }
    @EventHandler
    void onQuit(PlayerQuitEvent e){
        removeplayer(e.getPlayer());
    }

    public void addplayer(Player player) {
        bars.put(player, createBossBar(player)).forEach(element -> element.addPlayer(player));
    }
    void removeplayer(Player player) {
        bars.remove(player).forEach(BossBar::removeAll);
    }


    public List<BossBar> createBossBar(Player player) {
        List<BossBar> list = new ArrayList<>();
        if (SatanicDesign.getInstance().getConfig().getBoolean(path + ".enable_second_layer"))
            list.add(emptyBossBar);
        String group = Cache.getGroup(player);
        String bossbarLine = SatanicDesign.getInstance().getConfig().getString(path + ".titles." + group, "&cОшибка боссбара.");
        bossbarLine = bossbarLine.replace("{nickname}", player.getName());
        bossbarLine = PlaceholderAPI.setPlaceholders(player, bossbarLine);
        bossbarLine = Utils.translateHex(bossbarLine);
        list.add(Bukkit.createBossBar(bossbarLine, BarColor.YELLOW, BarStyle.SOLID));
        return list;
    }

    public void updateBossBar() {
        bars.forEach((k,v) -> {
            String group = Cache.getGroup(k);
            String bossbarLine = SatanicDesign.getInstance().getConfig().getString(path + ".titles." + group,"&cОшибка. Не найдена группа игрока или игрок");
            bossbarLine = bossbarLine.replace("{nickname}",k.getName());
            bossbarLine = PlaceholderAPI.setPlaceholders(k, bossbarLine);
            bossbarLine = Utils.translateHex(bossbarLine);
            for (BossBar bossBar : v) {
                bossBar.setTitle(bossbarLine);
            }
        });
    }

    @Override
    public void killModule() {
        cancel();
        bars.forEach((k, v) -> v.forEach(BossBar::removeAll));
        bars.clear();
    }

    @Override
    public void run() {
        updateBossBar();
    }
}
