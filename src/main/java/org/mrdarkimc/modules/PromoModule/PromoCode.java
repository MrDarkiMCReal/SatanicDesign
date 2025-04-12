package org.mrdarkimc.modules.PromoModule;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.List;

public class PromoCode {
    private String name;
    private List<String> commandlist;

    public PromoCode(String name) {
        this.name = name;
        this.commandlist = PromoModules.getConfig().get().getStringList("promos."+ name + ".joiner");
    }
    public void executeFor(Player player){
        commandlist.replaceAll(s -> s.replace("%player%",player.getName()));
        commandlist.forEach(cmd -> Bukkit.dispatchCommand(Bukkit.getConsoleSender(),cmd));
    }
}
