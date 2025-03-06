package org.mrdarkimc.modules.Randomizer.utils;

import java.util.Comparator;

public class ComponentComparator implements Comparator<IRandomTaskComponent> {
    @Override
    public int compare(IRandomTaskComponent o1, IRandomTaskComponent o2) {
        if (o1.getChance() == o2.getChance())
            return 0;
        return o1.getChance() > o2.getChance() ? 1 : -1;
    }
}
