package com.kyogi.dantiao.event.game;

import com.kyogi.dantiao.DantiaoMod;
import com.kyogi.dantiao.arenas.Arena;
import com.kyogi.dantiao.arenas.ArenaManager;
import com.kyogi.dantiao.arenas.FinishGame;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class EndGame {
    // 一方选手死亡，无论因素，判定“非平局结束比赛”
    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void onDTDeath(LivingDeathEvent event) {
        LivingEntity entity = event.getEntity();
        if (entity instanceof ServerPlayer player) {
            ArenaManager arenaManager = DantiaoMod.getArenaManager();
            if (arenaManager.isPlayerBusy(player)) {// 属于比赛玩家，对方取胜
                String playerOfArenaName = arenaManager.getPlayerOfArena(player);
                Arena arena = arenaManager.getArena(playerOfArenaName);
                if (arena.isPlayer1(player)){
                    ServerPlayer winner = arena.getPlayer2();
                    ServerPlayer loser = arena.getPlayer1();
                    FinishGame.normalEnd(playerOfArenaName, winner, loser, false);
                }else {
                    ServerPlayer winner = arena.getPlayer1();
                    ServerPlayer loser = arena.getPlayer2();
                    FinishGame.normalEnd(playerOfArenaName, winner, loser, false);
                }
            }
        }
    }

    // 一方玩家下线，无论因素，判定“非平局结束比赛”
    @SubscribeEvent
    public void onDTLeaveGame(PlayerEvent.PlayerLoggedOutEvent event) {
        ServerPlayer player = (ServerPlayer) event.getEntity();
        ArenaManager arenaManager = DantiaoMod.getArenaManager();
        if (arenaManager.isPlayerBusy(player)) {// 属于比赛玩家，对方取胜
            String playerOfArenaName = arenaManager.getPlayerOfArena(player);
            Arena arena = arenaManager.getArena(playerOfArenaName);
            if (arena.isPlayer1(player)) {
                ServerPlayer winner = arena.getPlayer1();
                ServerPlayer loser = arena.getPlayer2();
                winner.sendSystemMessage(Component.literal("&b对方下线了！系统判定你胜利！"));
                FinishGame.normalEnd(playerOfArenaName, winner, loser, false);
            }else {
                ServerPlayer winner = arena.getPlayer2();
                ServerPlayer loser = arena.getPlayer1();
                winner.sendSystemMessage(Component.literal("&b对方下线了！系统判定你胜利！"));
                FinishGame.normalEnd(playerOfArenaName, winner, loser, false);
            }
        }
    }
}
