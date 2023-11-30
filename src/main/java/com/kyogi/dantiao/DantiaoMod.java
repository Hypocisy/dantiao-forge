package com.kyogi.dantiao;

import com.kyogi.dantiao.caches.CacheEvent;
import com.mojang.logging.LogUtils;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;
import sun.tools.jconsole.JConsole;

@Mod(DantiaoMod.MOD_ID)
public class DantiaoMod
{
    public static final String MOD_ID = "dantiao";
    public static final Logger LOGGER = LogUtils.getLogger();
    public static DantiaoMod Instance;
    public JConsole getConfigMananger;

    public DantiaoMod()
    {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
//        modEventBus.addListener(this::common);
        modEventBus.addListener(this::common);
        MinecraftForge.EVENT_BUS.register(this);
    }
    public void common(FMLCommonSetupEvent event){
        new CacheEvent();
    }
    @SubscribeEvent
    public void onPlayerJoin(PlayerEvent.PlayerLoggedInEvent event){
        if (event.getEntity() instanceof ServerPlayer && event.getPhase().equals(PlayerEvent.PlayerLoggedInEvent.NameFormat)) {
        }
    }
    

    public static DantiaoMod getInstance() {
        return Instance;
    }

    public CacheEvent getCacheHandler() {
        return cacheHandler;
    }

    public static getConfigManager() {
    }

}
