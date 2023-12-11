package com.kyogi.dantiao.event.arena;

import com.kyogi.dantiao.arenas.Arena;
import net.minecraft.server.level.ServerPlayer;


public class ArenaFinishEvent extends ArenaEventAbs  {
    private final ServerPlayer winner;
    private final ServerPlayer loser;
    private final Arena arena;

    /**
     * 某场比赛在非平局情况下结束时触发，不包括管理员强行结束的情况
     *
     * @param winner 胜利的玩家
     * @param loser  战败的玩家
     * @param arena  所在的竞技场
     */
    public ArenaFinishEvent(ServerPlayer winner, ServerPlayer loser, Arena arena) {
        this.winner = winner;
        this.loser = loser;
        this.arena = arena;
    }

    public ServerPlayer getWinner() {
        return winner;
    }

    public ServerPlayer getLoser() {
        return loser;
    }

    public Arena getArena() {
        return arena;
    }
}