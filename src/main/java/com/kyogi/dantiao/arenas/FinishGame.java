package com.kyogi.dantiao.arenas;

import com.kyogi.dantiao.DantiaoMod;
import com.kyogi.dantiao.caches.EnergyCache;
import com.kyogi.dantiao.configuration.ConfigManager;
import com.kyogi.dantiao.dan.CustomDan;
import com.kyogi.dantiao.dan.DanHandler;
import com.kyogi.dantiao.effect.WinFirework;
import com.kyogi.dantiao.event.arena.ArenaDrawFinishEvent;
import com.kyogi.dantiao.event.arena.ArenaEventAbs;
import com.kyogi.dantiao.event.arena.ArenaFinishEvent;
import com.kyogi.dantiao.event.game.CompulsoryTeleport;
import com.kyogi.dantiao.teleport.ToLobby;
import com.kyogi.dantiao.teleport.ToLogLocation;
import com.kyogi.dantiao.util.Debug;
import com.kyogi.dantiao.util.Positions;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.common.MinecraftForge;

import java.io.IOException;
import java.util.List;

import static com.kyogi.dantiao.util.SyncBroadcast.broadcast;


public class FinishGame {
    public static void normalEnd(String arenaName, ServerPlayer winner, ServerPlayer loser,
                                 boolean isDraw) {// 正常结束
        Arena arena = DantiaoMod.getArenaManager().getArena(arenaName);
        if (arena == null || !arena.isArenaEnable()) {
            return;
        }
        if (winner != null && loser != null) {
            respawnOrReturnToLobby(winner, arena);
            respawnOrReturnToLobby(loser, arena);
            try {
                EnergyCache energyCache = DantiaoMod.getCacheHandler().getEnergy();
                if (energyCache.isEnable()) {
                    double energyNeeded = energyCache.getEnergyNeeded();
                    energyCache.set(winner, energyCache.get(winner.toString()) - energyNeeded);
                    energyCache.set(loser, energyCache.get(loser.toString()) - energyNeeded);
                }

                arena.setCanTeleport(true);
                arena.setWatchersTeleport(true);
                List<ServerPlayer> watchers = arena.getWatchers();
                Positions lobbyLocation = DantiaoMod.getCacheHandler().getArea()
                        .getLobby();
                if (lobbyLocation != null) {
                    ToLobby.to(winner);
                    ToLobby.to(loser);
                    winner.sendSystemMessage(Component.literal("&b已将你带回单挑大厅！"));
                    loser.sendSystemMessage(Component.literal("&b已将你带回单挑大厅！"));

                    for (ServerPlayer watcher : watchers) {
                        if (watcher != null) {
                            ToLobby.to(watcher);
                            watcher.sendSystemMessage(Component.literal("&b已将你带回单挑大厅！"));
                        }
                    }
                } else {
                    if (arena.isPlayer1(winner)) {
                        Positions winnerLocation = arena.getLocation(arena.getPlayer1());
                        Positions loserLocation = arena.getLocation(arena.getPlayer2());
                        ToLogLocation.to(winner, loser, winnerLocation, loserLocation);
                    } else {
                        Positions winnerLocation = arena.getLocation(arena.getPlayer2());
                        Positions loserLocation = arena.getLocation(arena.getPlayer1());
                        ToLogLocation.to(winner, loser, winnerLocation, loserLocation);
                    }
                    for (ServerPlayer watcher : watchers) {
                        if (watcher != null) {
                            watcher.sendSystemMessage(Component.literal("&b[报告] &7你所观战的竞技场上的比赛已结束，请自行传送回家..."));
                        }
                    }
                }// 回到原处
                ConfigManager configManager = DantiaoMod.getConfigManager();
                boolean isFirework = false;
                int startWay = arena.getStartWay();
                if (startWay == 1) {
                    isFirework = configManager.isArenaWinnerPlayFirework();
                }
                if (startWay == 2) {
                    isFirework = configManager.isArenaInvitingWinnerPlayFirework();
                }
                if (isFirework) {
                    WinFirework.setFirework(new Positions(winner));
                    winner.sendSystemMessage(Component.literal("&a[v]WOW！服务器专门为你的获胜放了一朵烟花~"));
                }

                ArenaManager.busyArenasName.remove(arena.getArenaName());
                DantiaoMod.getSingleLineChartData().addGameTimes();

                ArenaEventAbs event;
                if (isDraw) {
                    event = new ArenaDrawFinishEvent(winner, loser, arena);
                } else {
                    event = new ArenaFinishEvent(winner, loser, arena);
                }
                MinecraftForge.EVENT_BUS.post(event);

                try {
                    SettleEnd.settle(arena, winner, loser, isDraw);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                DanHandler danHandler = DantiaoMod.getDanHandler();
                danHandler.refreshPlayerDan(winner);
                danHandler.refreshPlayerDan(loser);
                if (arena.isPlayer1(winner)) {
                    if (arena.getPlayerDan(winner) != null) {
                        CustomDan danBefore = arena.getPlayerDan(winner);
                        CustomDan danNow = danHandler.getPlayerDan(winner);
                        if (!danBefore.equals(danNow)) {
                            broadcast(Component.literal("&a[恭喜]: &7玩家 &e" + winner.getName().getString() + " &7的单挑段位成功升到了" + "&r" + danNow.getDisplayName()), startWay);
                        }
                    }
                } else {
                    CustomDan danNow = danHandler.getPlayerDan(winner);
                    if (danNow != null) {
                        broadcast(Component.literal("&a[恭喜]: &7玩家 &e" + winner.getName().getString() + danNow.getDisplayName() + " &7突破了无段位的身份，首次获得了段位：&r{dan}&7！祝TA在单挑战斗的路上越走越远！"), startWay);
                    }
                }
                if (isDraw) {
                    if (arena.isPlayer1(loser)) {
                        if (arena.getPlayerDan(loser) != null) {
                            List<CustomDan> danList = danHandler.getCustomDans();
                            for (int i = 0; i < danList.size(); i++) {
                                CustomDan danBefore = arena.getPlayerDan(loser);
                                CustomDan danNow = danHandler.getPlayerDan(loser);
                                if (!danBefore.equals(danNow)) {
                                    broadcast(Component.translatable("&a[恭喜]: &7玩家 &e" + winner.getName().getString() + " &7的单挑段位成功升到了" + "&r" + danNow.getDisplayName()), startWay);
                                }
                            }
                        }
                    } else {
                        CustomDan danNow = danHandler.getPlayerDan(loser);
                        broadcast(Component.literal("&a[恭喜]: &7玩家 &e" + winner.getName().getString() + danNow.getDisplayName() + " &7突破了无段位的身份，首次获得了段位：&r{dan}&7！祝TA在单挑战斗的路上越走越远！"), startWay);
                    }
                }
                arena.finish();
            } catch (Exception exception) {
                exception.printStackTrace();
                ArenaManager.busyArenasName.remove(arena.getArenaName());
                arena.finish();
                Debug.send(Component.literal("§c比赛结束时出现异常！请将报错信息截图反馈给本插件作者"),
                        Component.literal("Some errors occurred when the game finished!"));
            }
        }

    }

    private static void respawnOrReturnToLobby(ServerPlayer player, Arena arena) {
        try {
            if (player != null && player.isDeadOrDying()) {
                player.respawn();
                player.setHealth(player.getMaxHealth());
                player.getFoodData().setFoodLevel(20);
            }
        } catch (Exception e) {
            CompulsoryTeleport.players.put(player.toString(),
                    arena.getLocation(player));
        }
    }

    public static void compulsoryEnd(String name, ServerPlayer finisher) throws IOException {// 强制结束，不予记录
        Arena arena = DantiaoMod.getArenaManager().getArena(name);
        if (arena == null) {
            finisher.sendSystemMessage(Component.literal("&c[x]不存在的竞技场，请检查输入"));
            return;
        }
        if (!arena.isArenaEnable()) {
            finisher.sendSystemMessage(Component.literal("&c[x]这个竞技场还没有比赛呢！"));
            return;
        }

        ServerPlayer player1 = arena.getPlayer1();
        ServerPlayer player2 = arena.getPlayer2();

        arena.setWatchersTeleport(true);
        List<ServerPlayer> watchers = arena.getWatchers();
        Positions lobbyLocation = DantiaoMod.getCacheHandler().getArea()
                .getLobby();
        if (lobbyLocation != null) {
            if (player1 != null && player2 != null) {
                ToLobby.to(player1);
                player1.sendSystemMessage(Component.literal("&b已将你带回单挑大厅！"));
                ToLobby.to(player2);
                player2.sendSystemMessage(Component.literal("&b已将你带回单挑大厅！"));
            }
            for (ServerPlayer watcher : watchers) {
                if (watcher != null) {
                    ToLobby.to(watcher);
                    watcher.sendSystemMessage(Component.literal("&b已将你带回单挑大厅！"));
                }
            }
        } else {
            if (arena.isPlayer1(player1)) {
                Positions winnerLocation = arena.getLocation(player1);
                Positions loserLocation = arena.getLocation(player1);
                ToLogLocation.to(player1, player2, winnerLocation, loserLocation);
                for (ServerPlayer watcher : watchers) {
                    if (watcher != null) {
                        watcher.sendSystemMessage(Component.literal("&b[报告] &7你所观战的竞技场上的比赛已结束，请自行传送回家..."));
                    }
                }
            }
        }// 回到原处

        arena.finish();
        if (player1 != null) {
            player1.sendSystemMessage(Component.literal("&b&l比赛被管理员强制结束！本局比赛不会被记录！"));
        }
        if (player2 != null) {
            player2.sendSystemMessage(Component.literal("&b&l比赛被管理员强制结束！本局比赛不会被记录！"));
        }
        finisher.sendSystemMessage(Component.literal("&a[v]已强制停止"));
        ArenaManager.busyArenasName.remove(arena.getArenaName());
    }
}
