package com.kyogi.dantiao.request;

import net.minecraft.server.level.ServerPlayer;

public class Request {
    private final ServerPlayer sender;
    private final ServerPlayer receiver;

    public Request(ServerPlayer sender, ServerPlayer receiver){
        this.sender = sender;
        this.receiver = receiver;
    }

    public ServerPlayer getSender() {
        return sender;
    }
    public ServerPlayer getReceiver() {
        return receiver;
    }
}
