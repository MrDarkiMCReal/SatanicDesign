package org.mrdarkimc.modules.Randomizer.utils;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ThreadLocalRandom;

public interface IRandomTask {
    List<IRandomTaskComponent> getComponents();
    default void executeAs(Player player){
        IRandomTaskComponent component = randomize();
        component.execute(player);
    }

    default IRandomTaskComponent randomize() {
        Bukkit.getLogger().info("[SatanicDesign] Рандомиризую таск...");
        CompletableFuture<IRandomTaskComponent> furute = CompletableFuture.supplyAsync( () -> {
//            int sum = 0;
//            for (IRandomTaskComponent component : getComponents()) {
//                sum = sum + component.getChance();
//            }
            List<IRandomTaskComponent> sorted = getComponents().stream().sorted(new ComponentComparator()).toList();
            //todo redo to cumulative chances
            int index = ThreadLocalRandom.current().nextInt(1,sorted.size()+1);
            return sorted.get(index-1);
        });
        try {
            return furute.get();
        } catch (InterruptedException | ExecutionException e) {
            //Bukkit.getLogger().info("[SatanicDesign] Ошибка при вычислении рандомного компонента");
        }
        return new EmptyComponent();
    }
}
