package org.mrdarkimc.modules.NearCommand;

import org.mrdarkimc.SatanicDesign;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class CooldownHandler {
    private Map<UUID,CooldownResetter> cooldowns;

    public Map<UUID, CooldownResetter> getCooldowns() {
        return cooldowns;
    }

    public CooldownHandler() {
        this.cooldowns = new HashMap<>();
    }

    public void addCooldown(UUID uuid, int seconds){
        var cd = new CooldownResetter(uuid,this);
        cd.runTaskLater(SatanicDesign.getInstance(),seconds*20L);
        cooldowns.put(uuid,cd);
    }
    public boolean hasCooldown(UUID uuid){
        return cooldowns.containsKey(uuid);
    }
    public long getStartTime(UUID uuid){
        return cooldowns.get(uuid).getTimeStart();
    }
    CooldownResetter getByUUID(UUID uuid){
        return cooldowns.get(uuid);
    }
    public void remove(UUID uuid){
        if (cooldowns.containsKey(uuid)){
            cooldowns.get(uuid).cancel();
        }

    }
}
