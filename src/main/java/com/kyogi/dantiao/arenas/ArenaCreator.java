package com.kyogi.dantiao.arenas;

import net.minecraft.core.BlockPos;

public class ArenaCreator {

    BlockPos pointA, pointB;

    String creator;

    public ArenaCreator(String creator){
        this.creator =creator;
    }

    public BlockPos getPointA() {
        return pointA;
    }

    public BlockPos getPointB() {
        return pointB;
    }

    public void setPointA(BlockPos pointA) {
        this.pointA = pointA;
    }

    public void setPointB(BlockPos pointB) {
        this.pointB = pointB;
    }

    public String getCreator() {
        return creator;
    }
}
