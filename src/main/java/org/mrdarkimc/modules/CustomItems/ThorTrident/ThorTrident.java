package org.mrdarkimc.modules.CustomItems.ThorTrident;

import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.mrdarkimc.modules.CustomItems.Utils.BasicItem;

public class ThorTrident extends BasicItem {
    public ThorTrident(String item, int cooldown, NamespacedKey key) {
        super(item, cooldown, key);
    }

    @Override
    public void useItemFor(Player player) {

    }
}
