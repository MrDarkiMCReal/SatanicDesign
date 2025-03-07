package org.mrdarkimc.modules.CustomItems.Disorientation;

import org.bukkit.Location;
import org.bukkit.NamespacedKey;
import org.bukkit.Particle;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.mrdarkimc.SatanicDesign;
import org.mrdarkimc.modules.CustomItems.Utils.BasicItem;
import org.mrdarkimc.modules.CustomItems.Utils.UseEffects;

import java.util.Objects;

public class Disorientation extends BasicItem {
    int radius;
    int duration;
    public Disorientation(String item,NamespacedKey key, int cooldown, int radius, int duration) {
        super(item, cooldown, key);
        this.radius = radius;
        this.duration = duration;
    }
//todo задефать эксепшены при попытке использовать предмет, если он выкл
    @Override
    public void useItemFor(Player player) {
        Location loc = player.getLocation();
        loc.getWorld().getNearbyEntities(loc,radius,radius,radius).stream()
                .filter(e -> e instanceof Player)
                .map(e -> ((Player) e).getPlayer())
                .filter(Objects::nonNull)
                .filter(en -> !en.equals(player))
                .forEach(this::applyEffectToEnemy);
        playVisualEffects(player.getLocation());
    }
    public void applyEffectToEnemy(Player player){
        player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS,duration * 20,5));
        player.addPotionEffect(new PotionEffect(PotionEffectType.SLOWNESS,duration * 20, 1));
    }
    void playVisualEffects(Location loc){
        new UseEffects(Particle.FLAME, loc,radius,500).runTask(SatanicDesign.getInstance());
    }

}
