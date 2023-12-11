package com.kyogi.dantiao.arenas;

import com.kyogi.dantiao.DantiaoMod;
import com.kyogi.dantiao.caches.ArenaInfoCache;
import com.kyogi.dantiao.caches.DanCache;
import com.kyogi.dantiao.caches.PointCache;
import com.kyogi.dantiao.caches.RecordCache;
import com.kyogi.dantiao.configuration.ConfigManager;
import com.kyogi.dantiao.dan.DanHandler;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class SettleEnd {
    public static void settle(Arena arena, ServerPlayer winner, ServerPlayer loser, boolean isDraw) throws IOException {
        int time = arena.getTime();
        int startWay = arena.getStartWay();

        double player1Damage = arena.getDamage(arena.getPlayer1());
        double player1MaxDamage = arena.getMaxDamage(arena.getPlayer1());
        double player2Damage = arena.getDamage(arena.getPlayer2());
        double player2MaxDamage = arena.getMaxDamage(arena.getPlayer2());

        double winnerDamage, winnerMaxDamage, loserDamage, loserMaxDamage;
        if (arena.isPlayer1(winner)) {
            winnerDamage = player1Damage;
            winnerMaxDamage = player1MaxDamage;
            loserDamage = player2Damage;
            loserMaxDamage = player2MaxDamage;
        } else {
            winnerDamage = player2Damage;
            winnerMaxDamage = player2MaxDamage;
            loserDamage = player1Damage;
            loserMaxDamage = player1MaxDamage;
        }

        Calendar cal = Calendar.getInstance();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date = formatter.format(cal.getTime());
        ConfigManager configManager = DantiaoMod.getConfigManager();
        DanCache danCache = DantiaoMod.getCacheHandler().getDan();
        PointCache pointCache = DantiaoMod.getCacheHandler().getPoint();
        RecordCache recordCache = DantiaoMod.getCacheHandler().getRecord();

        String server = DantiaoMod.getConfigManager().getServerName();
        int loserResult = 0, winnerResult = 0;

        double pointsAwarded = 0;
        int winExpAwarded = 0, drawExpAwarded = 0, maxExpAwarded = 0;
        // 匹配赛
        if (startWay == 1) {
            pointsAwarded = configManager.getArenaRewardPoints();
            winExpAwarded = configManager.getArenaWinnerExp();
            drawExpAwarded = configManager.getArenaDrawsExp();
            maxExpAwarded = configManager.getArenaWinnerMaxExp();
        }
        // 邀请赛
        if (startWay == 2) {
            pointsAwarded = configManager.getArenaInvitingRewardPoints();
            winExpAwarded = configManager.getArenaInvitingWinnerExp();
            drawExpAwarded = configManager.getArenaInvitingDrawsExp();
            maxExpAwarded = configManager.getArenaInvitingWinnerMaxExp();
        }
        // 强制赛
        if (startWay == 3) {
            pointsAwarded = configManager.getArenaCompulsionRewardPoints();
            winExpAwarded = configManager.getArenaCompulsionWinnerExp();
            drawExpAwarded = configManager.getArenaCompulsionDrawsExp();
            maxExpAwarded = configManager.getArenaCompulsionWinnerMaxExp();
        }

        if (!isDraw) {
            loserResult = 1;
            winnerResult = 0;

            if (pointsAwarded != 0) {
                double now = pointCache.get(winner.getName().getString());
                pointCache.set(winner.getName().getString(), now + pointsAwarded);
                winner.sendSystemMessage(Component.literal("&b做的不错！奖励你 &d" + pointsAwarded + " &b点单挑积分");
            }

            recordCache.addWins(winner.getName().getString());
            recordCache.addLoses(loser.getName().getString());
        } else {
            recordCache.addDraws(winner.getName().getString());
            recordCache.addDraws(loser.getName().getString());
        }

        ArenaInfoCache arenaInfoCache = DantiaoMod.getCacheHandler()
                .getArenaInfoCache();
        String displayName = arenaInfoCache.get(arena.getArenaName())
                .getDisplayName();
        String editName = arena.getArenaName();
        int winnerExpChange = 0;
        int loserExpChange = 0;
        //有赢家产生，刷新排名，给予经验
        if (!isDraw) {
            RankingCache rankingCache = getInstance().getCacheHandler()
                    .getRanking();
            Ranking ranking = getInstance().getRanking();

            int winnerRank = ranking.getWin(winner);
            int loserRank = ranking.getWin(loser);
            int winnerRank2 = ranking.getKD(winner);
            int loserRank2 = ranking.getKD(loser);

            int winnerOrder = 0;
            if (rankingCache.getWin() != null) {
                for (int i = 0; i < rankingCache.getWin().size(); i++) {
                    if (rankingCache.getWin().get(i).split("\\|")[0].equals(winner)) {
                        winnerOrder = i;
                    }
                }
            }
            int loserOrder = 0;
            if (rankingCache.getWin() != null) {
                for (int i = 0; i < rankingCache.getWin().size(); i++) {
                    if (rankingCache.getWin().get(i).split("\\|")[0].equals(loser)) {
                        loserOrder = i;
                    }
                }
            }

            if (winnerOrder <= loserOrder) {// winner本来就强过loser 等号则代表没有排行数据，winner优先
                ranking.rank(winner + "|" + recordCache.getWins(winner), true);
                ranking.rank(loser + "|" + recordCache.getWins(loser), true);
            } else {
                ranking.rank(loser + "|" + recordCache.getWins(loser), true);
                ranking.rank(winner + "|" + recordCache.getWins(winner), true);
            }

            int winnerOrder2 = 0;
            if (rankingCache.getKD() != null) {
                for (int i = 0; i < rankingCache.getKD().size(); i++) {
                    if (rankingCache.getKD().get(i).split("\\|")[0].equals(winner)) {
                        winnerOrder2 = i;
                    }
                }
            }
            int loserOrder2 = 0;
            if (rankingCache.getKD() != null) {
                for (int i = 0; i < rankingCache.getKD().size(); i++) {
                    if (rankingCache.getKD().get(i).split("\\|")[0].equals(loser)) {
                        loserOrder2 = i;
                    }
                }
            }
            if (winnerOrder2 <= loserOrder2) {// winner本来就强过loser
                // 等号则代表没有排行数据，winner优先
                if (recordCache.getLoses(winner) != 0) {
                    ranking.rank(
                            winner
                                    + "|"
                                    + ((double) recordCache.getWins(winner) / (double) recordCache
                                    .getLoses(winner)), false);
                } else {
                    ranking.rank(
                            winner + "|" + ((double) recordCache.getWins(winner)),
                            false);
                }

                if (recordCache.getLoses(loser) != 0) {
                    ranking.rank(
                            loser
                                    + "|"
                                    + ((double) recordCache.getWins(loser) / (double) recordCache
                                    .getLoses(loser)), false);
                } else {
                    ranking.rank(loser + "|"
                            + ((double) recordCache.getWins(loser)), false);
                }
            } else {
                if (recordCache.getLoses(loser) != 0) {
                    ranking.rank(
                            loser
                                    + "|"
                                    + ((double) recordCache.getWins(loser) / (double) recordCache
                                    .getLoses(loser)), false);
                } else {
                    ranking.rank(loser + "|"
                            + ((double) recordCache.getWins(loser)), false);
                }
                if (recordCache.getLoses(winner) != 0) {
                    ranking.rank(
                            winner
                                    + "|"
                                    + ((double) recordCache.getWins(winner) / (double) recordCache
                                    .getLoses(winner)), false);
                } else {
                    ranking.rank(
                            winner + "|" + ((double) recordCache.getWins(winner)),
                            false);
                }
            }

            if (winnerRank != ranking.getWin(winner) && ranking.getWin(winner) != 0) {
                sm("&b胜场排名发生变更！&e{before}->{now}", winner, "before now", new String[]{
                        "" + winnerRank, "" + ranking.getWin(winner)});
            }
            if (loserRank != ranking.getWin(loser) && ranking.getWin(loser) != 0) {
                sm("&b胜场排名发生变更！&e{before}->{now}", loser, "before now", new String[]{
                        "" + loserRank, "" + ranking.getWin(loser)});
            }
            if (winnerRank2 != ranking.getKD(winner) && ranking.getWin(winner) != 0) {
                sm("&bKD排名发生变更！&e{before}->{now}", winner, "before now", new String[]{
                        "" + winnerRank2, "" + ranking.getKD(winner)});
            }
            if (loserRank2 != ranking.getKD(loser) && ranking.getWin(loser) != 0) {
                sm("&bKD排名发生变更！&e{before}->{now}", loser, "before now", new String[]{
                        "" + loserRank2, "" + ranking.getKD(loser)});
            }

            boolean b1 = arena.isp1(winner);
            int winnerExp;
            int loserExp;
            if (arena.getExp(b1) > maxExpAwarded) {
                winnerExp = maxExpAwarded;
            } else {
                winnerExp = arena.getExp(b1);
            }
            winnerExp = winnerExp + winExpAwarded;
            loserExp = (int) winnerExp / 3;

            DanHandler danHandler = DantiaoMod.getDanHandler();

            boolean protection;
            int protectionExp = configManager.getProtectionExp();
            int winnerExpNow = danCache.get(winner);
            int loserExpNow = danCache.get(loser);
            if (protectionExp == 0) { // 有保护措施
                protection = false;
            } else {
                if (winnerExpNow - loserExpNow >= protectionExp
                        || loserExpNow - winnerExpNow >= protectionExp) {
                    protection = true;
                } else {
                    protection = false;
                }
            }

            int loserExpShow;
            int winnerExpShow;
            if (protection) {
                loserExpShow = 0;
                winnerExpShow = 0;
                sm("&c双方段位差距过大，段位经验不会变更", winner, loser);
            } else {
                changeExp(winner, winnerExpNow + winnerExp);
                winnerExpChange = winnerExp;
                winnerExpShow = winnerExp;
                loserExpShow = loserExp;
                if (loserExpNow - loserExp > dh.getThreshold()) {
                    changeExp(loser, loserExpNow - loserExp); // 正常扣除经验
                    loserExpChange = 0 - loserExp;
                } else {
                    if (loserExpNow > dh.getThreshold()) {
                        loserExpShow = loserExpNow - dh.getThreshold();
                        changeExp(loser, dh.getThreshold()); // 设置为保底经验
                        loserExpChange = 0 - (loserExpNow - dh.getThreshold());
                    } else {
                        loserExpShow = 0; // 不扣经验
                    }
                }
            }

            sml("&7============================================================| |                       &b比赛结束！|        &7恭喜获得了胜利，期待你下一次更加精彩得表现！|                  &7同时获得了 &a{exp} &7经验| |&7============================================================",
                    winner, "exp", new String[]{"" + winnerExpShow});
            if (loser != null) {
                sml("&7============================================================| |                     &b比赛结束！|           &7你没有获胜，不要灰心，再接再厉！|                &7同时损失了 &c{exp} &7经验| |&7============================================================",
                        loser, "exp", new String[]{"" + loserExpShow});
            }
            if (displayName == null) {
                displayName = arena.getName();
            } else {
                displayName = displayName.replace("&", "§");
            }
            bc(gm("&b[战报]: &7玩家 &e{winner} &7在单挑赛场&r{arenaname}&r&7上以 &6{time}秒 &7击败玩家 &e{loser}",
                    null, "winner arenaname time loser", new String[]{winner,
                            displayName, arena.getTime() + "", loser}), startWay);

            //连胜
            int winTime = recordCache.getWinningStreakTimes(winner);
            int maxWinTime = recordCache.getMaxWinningStreakTimes(winner);
            recordCache.addWinningStreakTimes(winner);
            if (winTime + 1 > maxWinTime) {
                recordCache.addMaxWinningStreakTimes(winner);
            }
            recordCache.clearWinningStreakTimes(loser);
            int reportTimes = configManager.getBroadcastWinningStreakTimes();
            if (reportTimes == 0) {
                reportTimes = 3;
            }
            if (winTime + 1 >= reportTimes) {
                bc(gm("&a[恭喜]: &7玩家 &e{player} &7在竞技场上完成了 &b{times} &7连胜！", null,
                        "player times", new String[]{winner, (winTime + 1) + ""}), startWay);
            }
        } else { // 平局
            changeExp(winner, danCache.get(winner) + drawExpAwarded);
            changeExp(loser, danCache.get(loser) + drawExpAwarded);
            winnerExpChange = drawExpAwarded;
            loserExpChange = winnerExpChange;
            sml("&7============================================================| |                    &b比赛结束！|          &7比赛超时！未决出胜负，判定为平局！|          &7同时获得了 &a{exp} &7经验| |&7============================================================",
                    winner, "exp", new String[]{"" + drawExpAwarded});
            if (loser != null) {
                sml("&7============================================================| |                    &b比赛结束！|          &7比赛超时！未决出胜负，判定为平局！|          &7同时获得了 &a{exp} &7经验| |&7============================================================",
                        loser, "exp", new String[]{"" + drawExpAwarded});
            }
            if (displayName == null) {
                displayName = editName;
            } else {
                displayName = displayName.replace("&", "§");
            }
            bc(gm("&b[战报]: &7玩家 &e{p1} &7与 &e{p2} &7在单挑赛场&r{arenaname}&r&7上打成平手，实为精妙！",
                    null, "p1 arenaname p2", new String[]{winner,
                            displayName, loser}), startWay);
        }

        getInstance().getHD().setIsNeedRefresh(true);
        recordCache.add(loser, date, winner, server, time, loserDamage,
                loserMaxDamage, loserResult, startWay, loserExpChange, editName);
        recordCache.add(winner, date, loser, server, time, winnerDamage,
                winnerMaxDamage, winnerResult, startWay, winnerExpChange, editName);
        recordCache.refreshServerTotalGameTimes();
    }
}
