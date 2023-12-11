package com.kyogi.dantiao.teleport;

import com.valorin.Main;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class ToWatchingPoint {
	public static void to(Player p, String editName) {
		Location watchingPoint = Main.getInstance().getCacheHandler().getArenaInfo().get(editName).getWatchingPoint();
		if (watchingPoint == null || p == null) {
			return;
		}
		p.teleport(watchingPoint);
	}
}
