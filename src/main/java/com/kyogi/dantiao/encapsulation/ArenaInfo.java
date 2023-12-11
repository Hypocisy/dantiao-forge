package com.kyogi.dantiao.encapsulation;

import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class ArenaInfo {
    private final String editName;
    private final String displayName;
    private final BlockPos pointA;
    private final BlockPos pointB;
    private List<String> commandList;
    private BlockPos watchingPoint;
    private boolean isKitMode;
    private List<ItemStack> kit;

    public ArenaInfo(String editName, String displayName, BlockPos pointA, BlockPos pointB, List<String> commandList, BlockPos watchingPoint, List<ItemStack> kit){
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

    public String getEditName() {
        return editName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public BlockPos getPointA() {
        return pointA;
    }

    public BlockPos getPointB() {
        return pointB;
    }

    public List<String> getCommandList() {
        return commandList;
    }

    public void setCommandList(List<String> commandList) {
        if (commandList.isEmpty()){
            this.commandList = new ArrayList<>();
        } else {
            this.commandList = commandList;
        }
    }

    public BlockPos getWatchingPoint() {
        return watchingPoint;
    }

    public void setWatchingPoint(BlockPos watchingPoint) {
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
