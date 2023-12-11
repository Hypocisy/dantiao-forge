package com.kyogi.dantiao.event;


import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.eventbus.api.Event;

public class EnergyChangedEvent extends Event {
    private boolean cancelled;
    private final ServerPlayer player;
    private final double before;
    private final double now;
    /**
	 * 精力值变更时调用，前提是服务器的精力值系统允许使用
	 * @param player 玩家
	 * @param d 变更前的精力值
	 * @param amount 变更后的精力值
	 */
    public EnergyChangedEvent(ServerPlayer player,double d,double amount)
    {
      this.player = player;
      this.before = d;
      this.now = amount;
	}
    
    public ServerPlayer getPlayer() {
    	return player;
    }
    
    public double getBefore() {
        return before;
    }
    
    public double getNow() {
    	return now;
    }

    public boolean isCancelled() {
        return cancelled;
    }
    
    public void setCancelled(boolean cancel) {
        this.cancelled = cancel;
    }
}
