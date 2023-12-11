package com.kyogi.dantiao.caches;

import com.kyogi.dantiao.DantiaoMod;
import com.kyogi.dantiao.encapsulation.ArenaInfo;
import com.kyogi.dantiao.util.Debug;
import net.minecraft.network.chat.Component;

import java.io.IOException;

public class CacheHandler {
    private AreaCache areaCache;
    private ArenaInfoCache arenaInfoCache;
    private BlacklistCache blacklistCache;
    private DanCache danCache;
    private EnergyCache energyCache;
    private PointCache pointCache;
    private RankingCache rankingCache;
    private RecordCache recordCache;
    private ShopCache shopCache;

    private int taskId = -1;

    public CacheHandler() throws IOException {
        long start = System.currentTimeMillis();
        Debug.send(Component.literal("开始载入缓存"), Component.literal("The Caches loading..."));
        areaCache = new AreaCache();
        arenaInfoCache = new ArenaInfoCache();
        blacklistCache = new BlacklistCache();
        danCache = new DanCache();
        energyCache = new EnergyCache();
        pointCache = new PointCache();
        rankingCache = new RankingCache();
        recordCache = new RecordCache();
        shopCache = new ShopCache();
        long end = System.currentTimeMillis();
        Debug.send(Component.literal("所有缓存已就绪，耗时" + (end - start) + "ms"), Component.literal("All the caches was loaded successfully in " + (end - start) + "ms"));
        int interval = DantiaoMod.getConfigManager().getAutoSaveCachesTime();
        if (interval <= 10) { // 最低10秒
            interval = 10 * 20;
        } else {
            interval = interval * 20;
        }
        if (taskId == -1) {
            taskId = this.getTaskId();
        } else {
            danCache.save(true);
            pointCache.save(true);
            energyCache.save(true);
        }
    }
    public void unload () {
        try {
            taskId = -1;

            danCache.save(false);
            pointCache.save(false);
            energyCache.cancelTimer();
            energyCache.save(false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public AreaCache getArea () {
        return areaCache;
    }

    public ArenaInfoCache getArenaInfoCache() {
        return arenaInfoCache;
    }
    public ArenaInfo getArenaInfo () {
        return new ArenaInfo();
    }

    public BlacklistCache getBlacklist () {
        return blacklistCache;
    }

    public DanCache getDan () {
        return danCache;
    }

    public EnergyCache getEnergy () {
        return energyCache;
    }

    public PointCache getPoint () {
        return pointCache;
    }

    public RankingCache getRanking () {
        return rankingCache;
    }

    public RecordCache getRecord () {
        return recordCache;
    }

    public ShopCache getShop () {
        return shopCache;
    }
}