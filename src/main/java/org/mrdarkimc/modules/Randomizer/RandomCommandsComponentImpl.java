package org.mrdarkimc.modules.Randomizer;

import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.mrdarkimc.SatanicLib.Utils;
import org.mrdarkimc.modules.Randomizer.utils.IRandomTaskComponent;

import java.util.Arrays;
import java.util.List;

public class RandomCommandsComponentImpl implements IRandomTaskComponent {
    private int chance;
    private List<String> commandlist;

    public RandomCommandsComponentImpl(int chance, List<String> commandlist) {
        this.chance = chance;
        this.commandlist = commandlist;
    }

    @Override
    public int getChance() {
        return chance;
    }

    @Override
    public List<String> getActions() {
        return commandlist;
    }

    @Override
    public void execute(Player player) {
        Bukkit.getLogger().info("[SatanicDesign] Выполняю команды от рандомного таска: " + Arrays.toString(commandlist.toArray()));
        for (int i = 0; i < commandlist.size(); i++) {
            String currentline = commandlist.get(i);
            commandlist.set(i, parse(player,currentline));
        }
        for (String command : commandlist) {
            Bukkit.getLogger().info("Выполняю команду: " + command);
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), command);
        }

    }
    private String parse(Player player, String text){
        text = PlaceholderAPI.setPlaceholders(player,text);
        text = Utils.translateHex(text);
//        if (ph==null)
//            return text;
//        for (Map.Entry<String, String> map : ph.entrySet()) {
//            text = text.replace(map.getKey(), map.getValue());
//        }
        return text;
    }
}
