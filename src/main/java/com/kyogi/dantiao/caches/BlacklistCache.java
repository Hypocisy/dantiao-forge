package com.kyogi.dantiao.caches;

import com.kyogi.dantiao.configuration.DataManager;
import com.kyogi.dantiao.util.Debug;
import net.minecraft.network.chat.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class BlacklistCache {
	private List<String> blacklist = new ArrayList<>();

	public List<String> get() {
		return blacklist;
	}

	public BlacklistCache() {
		try {
			blacklist = DataManager.getBlacklist();
			Debug.send(Component.literal("黑名单缓存已就绪"), Component.literal("The blacklist cache has been initialized"));
		} catch (Exception e) {
			Debug.send(Component.literal("§c黑名单缓存加载失败"), Component.literal("§cThe blacklist cache failed to initialize"));
		}
	}

	public void add(String name) throws IOException {
		blacklist.add(name);
		DataManager.setBlacklist(blacklist);
	}

	public void remove(String name) throws IOException {
		blacklist.remove(name);
		DataManager.setBlacklist(blacklist);
	}

	public void clear() throws IOException {
		blacklist.clear();
		DataManager.setBlacklist(blacklist);
	}
}
