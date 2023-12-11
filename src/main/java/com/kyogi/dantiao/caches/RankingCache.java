package com.kyogi.dantiao.caches;

import com.kyogi.dantiao.configuration.DataManager;
import com.kyogi.dantiao.util.Debug;
import net.minecraft.network.chat.Component;

import java.io.IOException;
import java.util.List;

public class RankingCache {
	private List<String> win;
	private List<String> KD;

	public List<String> getWin() {
		return win;
	}

	public List<String> getKD() {
		return KD;
	}

	public RankingCache() {
		try {
			win = DataManager.getWinRanking();
			KD = DataManager.getKDRanking();
			Debug.send(Component.literal("排行榜缓存已就绪"), Component.literal("The ranking cache has been initialized"));
		} catch (Exception e) {
			Debug.send(Component.literal("§c排行榜缓存未能就绪"), Component.literal("§cThe ranking cache failed to initialize"));
			e.printStackTrace();
		}
	}

	public void setWin(List<String> list) throws IOException {
		win = list;
		DataManager.setRanking(0, list);
	}

	public void setKD(List<String> list) throws IOException {
		KD = list;
		DataManager.setRanking(1, list);
	}
}
