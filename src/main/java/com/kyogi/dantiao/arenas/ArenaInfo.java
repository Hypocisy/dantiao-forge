package com.kyogi.dantiao.arenas;

import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;

import java.util.ArrayList;
import java.util.List;

public class ArenaInfo {
    private final Component editName;
    private final Component displayName;
    private final Vec3 pointA;
    private final Vec3 pointB;
    private List<Component> commandList;
    private Vec3 watchingPoint;
    private boolean isKitMode;
    private List<ItemStack> kit;

    public ArenaInfo(Component editName, Component displayName, Vec3 pointA, Vec3 pointB, List<Component> commandList, Vec3 watchingPoint, List<ItemStack> kit){

        this.editName = editName;
        this.displayName = displayName;
        this.pointA = pointA;
        this.pointB = pointB;
        if (commandList.isEmpty()){
            this.commandList = new ArrayList<>();
        }else {
            this.commandList = commandList;
        }
        this.watchingPoint = watchingPoint;
        if (kit.isEmpty()){
            isKitMode = false;
        }else {
            isKitMode = true;
            this.kit = kit;
        }
    }

    public Component getEditName() {
        return editName;
    }

    public Component getDisplayName() {
        return displayName;
    }

    public Vec3 getPointA() {
        return pointA;
    }

    public Vec3 getPointB() {
        return pointB;
    }

    public List<Component> getCommandList() {
        return commandList;
    }

    public void setCommandList(List<Component> commandList) {
        if (commandList.isEmpty()){
            this.commandList = new ArrayList<>();
        } else {
            this.commandList = commandList;
        }
    }

    public Vec3 getWatchingPoint() {
        return watchingPoint;
    }

    public void setWatchingPoint(Vec3 watchingPoint) {
        this.watchingPoint = watchingPoint;
    }

    public boolean isKitMode() {
        return isKitMode;
    }

    public void setKitMode(boolean kitMode) {
        isKitMode = kitMode;
    }

    public List<ItemStack> getKit() {
        return kit;
    }

    public void setKit(List<ItemStack> kit) {
        if (kit.isEmpty()) {
            this.kit = new ArrayList<>();
        } else {
            this.kit = kit;
        }
    }
}
