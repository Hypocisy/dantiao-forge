package com.kyogi.dantiao;

import com.kyogi.dantiao.arenas.ArenaCreatorHandler;
import com.kyogi.dantiao.arenas.ArenaManager;
import com.kyogi.dantiao.caches.CacheHandler;
import com.kyogi.dantiao.configuration.ConfigManager;
import com.kyogi.dantiao.dan.DanHandler;
import com.kyogi.dantiao.request.RequestsHandler;
import com.kyogi.dantiao.util.SingleLineChartData;
import com.mojang.logging.LogUtils;
import net.minecraft.network.chat.Component;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.server.ServerStartedEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

import java.io.IOException;

@Mod(DantiaoMod.MOD_ID)
public class DantiaoMod {
    public static final String MOD_ID = "dantiao";
    public static final Logger LOGGER = LogUtils.getLogger();
    private static DantiaoMod Instance;
    private static ArenaManager arenaManager;
    private static SingleLineChartData singleLineChartData;
    public ConfigManager configManager;
    private static MinecraftServer minecraftServer;

    public DantiaoMod() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        // modEventBus.addListener(this::common);
        MinecraftForge.EVENT_BUS.register(this);
        // 创建一个配置管理器
        // configManager = getConfigManager();
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, ConfigManager.getConfigSpec(), DantiaoMod.MOD_ID + "_settings.toml");
    }

    public static MinecraftServer getMinecraftServer() {
        return minecraftServer;
    }

    public static SingleLineChartData getSingleLineChartData() {
        return singleLineChartData;
    }

    @SubscribeEvent
    public void onPlayerJoin(PlayerEvent.NameFormat event) {
        Player player = event.getEntity();
        String customDisplayName = getCustomDisplayName(player);
        event.setDisplayname(Component.literal(customDisplayName));
    }
    public void onEnable(){
        arenaManager = new ArenaManager();
    }

    @SubscribeEvent
    public void common(ServerStartedEvent event) {
        minecraftServer = event.getServer();
    }

    private String getCustomDisplayName(Player player) {
        return "testPrefix" + player.getName().getString();
    }


    public static DantiaoMod getInstance() {
        return Instance;
    }

    public static ArenaManager getArenaManager() {
        return arenaManager;
    }

    public static ConfigManager getConfigManager() {
        return ConfigManager.getInstance();
    }
    public static CacheHandler getCacheHandler() throws IOException {
        return new CacheHandler();
    }

    public static DanHandler getDanHandler() {
        return new DanHandler();
    }

    public RequestsHandler getRequestsHandler() {
        return new RequestsHandler();
    }

    public ArenaCreatorHandler getArenaCreator() {
        return new ArenaCreatorHandler();
    }

//    public SearchingQueue getSearchingQueue() {
//    }
}
