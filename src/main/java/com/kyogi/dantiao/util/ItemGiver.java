package com.kyogi.dantiao.util;

import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;

public class ItemGiver {
	private final boolean isReceive;
	private int freeSlot;

	public boolean getIsReceive() {
		return isReceive;
	}

	public ItemGiver(ServerPlayer player, ItemStack item) {
		freeSlot = player.getInventory().getFreeSlot();
		if (freeSlot != -1) {
			player.getInventory().setItem(freeSlot, item);
			isReceive = true;
			player.sendSystemMessage(Component.literal("&a[v]物品已发送"));
		} else {
			isReceive = false;
			player.sendSystemMessage(Component.literal("&c[x]背包满了，无法获取物品，请先为你的背包留出空位！"));
		}
	}
}
