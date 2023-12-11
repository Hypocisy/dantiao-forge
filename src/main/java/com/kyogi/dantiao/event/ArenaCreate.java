package com.kyogi.dantiao.event;

import com.kyogi.dantiao.DantiaoMod;
import com.kyogi.dantiao.arenas.ArenaCreator;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class ArenaCreate {
	@SubscribeEvent
	public void selectPoint(PlayerInteractEvent.EntityInteract event) {
		Player player = event.getEntity();
		String creatorName = player.getName().getString();
		ItemStack item = player.getMainHandItem();
		if (item.getTag() != null){

		}
		if (item.hasTag()&&item.getTag().contains("display")) {
			if (item.getItemMeta().getLore().get(0)
					.equals(PlayerItems.mark1)) {
				if (!player.hasPermission("dt.admin")) {
					sm("&c[x]无权限！", player);
					return;
				}
				if (!DantiaoMod.getInstance().getACS().getCreators()
						.contains(creatorName)) {
					player.sendSystemMessage(Component.literal("&c[x]请输入/dt a mode进入竞技场创建模式后再使用这个快捷创建工具！"));
					return;
				}
				event.setCanceled(true);
				ArenaCreator ac = DantiaoMod.getInstance().getArenaCreator()
						.getArenaCreatorByName(creatorName);
				if (event.getTarget()) {
					if (ac.getPointB() != null) {
						if (!ac.getPointB().getWorld().equals(player.level())) {
							sm("&c[x]两点必须处于同一世界！", player);
							return;
						}
						ac.setPointA(player.getLocation());
						sm("&a[v]A点设定完毕", player);
						return;
					}
					ac.setPointA(player.getLocation());
					sm("&a[v]A点设定完毕", player);
				}
				if (action.equals(Action.RIGHT_CLICK_AIR)) {
					if (ac.getPointA() != null) {
						if (!ac.getPointA().getWorld().equals(player.getWorld())) {
							sm("&c[x]两点必须处于同一世界！", player);
							return;
						}
						ac.setPointB(player.getLocation());
						sm("&a[v]B点设定完毕", player);
						return;
					}
					ac.setPointB(player.getLocation());
					sm("&a[v]B点设定完毕", player);
				}
				if (action.equals(LocalPlayer)
						|| action.equals()) {
					sm("&c[x]竞技场创建方式错误！请将创建工具拿在手上后点击空气，以将你当前所处的位置作为传送点！", player);
				}
			}
		}
	}
}
