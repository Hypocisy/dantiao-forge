package com.kyogi.dantiao.event;

import com.kyogi.dantiao.DantiaoMod;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;


public class JoinServer implements Listener {
	@SubscribeEvent
	public void onPlayerJoin(PlayerEvent.PlayerLoggedInEvent event) {
		//载入缓存
		String name = event.getEntity().getName().getString();
		CacheHandler cacheHandler = DantiaoMod.getInstance().getCacheHandler();
		cacheHandler.getDan().load(name);
		cacheHandler.getEnergy().load(name);
		cacheHandler.getLanguageFile().load(name);
		cacheHandler.getPoint().load(name);
		cacheHandler.getRecord().load(name, null);
		//首次有玩家进入，初始化全息图
		getInstance().getHD().initial();
	}
}
