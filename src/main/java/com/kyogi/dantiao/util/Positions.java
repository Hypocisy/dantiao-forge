package com.kyogi.dantiao.util;

import com.kyogi.dantiao.DantiaoMod;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class Positions {
    private final BlockPos position;
    private Vec3 rotation;
    private final Level level;

    public Positions(BlockPos position, Vec3 rotation, Level level){
        this.position = position;
        this.rotation = rotation;
        this.level = level;
    }

    public Positions(LivingEntity entity){
        this.position = entity.blockPosition();
        this.rotation = new Vec3(entity.getYHeadRot(), entity.getXRot(), 0);
        this.level = entity.level();
    }
    public Positions(BlockPos position, String levelName) {
        this.position = position;
        this.level = getServerLevelByName(DantiaoMod.getMinecraftServer(),levelName);
    }
    public Positions(BlockPos position, Level levelName) {
        this.position = position;
        this.level = levelName;
    }

    public BlockPos getPosition() {
        return position;
    }

    public Vec3 getRotation() {
        return rotation;
    }

    public Level getLevel() {
        return level;
    }
    public Component getLevelComponent() {
        String diamentionPath = this.getLevel().dimension().location().getPath();
        String diamentionNamespace = this.getLevel().dimension().location().getNamespace();
        return Component.literal(diamentionNamespace +":"+ diamentionPath);
    }
    public static Component getLevelComponent(ServerPlayer player) {
        try(Level playerLevel = player.level()){
            String dimensionPath = playerLevel.dimension().location().getPath();
            String dimensionNamespace = playerLevel.dimension().location().getNamespace();
            return Component.literal(dimensionNamespace +":"+ dimensionPath);
        }catch (Exception e){
            DantiaoMod.LOGGER.error(e.toString());
        }
        return Component.literal("Can not get player level");
    }
    public static ServerLevel getServerLevelByName(MinecraftServer server, String worldName){
        ResourceLocation dimensionId = new ResourceLocation(worldName);
        ResourceKey<Level> resourceKey = ResourceKey.create(Registries.DIMENSION, dimensionId);
        return server.getLevel(resourceKey);
    }
}
