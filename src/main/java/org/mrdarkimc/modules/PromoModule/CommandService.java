package org.mrdarkimc.modules.PromoModule;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.mrdarkimc.SatanicLib.messages.KeyedMessage;

public class CommandService {
    private PromoModules module;

    public CommandService(PromoModules module) {
        this.module = module;
    }

    public void showInfo(CommandSender sender){
        Player player = ((Player) sender);
    new KeyedMessage(player,"modules.promo.messages.info",null).send();
    }
    public void attemptPromo(CommandSender sender, String promo){
        String code = PromoModules.getConfig().get().getString("promos." + promo);
        if (code !=null){
            new PromoCode(code).executeFor((Player) sender);
            return;
        }
        showError();
    }
    public void showError(){

    }
    private void increaseUsage(){

    }
}
