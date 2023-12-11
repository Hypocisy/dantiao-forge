package com.kyogi.dantiao.event.arena;


import net.minecraftforge.eventbus.ListenerList;
import net.minecraftforge.eventbus.api.Event;

public abstract class ArenaEventAbs extends Event {
    private static final ListenerList handlers = new ListenerList();

    @Override
    public ListenerList getListenerList() {
        return handlers;
    }
}
