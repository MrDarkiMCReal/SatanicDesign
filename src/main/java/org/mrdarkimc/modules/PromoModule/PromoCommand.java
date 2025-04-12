package org.mrdarkimc.modules.PromoModule;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class PromoCommand implements CommandExecutor {
    private CommandService service;

    public PromoCommand(CommandService service) {
        this.service = service;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if (strings.length < 1){
            service.showInfo(commandSender);
            return true;
        }
        service.attemptPromo(commandSender, strings[0]);
        return true;
    }
}
