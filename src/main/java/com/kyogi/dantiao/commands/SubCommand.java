package com.kyogi.dantiao.commands;

import com.mojang.brigadier.Command;
import net.minecraft.server.level.ServerPlayer;

public abstract class SubCommand {
    private String[] names;

    public SubCommand(String... names) {
        this.names = names;
    }

    public abstract boolean onCommand(ServerPlayer sender, Command cmd, String label, String[] args);

    public String[] getNames() {
        return names;
    }
}
