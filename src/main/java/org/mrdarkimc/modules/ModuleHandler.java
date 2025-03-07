package org.mrdarkimc.modules;

import org.bukkit.configuration.file.FileConfiguration;
import org.mrdarkimc.SatanicDesign;
import org.mrdarkimc.modules.BossBar.BossBarHandler;
import org.mrdarkimc.modules.CustomItems.ItemsModule;
import org.mrdarkimc.modules.DeathCoords.DeathCoords;
import org.mrdarkimc.modules.Randomizer.RandomizerModule;
import org.mrdarkimc.modules.SoundModule.SoundDispatcher;

import java.util.HashMap;
import java.util.Map;

public class ModuleHandler {
    public Map<String, BaseModule> modules;
    private BossBarHandler bossBarHandler;
    private DeathCoords coords;
    private SoundDispatcher soundDispatcher;
    private RandomizerModule randomizer;
    private ItemsModule items;

    public ItemsModule getItems() {
        return items;
    }

    public void setUpModules(){
        modules = new HashMap<>();
        FileConfiguration config = SatanicDesign.getInstance().getConfig();
        if (config.getBoolean("modules.bossbar.enable",false)){
            bossBarHandler = new BossBarHandler();
            //todo modules.put(bossBarHandler.getClass().getName(),bossBarHandler);
        }
        if (config.getBoolean("modules.sound.enable",false)){
            //SoundDispatcher.enabled = true;
        }
        if (config.getBoolean("modules.death-coords.enable",false)){
            coords = new DeathCoords();
            modules.put(coords.getClass().getName(),coords);
        }
        if (config.getBoolean("modules.randomizer.enable",false)){
            randomizer = new RandomizerModule();
            modules.put(randomizer.getClass().getName(),randomizer);
        }
        if (config.getBoolean("modules.items.enable",false)){
            items = new ItemsModule();
            modules.put(items.getClass().getName(),items);

            //todo задефать эксепшены при попытке использовать предмет, если он выкл
        }


        modules.forEach((k,v) -> v.startModule());
    }

    public BossBarHandler getBossBarHandler() {
        return bossBarHandler;
    }

    public RandomizerModule getRandomizer() {
        return randomizer;
    }

    public ModuleHandler() {
        modules = new HashMap<>();
    }

    public void enable(BaseModule module){
    modules.put(module.getClass().getName(),module);
    module.startModule();
    }
    public void disable(String module){
        modules.remove(module).killModule();
    }
    public void disableAll(){
        modules.forEach((k,v) -> v.killModule());
        modules.clear();
    }
}

