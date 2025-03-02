package org.mrdarkimc.PAPI;

import me.clip.placeholderapi.PlaceholderAPI;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.OfflinePlayer;
import org.jetbrains.annotations.NotNull;
import org.mrdarkimc.SatanicDesign;
import org.mrdarkimc.SatanicLib.Utils;
import org.mrdarkimc.config.Config;

public class StaticPlaceholders extends PlaceholderExpansion {
    @Override
    public @NotNull String getIdentifier() {
        return "design";
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
    @Override
    public String onRequest(OfflinePlayer offlinePlayer, String identifier) {
        String text = SatanicDesign.getInstance().getConfig().getString("static." + identifier,"notFound");
        text = PlaceholderAPI.setPlaceholders(null,text);
        text = Utils.translateHex(text);
        return text;
    }
}
