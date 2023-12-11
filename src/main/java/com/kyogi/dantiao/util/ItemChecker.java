package com.kyogi.dantiao.util;

import com.kyogi.dantiao.DantiaoMod;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.ForgeRegistries;

public class ItemChecker {
    private boolean hasItem = false;

    public ItemChecker(Player player, Tag lore) {
        Inventory inv = player.getInventory();
        // check if player has item
        for (ItemStack itemStack : inv.items) {
            if (!itemStack.isEmpty() && itemStack.getTag() != null && itemStack.getTag().getCompound("display").contains("Lore", Tag.TAG_LIST)) {
                ListTag loreTag = itemStack.getTag().getCompound("display").getList("Lore", Tag.TAG_LIST);
                for (Tag tag : loreTag) {
                    if (tag instanceof StringTag) {
                        if (tag.equals(lore)) {
                            hasItem = true;
                        }
                    }
                }
            }
        }
    }

    public ItemChecker(Player player, ItemStack itemStack) {
        Inventory inv = player.getInventory();
        for (int slot = 0; slot < inv.getContainerSize(); slot++) {
            if (!inv.getItem(slot).equals(itemStack, false)) {
                ItemStack playerItemstack = inv.getItem(slot);
                if (playerItemstack.is(itemStack.getItem())) {
                    hasItem = true;
                    DantiaoMod.LOGGER.info("玩家拥有相同的物品");
                    break;
                }
            }
        }
    }

    public static ItemStack getItemstark(String itemName) {

        ResourceLocation itemLocation = new ResourceLocation(itemName);
        if (ForgeRegistries.ITEMS.containsKey(itemLocation)) {
            Item item = ForgeRegistries.ITEMS.getValue(itemLocation);
            // 创建物品栈实例
            if (item != null) {
                return new ItemStack(item);
            }
        }

        return ItemStack.EMPTY;
    }

    public boolean isHasItem() {
        return hasItem;
    }
}
