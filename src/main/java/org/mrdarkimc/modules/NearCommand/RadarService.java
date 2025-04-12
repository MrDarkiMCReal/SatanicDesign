package org.mrdarkimc.modules.NearCommand;

import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffectType;
import org.mrdarkimc.PAPI.playerformatter.Cache;
import org.mrdarkimc.SatanicDesign;
import org.mrdarkimc.SatanicLib.TagBuilderGetter;
import org.mrdarkimc.SatanicLib.Utils;
import org.mrdarkimc.SatanicLib.messages.KeyedMessage;

import java.util.List;
import java.util.Map;

public class RadarService {
    CooldownHandler cooldowns;

    public RadarService() {
        this.cooldowns  = new CooldownHandler();
    }

    public void sendCooldownMessage(Player player, int radius){
        long current = System.currentTimeMillis();
        int secounds_passed = (int) ((current-cooldowns.getStartTime(player.getUniqueId()))/1000);
        int seconds = NearModule.getCooldownInSeconds()-secounds_passed;
        var comp = TagBuilderGetter.get(player,"nearSkip",Map.of("{radius}",String.valueOf(radius)));
        new KeyedMessage(player,"modules.near.messages.toofast", Map.of("{time}",String.valueOf(seconds))).send();
        player.spigot().sendMessage(comp);
    }
    public int findRange(String group, String inputrange){
        int defaultRange = SatanicDesign.getInstance().getConfig().getInt("modules.near.groups." + group, 100);
        int definedRange = defaultRange;
        try{
            definedRange = Integer.parseInt(inputrange);
        }catch (NumberFormatException e){
            return defaultRange;
        }
        return Math.min(defaultRange,definedRange);
    }
    public String buildPlayerListMessage(Player player, List<Player> list){
        StringBuilder builder = new StringBuilder();
        for (Player entityPlayer : list) {
            String group = Cache.getGroup(entityPlayer);
            if (entityPlayer.hasPotionEffect(PotionEffectType.INVISIBILITY)){
                builder.append(Utils.translateHex("&#FF0048[невидимка]&#CFCFCF"));
            }else{
                builder.append(Utils.translateHex(PlaceholderAPI.setPlaceholders(player,"%design_clr_" + group + "%")));
                builder.append(entityPlayer.getName());
                builder.append(Utils.translateHex("&#CFCFCF"));
                builder.append("(").append(findDiff(entityPlayer,player)).append("м)");
            }
            if (!list.getLast().equals(entityPlayer)){
                builder.append(", ");
            }
        }
        return builder.toString();
    }
    public List<Player> getNearPlayers(Player player, int radius){
        List<Player> list = player.getWorld().getNearbyEntities(player.getLocation(),radius,radius,radius).stream()
                .filter(entity -> entity instanceof Player)
                .map(e -> ((Player) e).getPlayer())
                .filter(this::notInVanish)
                .filter(p -> !p.equals(player))
                .filter(p -> !p.hasPermission("satanic.near.immune"))
                .toList();
        return list;
    }
    void setCooldown(Player player){
        cooldowns.addCooldown(player.getUniqueId(),getCooldownInSecondsFromConfig(player));
    }
    int getCooldownInSecondsFromConfig(Player player){
        return 120;
    }
    boolean hasCooldown(Player player){
        return cooldowns.hasCooldown(player.getUniqueId());
    }
    void notFoundMessage(Player player, String group, int radius){
        new KeyedMessage(player, "modules.near.messages.notFound",Map.of("{radius}",String.valueOf(radius))).send();
        new KeyedMessage(player, "modules.near.messages." + group,null).send();
    }
    boolean notInVanish(Player player){
        String text = PlaceholderAPI.setPlaceholders(player,"%sunlight_vanish_state%");
        return text.contains("нет");
    }
    int findDiff(Player p1, Player p2){
        return (int) p1.getLocation().toVector().distance(p2.getLocation().toVector());
    }
}
