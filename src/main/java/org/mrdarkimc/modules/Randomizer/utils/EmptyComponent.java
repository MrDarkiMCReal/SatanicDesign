package org.mrdarkimc.modules.Randomizer.utils;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.Collections;
import java.util.List;

public class EmptyComponent implements IRandomTaskComponent{
    @Override
    public int getChance() {
        return 0;
    }

    @Override
    public List<String> getActions() {
        return Collections.emptyList();
    }

    @Override
    public void execute(Player player) {
        Bukkit.getLogger().info("[SatanicDesign] Отработал стандартный компонент рандомайзера. Это ошибка. Игрок: " + player.getName());
    }
}
