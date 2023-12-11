package com.kyogi.dantiao.ranking;

import com.valorin.Main;
import com.valorin.caches.RankingCache;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class Ranking {
	public void rank(String s, boolean isWin) {// 添加排行榜元素
		RankingCache cache = Main.getInstance().getCacheHandler().getRanking();
		List<String> ranking;
		if (isWin) {
			ranking = cache.getWin();
		} else {
			ranking = cache.getKD();
		}
		if (ranking.size() != 0) {
			List<String> list = ranking;
			for (int i = 0; i < list.size(); i++) {
				if (list.get(i).split("\\|")[0].equals(s.split("\\|")[0])) {
					list.remove(i);
				}
			}
			double n = Double.valueOf(s.split("\\|")[1]);
			int originalSize = list.size();
			list.add("");
			for (int i = (originalSize - 1); i >= 0; i--) {
				double a = Double.valueOf(list.get(i).split("\\|")[1]);
				if (n <= a) {// 如果小于等于比较对象，终止
					for (int i2 = (originalSize - 1); i2 > i; i2--) {
						list.set(i2 + 1, list.get(i2));
					}
					list.set(i + 1, s);
					return;
				}
			}
			for (int i2 = (originalSize - 1); i2 >= 0; i2--) {
				list.set(i2 + 1, list.get(i2));
			}
			list.set(0, s);
			if (isWin) {
				cache.setWin(list);
			} else {
				cache.setKD(list);
			}
		} else {
			List<String> list = new ArrayList<String>();
			list.add(s);
			if (isWin) {
				cache.setWin(list);
			} else {
				cache.setKD(list);
			}
		}
	}

	public int getWin(Player p) {// 获取胜场榜排名
		int n = 0;
		List<String> ranking = Main.getInstance().getCacheHandler()
				.getRanking().getWin();
		if (ranking.size() == 0) {
			return n;
		}
		for (int i = 0; i < ranking.size(); i++) {
			if (ranking.get(i).split("\\|")[0].equals(p.getName())) {
				n = i + 1;
			}
		}
		return n;
	}

	public String getPlayerByWin(int rank) {// 获取胜场榜排名对应的玩家
		List<String> rankingList = Main.getInstance().getCacheHandler()
				.getRanking().getWin();
		if (rankingList.size() <= rank) {
			return null;
		}
		return rankingList.get(rank).split("\\|")[0];
	}

	public int getKD(Player p) {// 获取KD榜排名
		List<String> ranking = Main.getInstance().getCacheHandler()
				.getRanking().getKD();
		int n = 0;
		if (ranking.size() == 0) {
			return n;
		}
		for (int i = 0; i < ranking.size(); i++) {
			if (ranking.get(i).split("\\|")[0].equals(p.getName())) {
				n = i + 1;
			}
		}
		return n;
	}

	public String getPlayerByKD(int rank) {// 获取KD榜排名对应的玩家
		List<String> rankingList = Main.getInstance().getCacheHandler()
				.getRanking().getKD();
		if (rankingList.size() <= rank) {
			return null;
		}
		return rankingList.get(rank).split("\\|")[0];
	}
}