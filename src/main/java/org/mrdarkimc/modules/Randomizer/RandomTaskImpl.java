package org.mrdarkimc.modules.Randomizer;

import org.mrdarkimc.modules.Randomizer.utils.IRandomTask;
import org.mrdarkimc.modules.Randomizer.utils.IRandomTaskComponent;

import java.util.List;

public class RandomTaskImpl implements IRandomTask {
    private List<IRandomTaskComponent> list;

    public RandomTaskImpl(List<IRandomTaskComponent> list) {
        this.list = list;
    }

    @Override
    public List<IRandomTaskComponent> getComponents() {
        return list;
    }
}
