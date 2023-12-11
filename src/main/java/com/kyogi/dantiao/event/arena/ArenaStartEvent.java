package com.kyogi.dantiao.event.arena;

import com.kyogi.dantiao.arenas.Arena;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.eventbus.api.Event;

public class ArenaStartEvent extends Event {

    private final ServerPlayer player1;
    private final ServerPlayer player2;
    private final Arena arena;
    /**
     * 玩家在单挑积分商城购物时调用（前提是有足够的积分）
     * @param player1 玩家1
     * @param player2 玩家2
     * @param arena 竞技场
     */
    public ArenaStartEvent(ServerPlayer player1, ServerPlayer player2, Arena arena){
        this.player1 = player1;
        this.player2 = player2;
        this.arena = arena;
    }

    public ServerPlayer getPlayer1() {
        return player1;
    }

    public ServerPlayer getPlayer2() {
        return player2;
    }

    public Arena getArena() {
        return arena;
    }
}
