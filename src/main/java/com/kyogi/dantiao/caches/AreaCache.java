package com.kyogi.dantiao.caches;

import com.kyogi.dantiao.util.Debug;
import net.minecraft.network.chat.Component;
import net.minecraft.world.phys.Vec3;

public class AreaCache {
    private Vec3 lobbyLocation;
    private Vec3 winRankingLocation;
    private Vec3 KDRankingLocation;

    public Vec3 getLobby() {
        return lobbyLocation;
    }

    public Vec3 getWinRankingLocation() {
        return winRankingLocation;
    }

    public Vec3 getKDRankingLocation() {
        return KDRankingLocation;
    }

    public AreaCache() {
        try {
            lobbyLocation = Data.getLobbyLocation();
            winRankingLocation = Data.getHologramLocation(0);
            KDRankingLocation = Data.getHologramLocation(1);
            Debug.send(Component.literal("大厅、全息图位置缓存已就绪"),
                    Component.literal("The lobby and hologram location cache has been initialized"));
        } catch (Exception e) {
            Debug.send(Component.literal("§c大厅、全息图位置缓存未能加载"),Component.literal(
                    "§cThe lobby and hologram location cache failed to initialize"));
        }
    }

    public void setLobby(Vec3 location) {
        this.lobbyLocation = location;
        Data.setLobbyLocation(location);
    }

    public void setWinRanking(Vec3 location) {
        this.winRankingLocation = location;
        Bukkit.getScheduler().runTaskAsynchronously(Main.getInstance(), () -> {
            Data.setHologramLocation(0, location);
        });
    }

    public void setKDRanking(Location location) {
        this.KDRankingLocation = location;
        Bukkit.getScheduler().runTaskAsynchronously(Main.getInstance(), () -> {
            Data.setHologramLocation(1, location);
        });
    }
}
