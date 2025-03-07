package org.mrdarkimc.modules.CustomItems;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.mrdarkimc.SatanicDesign;
import org.mrdarkimc.SatanicLib.Utils;
import org.mrdarkimc.modules.BaseModule;
import org.mrdarkimc.modules.CustomItems.Disorientation.Disorientation;
import org.mrdarkimc.modules.CustomItems.RevealDust.RevealDust;
import org.mrdarkimc.modules.CustomItems.Utils.BasicItem;

import java.awt.event.ItemListener;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ItemsModule implements BaseModule {
    String path = "modules.items";
    public static NamespacedKey desorient;
    public static NamespacedKey reveal_dust;

    private Map<NamespacedKey, BasicItem> itemMap;

    @Override
    public void startModule() {
        itemMap = new HashMap<>();
        desorient = new NamespacedKey(SatanicDesign.getInstance(), "desorient");
        reveal_dust = new NamespacedKey(SatanicDesign.getInstance(), "reveal_dust");
        setupDesorient();
        setupRevealDust();
    }

    @Override
    public void killModule() {

    }

    private Disorientation setupDesorient() {
        var conf = SatanicDesign.getInstance().getConfig();
        int cd = conf.getInt(path + ".desorient.cooldown",45);
        int radius = conf.getInt(path + ".desorient.radius",15);
        int dur = conf.getInt(path + ".desorient.duration",10);
        var v = new Disorientation("desorient", desorient, cd, radius, dur);
        Bukkit.getServer().getPluginManager().registerEvents(v, SatanicDesign.getInstance());
        itemMap.put(desorient, v);
        return v;
    }

    private RevealDust setupRevealDust() {
        var conf = SatanicDesign.getInstance().getConfig();
        int cd = conf.getInt(path + ".reveal_dust.cooldown",45);
        int radius = conf.getInt(path + ".reveal_dust.radius",15);
        int dur = conf.getInt(path + ".reveal_dust.duration",10);
        var v = new RevealDust("reveal_dust", cd, reveal_dust,radius,dur);
        Bukkit.getServer().getPluginManager().registerEvents(v, SatanicDesign.getInstance());
        itemMap.put(reveal_dust, v);
        return v;
    }

    public ItemStack getDesorient() {
        var conf = SatanicDesign.getInstance().getConfig();
        String display = Utils.translateHex(conf.getString(path + ".desorient.display"));
        Material mat = Material.valueOf(conf.getString(path + ".desorient.material"));
        List<String> lore = conf.getStringList(path + ".desorient.lore");
        lore.replaceAll(Utils::translateHex);
        var item = new ItemStack(mat);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(display);
        meta.setLore(lore);
        meta.getPersistentDataContainer().set(desorient, PersistentDataType.BOOLEAN, true);
        item.setItemMeta(meta);
        return item;
    }
    public ItemStack getRevealDust() {
        var conf = SatanicDesign.getInstance().getConfig();
        String display = Utils.translateHex(conf.getString(path + ".reveal_dust.display"));
        Material mat = Material.valueOf(conf.getString(path + ".reveal_dust.material"));
        List<String> lore = conf.getStringList(path + ".desorient.lore");
        lore.replaceAll(Utils::translateHex);
        var item = new ItemStack(mat);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(display);
        meta.setLore(lore);
        meta.getPersistentDataContainer().set(reveal_dust, PersistentDataType.BOOLEAN, true);
        item.setItemMeta(meta);
        return item;
    }
}
