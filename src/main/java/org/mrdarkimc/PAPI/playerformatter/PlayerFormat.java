package org.mrdarkimc.PAPI.playerformatter;

import me.clip.placeholderapi.PlaceholderAPI;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.mrdarkimc.SatanicDesign;
import org.mrdarkimc.SatanicLib.Utils;

import java.util.List;

public class PlayerFormat extends PlaceholderExpansion {
    @Override
    public @NotNull String getIdentifier() {
        return "pFormat";
    }

    @Override
    public @NotNull String getAuthor() {
        return "MrDarkiMC";
    }

    @Override
    public boolean persist() {
        return true;
    }

    @Override
    public @NotNull String getVersion() {
        return "1.33.7";
    }

    //%pFormat_MrDarkiMC_prefix%
    @Override
    public String onRequest(OfflinePlayer offlinePlayer, String identifier) {
        String[] args = identifier.split("_");
        Player player = Bukkit.getPlayer(args[0]);
        String value = args[1];
        if (!List.of("icon", "prefix", "suffix", "format", "full").contains(value.toLowerCase()))
            return "PlaceholderNotFound";
        if (player != null) {
            String text = SatanicDesign
                    .getInstance()
                    .getConfig()
                    .getString("playerformat." + Cache.getGroup(player) + "_" + value.toLowerCase(), "NotFound");
            text = text.replace("{nickname}", player.getName());
            text = PlaceholderAPI.setPlaceholders(player,text);
            text = Utils.translateHex(text);
            return text;
//                switch (value.toLowerCase()){
//                    case "prefix":
//
//                        break;
//                    case "suffix":
//                        //
//                        break;
//                    case "icon":
//                        //
//                        break;
//                    case "format":
//                        //
//                        break;
//                    case "full":
//                        //
//                        break;
//                    default:
//                        //
//                        break;
//                }
        }
        return "PlayerNull";
    }
}