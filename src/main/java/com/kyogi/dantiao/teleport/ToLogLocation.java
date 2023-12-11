package com.kyogi.dantiao.teleport;

import com.kyogi.dantiao.util.Positions;
import net.minecraft.server.level.ServerPlayer;

public class ToLogLocation {
	public static void to(ServerPlayer winner, ServerPlayer loser, Positions winnerLocation,
						  Positions loserLocation) {
		if (winnerLocation == null || loserLocation == null || winner == null
				|| loser == null) {
			return;
		}
		winner.teleportTo(winnerLocation.getPosition().getX(), winnerLocation.getPosition().getY(), winnerLocation.getPosition().getZ());
		loser.teleportTo(loserLocation.getPosition().getX(), loserLocation.getPosition().getY(), loserLocation.getPosition().getZ());
	}
}
