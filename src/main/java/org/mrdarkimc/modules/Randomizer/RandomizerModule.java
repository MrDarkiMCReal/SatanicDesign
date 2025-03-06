package org.mrdarkimc.modules.Randomizer;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.mrdarkimc.SatanicDesign;
import org.mrdarkimc.modules.BaseModule;
import org.mrdarkimc.modules.Randomizer.utils.IRandomTask;
import org.mrdarkimc.modules.Randomizer.utils.IRandomTaskComponent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RandomizerModule implements BaseModule {

    String path = "modules.randomizer";
    private Map<String, IRandomTask> taskMap;
    @Override
    public void startModule() {
    taskMap = new HashMap<>();
        var tasks = SatanicDesign.getInstance().getConfig().getConfigurationSection(path + ".randomtasks").getKeys(false);
        for (String task : tasks) {
            taskMap.put(task,new RandomTaskImpl(getComponentsFor(task)));
        }
    }
    public List<IRandomTaskComponent> getComponentsFor(String taskName){
        List<IRandomTaskComponent> list = new ArrayList<>();
        var conf = SatanicDesign.getInstance().getConfig();
        var components = conf.getConfigurationSection(path + ".randomtasks." + taskName).getKeys(false);

        for (String component : components) {
            int change = conf.getInt(path + ".randomtasks." + taskName + "." +component + ".chance");
            List<String> actions = conf.getStringList(path + ".randomtasks." + taskName + "." +component + ".commands");
           list.add(new RandomCommandsComponentImpl(change,actions));
        }
        return list;
    }

    @Override
    public void killModule() {
        taskMap.clear();
    }
    public void executeTask(String task, Player player){
        var originTask = taskMap.get(task);
        if (originTask==null) {
            Bukkit.getLogger().info("[SatanicDesign] Не найден таск " + task);
            return;
        }

        originTask.executeAs(player);
    }
}
