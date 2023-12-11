package com.kyogi.dantiao.dan;

import com.kyogi.dantiao.DantiaoMod;
import net.minecraft.server.level.ServerPlayer;

public class ExpChange {
    public static void changeExp(ServerPlayer player, int exp) {
//        DanCache cache = DantiaoMod.getInstance().getCacheHandler().getDan();
//        int before = cache.get(player.getName());

//        Bukkit.getScheduler().runTask(
//                Main.getInstance(),
//                () -> {
//                    DanExpChangedEvent event = new DanExpChangedEvent(player,
//                            before, exp);
//                    Bukkit.getServer().getPluginManager().callEvent(event);
//                    if (!event.isCancelled()) {
//                        cache.set(player.getName(), exp);
//                    }
//                });
    }
}
