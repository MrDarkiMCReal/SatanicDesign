package org.mrdarkimc.modules.MobsModule;

import org.bukkit.Location;

import org.bukkit.World;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Skeleton;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.ProjectileHitEvent;

import org.mrdarkimc.SatanicLib.Utils;

import java.util.concurrent.ThreadLocalRandom;

public class MobSpawnListener implements Listener {
    private MobsSettings settings;

    {
        settings = MobsSettings.init();
    }


    @EventHandler
    public void onSpawn(CreatureSpawnEvent e) {
        if (e.getEntity() instanceof Monster monster) {

            e.getEntityType().toString().toLowerCase();
            var entity = e.getEntity();
            //entity.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).setBaseValue(200);
            Location loc = entity.getLocation();
            World world = loc.getWorld();
            Location center = settings.getLocationByWorld(world.getName());
            //сделать так, что бы если демон не биг, то лвл модификаторы ему не давались
            int radius = (int) center.distance(loc);
            System.out.println("Radius: " + radius);
            if (radius<1000)
                return;
            if (radius>15000){
                spawnBoss(entity,radius);
                return;
            }
            spawnDemon(entity,radius);

        }
    }
    public void spawnDemon(LivingEntity entity, int radius){
        var demon = settings.getDemonTypeByRadius(radius);
        int maxlevel = demon.getMaxLevel();
        int minlevel = demon.getMinLevel();
        System.out.println("Max level: " + maxlevel);
        System.out.println("Min level: " + minlevel);
        int level = ThreadLocalRandom.current().nextInt(minlevel, maxlevel);
        boolean isBig = level >= 70;
        //return
        String mobname = MobsModule.getConfig().get().getString("mobs.names." + (isBig ? "bigdemon" : "demon")).replace("%lvl%", String.valueOf(level));
        entity.setCustomName(Utils.translateHex(mobname));
        setDamage(entity, level);

        setHealth(entity, level);
    }
    public void spawnBoss(LivingEntity entity, int radius){
//        var demon = settings.getDemonTypeByRadius(radius);
//        int maxlevel = demon.getMaxLevel();
//        int minlevel = demon.getMinLevel();
//        int level = ThreadLocalRandom.current().nextInt(minlevel, maxlevel);
//        boolean isBig = level >= 70;
//        //return
//        String mobname = MobsModule.getConfig().get().getString("mobs.names." + (isBig ? "bigdemon" : "demon")).replace("%lvl%", String.valueOf(level));
//        entity.setCustomName(Utils.translateHex(mobname));
//        setDamage(entity, level);
//
//        setHealth(entity, level);
    }


    public void setHealth(LivingEntity entity, int level) {
        double health = entity.getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue();
        double health_modifier = settings.getHealthModifier();
        double finalHealth = health + (health_modifier * level);
        System.out.println("Setting health: " + finalHealth);
        entity.setHealth(finalHealth);
    }

    public void setDamage(LivingEntity entity, int level) {
        double dmg_modifier = settings.getDamageModifier();
        AttributeInstance attInst = entity.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE); //от эта хуня может быть нулл. триггерится на свинок, коровок и т.д
        double baseDamage = attInst.getBaseValue();
        double finalDamage = baseDamage + (level * dmg_modifier);
        System.out.println("Setting damage: " + finalDamage);
        attInst.setBaseValue(finalDamage);
    }

    @EventHandler
    public void onSkeletonHit(ProjectileHitEvent e) {
        if (e.getEntity().getShooter() instanceof Skeleton skeleton) {
            //increase dmg
            //skeleton.getAttribute()
        }

    }
}

