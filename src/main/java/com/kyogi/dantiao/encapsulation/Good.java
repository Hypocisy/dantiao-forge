package com.kyogi.dantiao.encapsulation;

import net.minecraft.world.item.ItemStack;

public class Good {
    private int num;
    private ItemStack item;
    private double price;
    private String broadcast;
    private String description;
    private int salesVolumn;

    public Good(int num, ItemStack item,double price,String broadcast,String description,int salesVolumn) {
        this.num = num;
        this.item = item;
        this.price = price;
        this.broadcast = broadcast;
        this.description = description;
        this.salesVolumn = salesVolumn;
    }

    public int getNum() { return num; }

    public ItemStack getItemStack() { return item; }

    public double getPrice() { return price; }

    public String getBroadcast() { return broadcast; }

    public String getDescription() { return description; }

    public int getSalesVolumn() { return salesVolumn; }

    public void setBroadcast(String broadcast) { this.broadcast = broadcast; }

    public void setDescription(String description) { this.description = description; }

    public void updateSalesVolumn() { salesVolumn++; }
}
