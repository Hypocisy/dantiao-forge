package com.kyogi.dantiao.event.arena;


import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.eventbus.ListenerList;
import net.minecraftforge.eventbus.api.Event;

public class EnergyChangedEvent extends Event {
    private static final ListenerList listenerList = new ListenerList();
    private boolean cancelled;
    private ServerPlayer player;
    private double before;
    private double now;
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

    @Override
    public ListenerList getListenerList() {
        return listenerList;
    }
    
    public boolean isCancelled() {
        return cancelled;
    }
    
    public void setCancelled(boolean cancel) {
        this.cancelled = cancel;
    }
}
