package com.kyogi.dantiao.util;

import com.kyogi.dantiao.DantiaoMod;
import com.kyogi.dantiao.configuration.ConfigManager;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;

public class SyncBroadcast {
	public static void broadcast(Component message, int startWay) {
		try {
			boolean isBroadcast = false;
			ConfigManager configManager = DantiaoMod.getConfigManager();
			if (startWay == 1) {
				if (configManager.isArenaBroadcastWinningStreakTimes()) {
					isBroadcast = true;
				}
			}
			if (startWay == 2) {
				if (configManager.isArenaInvitingBroadcastWinningStreakTimes()) {
					isBroadcast = true;
				}
			}
			if (startWay == 3) {
				if (configManager.isArenaCompulsionBroadcastWinningStreakTimes()) {
					isBroadcast = true;
				}
			}
			if (isBroadcast) {
				for (ServerPlayer serverPlayer : DantiaoMod.getMinecraftServer().getPlayerList().getPlayers()){
					serverPlayer.sendSystemMessage(message);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
