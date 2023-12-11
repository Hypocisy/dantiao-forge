package com.kyogi.dantiao.caches;

import com.kyogi.dantiao.configuration.DataManager;
import com.kyogi.dantiao.encapsulation.ArenaInfo;
import com.kyogi.dantiao.util.Debug;
import com.kyogi.dantiao.util.Positions;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ArenaInfoCache {
	private final List<ArenaInfo> arenaInfoList = new ArrayList<>();

	public ArenaInfoCache() {
		try {
			List<String> arenaNameList = DataManager.getArenas();
			for (String editName : arenaNameList) {
				String displayName = DataManager.getArenaDisplayName(editName);
				BlockPos pointA = DataManager.getArenaPointA(editName).getPosition();
				BlockPos pointB = DataManager.getArenaPointB(editName).getPosition();
				List<String> commandList = DataManager.getArenaCommands(editName);
				BlockPos watchingPoint = DataManager.getArenaWatchingPoint(editName).getPosition();
				List<ItemStack> kits = DataManager.getKitList("testkit1");
				ArenaInfo arenaInfo = new ArenaInfo(editName, displayName, pointA,
						pointB, commandList, watchingPoint, kits);
				arenaInfoList.add(arenaInfo);
			}
			Debug.send(Component.literal("竞技场信息缓存已就绪"), Component.literal("The Arena info cache has been initialized"));
		} catch (Exception e) {
			Debug.send(Component.literal("§c竞技场信息缓存加载失败"), Component.literal("§cThe Arena info cache failed to initialize"));
			e.printStackTrace();
		}
	}

	public void saveArena(String editName, String displayName, BlockPos pointA,
			BlockPos pointB) {
		ArenaInfo arenaInfo = new ArenaInfo(editName, displayName, pointA,
				pointB, null, null, null);
		arenaInfoList.add(arenaInfo);
		(editName, displayName, pointA, pointB);
	}

	public void deleteArena(String editName) throws IOException {
		ArenaInfo arenaInfo = get(editName);
		if (arenaInfo != null) {
			arenaInfoList.remove(arenaInfo);
			DataManager.deleteArena(arenaInfo.getEditName());
		}
	}

	public ArenaInfo get(String editName) {
		for (ArenaInfo arenaInfo : arenaInfoList) {
			if (arenaInfo.getEditName().equals(editName)) {
				return arenaInfo;
			}
		}
		return null;
	}
	
	public int size() {
		return arenaInfoList.size();
	}

	public void setCommandList(String editName, List<String> commandList) throws IOException {
		int index = -1;
		for (ArenaInfo arenaInfo : arenaInfoList) {
			index++;
			if (arenaInfo.getEditName().equals(editName)) {
				break;
			}
		}
		if (index != -1) {
			List<String> list = commandList;
			if (list == null) {
				list = new ArrayList<>();
			}
			arenaInfoList.get(index).setCommandList(list);
			DataManager.setArenaCommands(editName, list);
		}
	}

	public void setWatchingPoint(String editName, Positions watchingPoint) throws IOException {
		int index = -1;
		for (ArenaInfo arenaInfo : arenaInfoList) {
			index++;
			if (arenaInfo.getEditName().equals(editName)) {
				break;
			}
		}
		if (index != -1) {
			arenaInfoList.get(index).setWatchingPoint(watchingPoint.getPosition());
			DataManager.setArenaWatchingPoint(editName, watchingPoint);
		}
	}
}
