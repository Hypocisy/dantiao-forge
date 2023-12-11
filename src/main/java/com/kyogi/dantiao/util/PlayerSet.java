package com.kyogi.dantiao.util;


import com.kyogi.dantiao.DantiaoMod;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.storage.PlayerDataStorage

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class PlayerSet {
     public static List<String> get() {
    	 List<String> nameList = new ArrayList<>();
    	 for (ServerPlayer onlinePlayer : DantiaoMod.getMinecraftServer().getPlayerList().getPlayers()) {
    		 nameList.add(onlinePlayer.getName().getString());
    	 }
    	 for (Player offlinePlayer : getOfflinePlayers()) {
    		 nameList.add(offlinePlayer.getName());
    	 }
    	 return nameList;
     }

	private static Player[] getOfflinePlayers() {
		Path path = getSeenPlayers;
	}
}
