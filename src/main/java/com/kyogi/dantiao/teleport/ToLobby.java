package com.kyogi.dantiao.teleport;

import com.kyogi.dantiao.DantiaoMod;
import com.kyogi.dantiao.util.Positions;
import net.minecraft.server.level.ServerPlayer;

import java.io.IOException;

public class ToLobby {
	public static void to(ServerPlayer player) throws IOException {
		Positions positions = DantiaoMod.getCacheHandler().getArea().getLobby();
		if (positions == null) {
			return;
		}
		if (player == null) {
			return;
		}
		player.teleportTo(positions.getPosition().getX(),positions.getPosition().getY(),positions.getPosition().getZ());
	}
}
