package com.kyogi.dantiao.arenas;

import com.kyogi.dantiao.configuration.DataManager;
import net.minecraft.server.level.ServerPlayer;

import java.util.ArrayList;
import java.util.List;

public class ArenaManager {

	public static List<Arena> arenas = new ArrayList<>();
	public static List<String> busyArenasName = new ArrayList<>();

	public Arena getArena(String name) {
		if (name == null) {
			return null;
		}
		if (arenas.isEmpty()) {
			return null;
		}
		for (Arena arena : arenas) {
			if (arena.getArenaName().equals(name)) {
				return arena;
			}
		}
		return null;
	}

	public void addArena(String name) {
		arenas.add(new Arena(name));
	}

	public void removeArena(String name) {
		arenas.remove(getArena(name));
	}

	public String getPlayerOfArena(ServerPlayer player) {
		if (arenas.isEmpty()) {
			return null;
		}
		for (Arena arena : arenas) {
			if (arena.isArenaEnable()) {
				if (arena.isPlayer1(player) || arena.isPlayer2(player)) {
					return arena.getArenaName();
				}
			}
		}
		return null;
	}

	public String getWatcherOfArena(ServerPlayer player) {
		if (arenas.isEmpty()) {
			return null;
		}
		for (Arena arena : arenas) {
			if (arena.isArenaEnable()) {
				if (arena.getWatchers().contains(player)) {
					return arena.getArenaName();
				}
			}
		}
		return null;
	}

	public ServerPlayer getTheOtherPlayer(ServerPlayer player) {
		String arenaName = getPlayerOfArena(player);
		if (arenaName != null) {
			Arena arena = getArena(arenaName);
			if (arena.getPlayer1().equals(player)) {
				return arena.getPlayer2();
			} else {
				return arena.getPlayer1();
			}
		}
		return null;
	}

	public boolean isContainPlayer(String arenaName, ServerPlayer player) {// 某个指定竞技场是否包括某玩家
		if (getArena(arenaName) == null) {
			return false;
		}
		if (getArena(arenaName).isArenaEnable()) {
            return getArena(arenaName).getPlayer1().equals(player)
                    || getArena(arenaName).getPlayer2().equals(player);
		}
		return false;
	}

	public boolean isPlayerBusy(ServerPlayer player) {// 某玩家是否在比赛
		if (arenas.isEmpty()) {
			return false;
		}
		for (Arena arena : arenas) {
			if (isContainPlayer(arena.getArenaName(), player)) {
				return true;
			}
		}
		return false;
	}
	
	public int size() { //获取竞技场数量
		return arenas.size();
	}

	public ArenaManager() {
		if (DataManager.getArenas().isEmpty()) {
			return;
		}
		for (String arena : DataManager.getArenas()) {
			arenas.add(new Arena(arena));
		}
	}
}
