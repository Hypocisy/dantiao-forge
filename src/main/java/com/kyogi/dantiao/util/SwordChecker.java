package com.kyogi.dantiao.util;


import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;

public class SwordChecker {
	public static boolean isHoldingSword(Player player) {
		ItemStack itemInHand = player.getMainHandItem();
		if (itemInHand.isEmpty()) {
			return false;
		}
        return itemInHand.getItem() instanceof SwordItem;
    }
}
