package org.mrdarkimc.modules.CustomItems.RevealDust;

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

public class RevealDust extends BasicItem {
    int radius;
    int duration;
    public RevealDust(String item, int cooldown, NamespacedKey key, int radius, int duration) {
        super(item, cooldown, key);
        this.radius = radius;
        this.duration = duration;
    }

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
        player.addPotionEffect(new PotionEffect(PotionEffectType.GLOWING,duration * 20, 1));
    }
    void playVisualEffects(Location loc){
        new UseEffects(Particle.SOUL_FIRE_FLAME, loc,radius,500).runTask(SatanicDesign.getInstance());
    }

}
