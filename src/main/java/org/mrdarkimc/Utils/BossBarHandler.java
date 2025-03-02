package org.mrdarkimc.Utils;

import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.mrdarkimc.PAPI.playerformatter.Cache;
import org.mrdarkimc.SatanicDesign;
import org.mrdarkimc.SatanicLib.Utils;
import org.mrdarkimc.wrappers.PlayerWrapper;
import org.mrdarkimc.wrappers.WrapperHandler;

public class BossBarHandler extends BukkitRunnable {
    String path = "modules.bossbar";
    public void updateTask(){
        long time = SatanicDesign.getInstance().getConfig().getInt(path + ".update");
    runTaskTimer(SatanicDesign.getInstance(),time,time);
    }

    @Override
    public void run() {
        WrapperHandler.getPlayers().forEach(BossBarHandler::updateBossBarFor);
    }
    public void kill(){
        cancel();
    }
    public static BossBar createBossBar(Player player){
        String group = Cache.getGroup(player);
        String bossbarLine = SatanicDesign.getInstance().getConfig().getString("bossbar." + group,"&cОшибка боссбара.");
        bossbarLine = PlaceholderAPI.setPlaceholders(player,bossbarLine);
        bossbarLine = Utils.translateHex(bossbarLine);
        return Bukkit.createBossBar(bossbarLine, BarColor.YELLOW, BarStyle.SOLID);
    }
    public static void updateBossBarFor(PlayerWrapper wrapper){
        String group = Cache.getGroup(wrapper.getPlayer());
        String bossbarLine = SatanicDesign.getInstance().getConfig().getString("bossbar." + group,"&cОшибка. Не найдена группа игрока или игрок");
        bossbarLine = PlaceholderAPI.setPlaceholders(wrapper.getPlayer(), bossbarLine);
        bossbarLine = Utils.translateHex(bossbarLine);
        wrapper.getBossBar().setTitle(bossbarLine);
    }
}
