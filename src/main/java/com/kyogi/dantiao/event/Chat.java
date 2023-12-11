package com.kyogi.dantiao.event;


import com.kyogi.dantiao.DantiaoMod;
import com.kyogi.dantiao.dan.DanHandler;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.event.ServerChatEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = DantiaoMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class Chat {
    @SubscribeEvent
    public void showDan(ServerChatEvent event) {
        if (!DantiaoMod.getConfigManager().isShowDanPrefix()) {
            return;
        }
        ServerPlayer player = event.getPlayer();
        DanHandler dh = DantiaoMod.getInstance().getDanHandler();
        String danDisplayName;
        if (dh.getPlayerDan(player) == null) {
            danDisplayName = DantiaoMod.getConfigManager().getDanBasicPrefix();
        } else {
            danDisplayName = dh.getPlayerDan(player).getDisplayName();
        }
        event.setMessage(Component.literal(danDisplayName.replace("&", "§") + event.getMessage()));
    }
}
