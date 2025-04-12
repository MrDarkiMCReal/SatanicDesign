package org.mrdarkimc.modules.CustomItems.Utils;

import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.scheduler.BukkitRunnable;
import org.mrdarkimc.SatanicDesign;
import org.mrdarkimc.SatanicLib.Utils;

import java.util.HashMap;
import java.util.Map;


public abstract class BasicItem implements Listener {
    public String item = "DefaultItem";
    public int cooldown = 45;
    private NamespacedKey key;
    Map<Player, Long> cooldowns;


    public BasicItem(String item, int cooldown, NamespacedKey key) {
        this.item = item;
        this.key = key;
        this.cooldown = cooldown;
        this.cooldowns = new HashMap<>();
    }

    public abstract void useItemFor(Player player);


    @EventHandler
    public void onItemUse(PlayerInteractEvent e) {
        if (e.getHand() == null)
            return;
        if (!e.getHand().equals(EquipmentSlot.HAND))
            return;

        if (e.getItem() != null && e.getItem().getItemMeta() != null && e.getItem().getItemMeta().getPersistentDataContainer().has(key)) {
            if (!hasCooldown(e.getPlayer())) {
                useItemFor(e.getPlayer());
                applyCooldown(e.getPlayer());
                e.getPlayer().setCooldown(e.getItem().getType(),cooldown * 20);
                return;
            }
            e.getPlayer().sendMessage(" ");
            e.getPlayer().sendMessage(Utils.translateHex("  &#CFCFCFЭтот предмет &#FF0048перезаряжается &#CFCFCFеще %s &#CFCFCFсекунд"
                    .formatted((
                            cooldown-((System.currentTimeMillis()-cooldowns.get(e.getPlayer()))/1000)
                    ))));
            e.getPlayer().sendMessage(" ");
        }

    }

    public void applyCooldown(Player player) {
        cooldowns.put(player,System.currentTimeMillis());
        new BukkitRunnable() {

            @Override
            public void run() {
                cooldowns.remove(player);
            }
        }.runTaskLater(SatanicDesign.getInstance(), cooldown * 20L);
    }

    public boolean hasCooldown(Player player) {
        return cooldowns.containsKey(player);
    }


}
