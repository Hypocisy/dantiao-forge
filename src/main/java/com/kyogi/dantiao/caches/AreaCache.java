package com.kyogi.dantiao.caches;


import com.kyogi.dantiao.configuration.DataManager;
import com.kyogi.dantiao.util.Positions;
import com.kyogi.dantiao.util.Debug;
import net.minecraft.network.chat.Component;

import java.io.IOException;

public class AreaCache {
    private Positions lobbyLocation;
    private Positions winRankingLocation;
    private Positions KDRankingLocation;

    public AreaCache() {
        try {
            lobbyLocation = DataManager.getLobbyLocation();
            winRankingLocation = DataManager.getHologramLocation(0);
            KDRankingLocation = DataManager.getHologramLocation(1);
            Debug.send(Component.literal("大厅、全息图位置缓存已就绪"),
                    Component.literal("The lobby and hologram location cache has been initialized"));
        } catch (Exception e) {
            Debug.send(Component.literal("§c大厅、全息图位置缓存未能加载"), Component.literal(
                    "§cThe lobby and hologram location cache failed to initialize"));
        }
    }

    public Positions getLobby() {
        return lobbyLocation;
    }

    public void setLobby(Positions positions) throws IOException {
        this.lobbyLocation = positions;
        DataManager.setLobbyLocation(positions);
    }

    public Positions getWinRankingLocation() {
        return winRankingLocation;
    }

    public Positions getKDRankingLocation() {
        return KDRankingLocation;
    }

    public void setWinRanking(Positions positions) throws IOException {
        this.winRankingLocation = positions;
        DataManager.setHologramLocation(0, positions);
    }

    public void setKDRanking(Positions positions) throws IOException {
        this.KDRankingLocation = positions;
        DataManager.setHologramLocation(1, positions);
    }
}
