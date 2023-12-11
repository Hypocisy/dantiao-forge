package com.kyogi.dantiao.caches;

import com.kyogi.dantiao.DantiaoMod;
import com.kyogi.dantiao.configuration.DataManager;
import com.kyogi.dantiao.event.EnergyChangedEvent;
import com.kyogi.dantiao.util.Debug;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.common.MinecraftForge;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class EnergyCache {
    private final Map<String, Double> map = new HashMap<>();
    //	private BukkitTask timer;
    private double maxEnergy;
    private double energyNeeded;
    private double energyRecoverPerSecond;
    private boolean isEnable;

    public EnergyCache() {
        isEnable = DantiaoMod.getConfigManager().isEnableEnergy();
        if (!isEnable) {
            return;
        }
        try {
            for (ServerPlayer player : DantiaoMod.getMinecraftServer().getPlayerList().getPlayers()) {
                load(player.getName().toString());
            }
            Debug.send(Component.literal("精力值数据缓存已就绪"), Component.literal("The energy cache has been initialized"));
        } catch (Exception e) {
            Debug.send(Component.literal("§c精力值数据缓存未能加载"), Component.literal("§cThe energy cache failed to initialize"));
        }
        maxEnergy = DantiaoMod.getConfigManager().getMaxEnergy();
        if (maxEnergy <= 0) {
            maxEnergy = 300.0;
        }
        energyNeeded = DantiaoMod.getConfigManager().getEnergyNeeds();
        if (energyNeeded <= 0) {
            energyNeeded = 90.0;
        }
        energyRecoverPerSecond = DantiaoMod.getConfigManager().getEnergyRecoverPerSecond();
        if (energyRecoverPerSecond <= 0) {
            energyRecoverPerSecond = 0.5;
        }
        for (String name : map.keySet()) {
            double energyNow = map.get(name);
            if (maxEnergy - energyNow <= energyRecoverPerSecond) {
                map.put(name, maxEnergy);
            } else {
                map.put(name, energyNow + energyRecoverPerSecond);
            }
        }
    }

    public double get(String name) {
        try {
            return map.get(name);
        } catch (Exception e) {
            return 0;
        }
    }

    public void load(String name) {
        if (!map.containsKey(name)) {
            double energy = DataManager.getEnergy(name);
            map.put(name, energy);
        }
    }

    public void save(boolean isAsyn) throws IOException {
        if (!isEnable) {
            return;
        }
        for (String name : map.keySet()) {
            DataManager.setEnergy(name, map.get(name));
        }
        Debug.send(Component.literal("精力值数据已自动保存"), Component.literal("The energy data saved automatically"));
    }

    public void set(ServerPlayer player, double energy) {
        double log = map.get(player.getName().getString());
        map.put(player.getName().getString(), energy);
        if (!player.hasDisconnected()) {
            EnergyChangedEvent event = new EnergyChangedEvent(player, log, energy);
            MinecraftForge.EVENT_BUS.post(event);
        }
    }

    public double getMaxEnergy() { // 获取最大精力值
        return maxEnergy;
    }

    public double getEnergyNeeded() { // 获取每场所需精力值
        return energyNeeded;
    }

    public double getEnergyRecoverPerSecond() { // 获取每秒恢复的精力值
        return energyRecoverPerSecond;
    }

    public boolean isEnable() { // 本功能是否开启
        return isEnable;
    }

    public void cancelTimer() {
        if (!isEnable) {
            return;
        }
        timer.cancel();
    }
}
