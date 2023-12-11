package com.kyogi.dantiao.queue;

import net.minecraft.server.level.ServerPlayer;

public class SearchingQueue {
	private ServerPlayer waiter;

	public ServerPlayer getWaiter() {
		return waiter;
	}

	public void setWaiter(ServerPlayer player) {
		waiter = player;
	}
}
