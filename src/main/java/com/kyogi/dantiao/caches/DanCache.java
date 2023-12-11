package com.kyogi.dantiao.caches;

import com.kyogi.dantiao.DantiaoMod;
import com.kyogi.dantiao.configuration.DataManager;
import com.kyogi.dantiao.util.Debug;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;

import java.io.IOException;
import java.util.*;

public class DanCache {
    private final List<ServerPlayer> changeList = new ArrayList<>();
    private Map<ServerPlayer, Integer> map = new HashMap<>();

    public DanCache() {
        try {
            for (ServerPlayer player : DantiaoMod.getMinecraftServer().getPlayerList().getPlayers()) {
                load(player);
            }
//			LevelStorageSource.LevelStorageAccess levelStorageAccess = DantiaoMod.getMinecraftServer().getWorldData().
            Debug.send(Component.literal("段位经验数据缓存已就绪"), Component.literal("The Dan Exp cache has been initialized"));
        } catch (Exception e) {
            Debug.send(Component.literal("§c段位经验数据缓存未能加载"), Component.literal("§cThe Dan Exp cache failed to initialize"));
        }
    }

    public int get(ServerPlayer player) {
        try {
            return map.get(player);
        } catch (Exception e) {
            return 0;
        }
    }

    public Set<ServerPlayer> keySet() {
        return map.keySet();
    }

    public void load(ServerPlayer player) {
        if (!map.containsKey(player)) {
            int exp = DataManager.getDanExp(player.getName().getString());
            map.put(player, exp);
        }
    }

    public void unload(ServerPlayer player) {
        map.remove(player);
    }

    public void save(boolean isAnsyn) throws IOException {
        if (!changeList.isEmpty()) {
            for (ServerPlayer player : changeList) {
                DataManager.setDanExp(player.getName().getString(), map.get(player));
            }
            changeList.clear();
            Debug.send(Component.literal("段位经验数据已自动保存"), Component.literal("Dan EXP data saved automatically"));
        }
    }

    public void set(ServerPlayer player, int exp) {
        map.put(player, exp);
        DantiaoMod.getInstance().getDanHandler().refreshPlayerDan(player);
        if (!changeList.contains(player)) {
            changeList.add(player);
        }
    }
}
