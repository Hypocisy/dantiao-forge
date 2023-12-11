package com.kyogi.dantiao.caches;

import com.kyogi.dantiao.DantiaoMod;
import com.kyogi.dantiao.util.Debug;
import net.minecraft.network.chat.Component;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = DantiaoMod.MOD_ID)
public class CacheEvent {
    private AreaCache areaCache;
//    private ArenaInfoCache arenaInfoCache;
//    private BlacklistCache blacklistCache;
//    private DanCache danCache;
//    private EnergyCache energyCache;
//    private LanguageFileCache languageFileCache;
//    private PointCache pointCache;
//    private RankingCache rankingCache;
//    private RecordCache recordCache;
//    private ShopCache shopCache;

    public CacheEvent() {
        long start = System.currentTimeMillis();
        Debug.send(Component.literal("开始载入缓存\n"), Component.literal( "The Caches is loading..."));
//        areaCache = new AreaCache();
//        arenaInfoCache = new ArenaInfoCache();
//        blacklistCache = new BlacklistCache();
//        danCache = new DanCache();
//        energyCache = new EnergyCache();
//        languageFileCache = new LanguageFileCache();
//        pointCache = new PointCache();
//        rankingCache = new RankingCache();
//        recordCache = new RecordCache();
//        shopCache = new ShopCache();
        long end = System.currentTimeMillis();
        Debug.send(Component.literal("所有缓存已就绪，耗时" + (end - start) + "ms"),
                Component.literal("\nAll the caches was loaded successfully in "
                        + (end - start) + "ms"));
//        int interval = DantiaoMod.getInstance().getConfigMananger()
//                .getAutoSaveCachesTime();
//        if (interval <= 10) { // 最低10秒
//            interval = 10 * 20;
//        } else {
//            interval = interval * 20;
//        }
    }

//    public void unload() {
//        energyCache.cancelTimer();
//        energyCache.save(false);
//    }
//
//    public AreaCache getArea() {
//        return areaCache;
//    }
//
//    public ArenaInfoCache getArenaInfo() {
//        return arenaInfoCache;
//    }
//
//    public BlacklistCache getBlacklist() {
//        return blacklistCache;
//    }
//
//    public DanCache getDan() {
//        return danCache;
//    }
//
//    public EnergyCache getEnergy() {
//        return energyCache;
//    }
//
//    public LanguageFileCache getLanguageFile() {
//        return languageFileCache;
//    }
//
//    public PointCache getPoint() {
//        return pointCache;
//    }
//
//    public RankingCache getRanking() {
//        return rankingCache;
//    }
//
//    public RecordCache getRecord() {
//        return recordCache;
//    }
//
//    public ShopCache getShop() {
//        return shopCache;
//    }

//    public void reloadCache(CacheType cacheType) {
//        Debug.send("开始重载缓存模块：" + cacheType, "Reloading the cache " + cacheType);
//        switch (cacheType) {
//            case AREA:
//                areaCache = new AreaCache();
//                break;
//            case ARENA_INFO:
//                arenaInfoCache = new ArenaInfoCache();
//                break;
//            case BLACKLIST:
//                blacklistCache = new BlacklistCache();
//                break;
//            case DAN:
//                danCache = new DanCache();
//                break;
//            case ENERGY:
//                unload();
//                energyCache = new EnergyCache();
//                break;
//            case LANGUAGE_FILE:
//                languageFileCache = new LanguageFileCache();
//                break;
//            case POINT:
//                pointCache = new PointCache();
//                break;
//            case RANKING:
//                rankingCache = new RankingCache();
//                break;
//            case RECORD:
//                recordCache = new RecordCache();
//                break;
//            case SHOP:
//                shopCache = new ShopCache();
//                break;
//        }
//    }


    public enum CacheType {
        AREA, ARENA_INFO, BLACKLIST, DAN, ENERGY, LANGUAGE_FILE, POINT, RANKING, RECORD, SHOP
    }
}