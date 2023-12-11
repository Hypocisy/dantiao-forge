package com.kyogi.dantiao.event.game;

import com.kyogi.dantiao.DantiaoMod;
import com.kyogi.dantiao.teleport.ToLobby;
import com.kyogi.dantiao.util.Positions;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


public class CompulsoryTeleport {
	public static Map<String, Positions> players = new HashMap<>();

	@SubscribeEvent(priority = EventPriority.HIGHEST)
	public void onRespawn(PlayerEvent.PlayerRespawnEvent event) throws IOException {
		ServerPlayer player = (ServerPlayer) event.getEntity();
		String playerName = player.getName().getString();
		if (players.containsKey(playerName)) {
			if (DantiaoMod.getCacheHandler().getArea().getLobby() != null) {
				if (!player.isDeadOrDying()) {
					ToLobby.to(player);
					player.sendSystemMessage(Component.literal("&b已将你带回单挑大厅！"));
				}
			} else {
				player.teleportTo(players.get(playerName).getPosition().getX(), players.get(playerName).getPosition().getY(),players.get(playerName).getPosition().getZ());
			}
			players.remove(playerName);
		}
	}
}
