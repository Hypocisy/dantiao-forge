package com.kyogi.dantiao.event.sign;

import net.minecraft.world.level.block.SignBlock;
import net.minecraft.world.level.block.entity.SignBlockEntity;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
public class CreateSign {
	@SubscribeEvent
	public void onCreateMatchingSign(PlayerInteractEvent.EntityInteractSpecific event) {
		if (event.getTarget().is(SignBlockEntity))

		String content = event.getTarget();
		if (content.equals(gm("[单挑匹配]", null))) {
			event.setLine(0, "§9" + content);
			sm("&a[v]快捷木牌创建成功！", event.getPlayer());
		}
	}
}
