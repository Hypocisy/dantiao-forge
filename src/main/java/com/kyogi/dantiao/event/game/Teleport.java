package com.kyogi.dantiao.event.game;

import com.kyogi.dantiao.DantiaoMod;
import com.kyogi.dantiao.arenas.Arena;
import com.kyogi.dantiao.arenas.ArenaManager;
import com.kyogi.dantiao.util.Debug;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.entity.EntityTeleportEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.io.IOException;

public class Teleport extends Event {
    @SubscribeEvent
    public void onLeaveGameWorld(PlayerEvent.PlayerChangedDimensionEvent event) {// 突然传送到别的世界去了
        ServerPlayer player = (ServerPlayer) event.getEntity();
        ArenaManager arenaManager = DantiaoMod.getInstance().getArenaManager();
        if (player.isLocalPlayer()) {
            return;
        }
        if (arenaManager.isPlayerBusy(player)) {
            Arena arena = arenaManager.getArena(arenaManager.getPlayerOfArena(player));
            if (!arena.isPlayersCanTeleport()) {
                try (Level playerLevel = player.level()) {
                    if (!event.getTo().registry().equals(playerLevel.dimension().registry())) {
                        event.setCanceled(true);
                        player.sendSystemMessage(Component.literal("&c[x]发生非法传送，已制止"));
                    }
                } catch (IOException e) {
                    Debug.send(Component.literal("玩家不存在，传送失败!"), Component.literal("Target player is not exist, Teleport error!"));
                }

            }
        }
    }

    @SubscribeEvent
    public void onTpToGamer(EntityTeleportEvent event) {// 场外玩家企图传送到场内玩家身边给TA武器什么的
        Entity entity = event.getEntity();
        if (entity instanceof Player){
            ArenaManager ah = DantiaoMod.getInstance().getArenaManager();
            Vec3 to = event.getTarget();
            for (String arenaEditName : ArenaManager.busyArenasName) {
                Arena arena = ah.getArena(arenaEditName);
                if (entity.is(arena.getPlayer1()) ||
                        entity.is(arena.getPlayer2())) {
                    continue;
                }
                BlockPos player1Location = arena.getLocation(arena.getPlayer1()).getPosition();
                BlockPos player2Location = arena.getLocation(arena.getPlayer2()).getPosition();

                if ((Math.abs(player1Location.getX() - to.x()) <= 2
                        && Math.abs(player1Location.getY() - to.y()) <= 2 && Math
                        .abs(player1Location.getZ() - to.z()) <= 2)
                        || (Math.abs(player2Location.getX() - to.x()) <= 2
                        && Math.abs(player2Location.getY()
                        - to.y()) <= 2 && Math
                        .abs(player2Location.getZ() - to.z()) <= 2)) {
                    event.setCanceled(true);
                    entity.sendSystemMessage(Component.literal("&c[x]发生非法传送，已制止"));
                }
            }
        }
    }
}
