package com.kyogi.dantiao.event.arena;

import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.eventbus.ListenerList;
import net.minecraftforge.eventbus.api.Event;

public class DanExpChangedEvent extends Event {
    private static final ListenerList listenerList = new ListenerList();
    private boolean cancelled;
    private ServerPlayer player;
    private int before;
    private int now;
    /**
	 * 玩家在经验变更时调用
	 * @param player 玩家
	 * @param before 变更前的经验
	 * @param now 变更后的经验
	 */
    public DanExpChangedEvent(ServerPlayer player, int before, int now)
    {
      this.player = player;
      this.before = before;
      this.now = now;
	}
    
    public ServerPlayer getPlayer() {
    	return player;
    }
    
    public int getBefore() {
        return before;
    }
    
    public int getNow() {
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
