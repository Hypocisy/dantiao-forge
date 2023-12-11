package com.kyogi.dantiao.event.arena;

import com.kyogi.dantiao.arenas.Arena;
import net.minecraft.server.level.ServerPlayer;

public class ArenaDrawFinishEvent extends ArenaEventAbs {
	private final ServerPlayer player1;
	private final ServerPlayer player2;
	private final Arena arena;

	/**
	 * 某场比赛在平局情况下结束时触发，不包括管理员强行结束的情况
	 * 
	 * @param player1
	 *            玩家1
	 * @param player2
	 *            玩家2
	 * @param arena
	 *            所在的竞技场
	 */
	public ArenaDrawFinishEvent(ServerPlayer player1, ServerPlayer player2, Arena arena) {
		this.player1 = player1;
		this.player2 = player2;
		this.arena = arena;
	}

	public ServerPlayer getPlayer1() {
		return player1;
	}

	public ServerPlayer getPlayer2() {
		return player2;
	}

	public Arena getArena() {
		return arena;
	}
}