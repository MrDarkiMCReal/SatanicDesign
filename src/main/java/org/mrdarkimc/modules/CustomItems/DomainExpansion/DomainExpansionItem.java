package org.mrdarkimc.modules.CustomItems.DomainExpansion;

import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.mrdarkimc.modules.CustomItems.Utils.BasicItem;

public class DomainExpansionItem extends BasicItem {
    public DomainExpansionItem(String item, int cooldown, NamespacedKey key) {
        super(item, cooldown, key);
    }

    @Override
    public void useItemFor(Player player) {

    }
}
