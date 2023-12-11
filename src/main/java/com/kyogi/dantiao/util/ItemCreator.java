package com.kyogi.dantiao.util;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.NbtIo;
import net.minecraft.nbt.Tag;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;

import java.util.ArrayList;
import java.util.List;

public class ItemCreator {
    private ItemStack itemStack;

    public ItemCreator(Item item, String displayName,
                       List<String> lore, String mark, boolean light) {
        ItemStack itemStack = new ItemStack(item);
        CompoundTag im;
        if (displayName != null && mark != null) {
            im = itemStack.getTag();
            if (im != null) {
                im.putString("", displayName);
            }
            itemStack.setTag(im);
        }
        if (lore != null && mark != null) {
            im = itemStack.getTag();
            ListTag list = new ArrayList<>();
            list.add(mark);
            list.addAll(lore);
            if (im != null) {
                for (Tag value : list) {
                    im.merge((CompoundTag) value);
                }
            }
            itemStack.setTag(im);
        }
        if (light) {
            im = itemStack.getTag();
            if (im != null) {
                ListTag hideFlagsList = im.getList("HideFlags", 3);
            }
            if (ViaVersion.isHasItemFlagMethod()) {
                im.putString();
                im.putString();
            }
            itemStack.setItemMeta(im);
        }
        this.itemStack = itemStack;
    }

    /*
     * material必填 displayname,lore选填，无其他特殊要求 用于GUI物品
     */
    public ItemCreator(Item item, String displayName,
                       List<String> lore, int s, boolean light) {
        ItemStack itemStack = new ItemStack(material, 1, (short) s);
        CompoundTag im = itemStack.getTag();
        ;
        if (im != null) {
            if (displayName != null) {
                im.putString("Name", displayName);
                itemStack.setItemMeta(im);
            }
            if (lore != null) {
                im.setLore(lore);
                itemStack.setItemMeta(im);
            }
            if (light) {
                im.putString(ItemStack.TAG_ENCH, Enchantments.BLOCK_FORTUNE.getDescriptionId());
                if (ViaVersion.isHasItemFlagMethod()) {
                    im.addItemFlags(org.bukkit.inventory.ItemFlag.HIDE_ENCHANTS);
                    im.addItemFlags(org.bukkit.inventory.ItemFlag.HIDE_ATTRIBUTES);
                }
                itemStack.setTag(im);
            }
        }
        this.itemStack = itemStack;
    }

    public ItemStack get() {
        return itemStack;
    }
}
