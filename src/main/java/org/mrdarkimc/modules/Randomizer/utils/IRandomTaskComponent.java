package org.mrdarkimc.modules.Randomizer.utils;

import org.bukkit.entity.Player;

import java.util.List;

public interface IRandomTaskComponent {
    int getChance();
    List<String> getActions();
    void execute(Player player);
}
