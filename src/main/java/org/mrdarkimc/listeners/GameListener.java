package org.mrdarkimc.listeners;

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
import org.mrdarkimc.SatanicLib.messages.KeyedMessage;

import java.util.Map;

public class GameListener {

    public void startPvpPrepareTimer(){

        players.forEach(bossBar::addPlayer);

        task = new BukkitRunnable() {
            int timeRemaining = prepareTime;

            @Override
            public void run() {
                bossBar.setTitle(Utils.translateHex(prepareText.replace("{time}", String.valueOf(timeRemaining))));
                bossBar.setProgress((double) timeRemaining / prepareTime);

                if (timeRemaining <= 0) {
                    bossBar.removeAll();
                    cancel();
                    if (!checkPlayersEquipment()) {
                        players1.engageMode = false;
                        players2.engageMode = false;
                        arena.disengage();
                        cancel();
                    }
                    if (EnhancedDuels.engageMode.checkAlreadyEngage(players1.owner)){
                        new KeyedMessage(players1.owner,"messages.cantAcceptPvp2",null).send();
                        new KeyedMessage(players2.owner,"messages.cantAcceptPvp1", Map.of("{player}",players1.owner.getName())).send();
                        players1.engageMode = false;
                        players2.engageMode = false;
                        arena.disengage();
                        cancel();
                        return;
                    }

                    if (EnhancedDuels.engageMode.checkAlreadyEngage(players2.owner)){
                        new KeyedMessage(players1.owner,"messages.cantAcceptPvp1",Map.of("{player}",players2.owner.getName())).send();
                        new KeyedMessage(players2.owner,"messages.cantAcceptPvp2",null).send();
                        players1.engageMode = false;
                        players2.engageMode = false;
                        arena.disengage();
                        cancel();
                        return;
                    }
                    phase = DuelGamePhase.initialize(arena, players1, players2, players);
                    cancel();
                }
                timeRemaining--;
            }
        }.runTaskTimer(EnhancedDuels.getInstance(), 0L, 20L);
    }
}
