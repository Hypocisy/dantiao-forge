package com.kyogi.dantiao.util;

import com.kyogi.dantiao.DantiaoMod;
import net.minecraft.network.chat.Component;

public class Debug {
    public static void send(Component Chinese, Component English){
        if (DantiaoMod.getConfigManager().isEnableDebug()){
            DantiaoMod.LOGGER.debug("§e[Dantiao Debug]");
            DantiaoMod.LOGGER.debug("§7[Dantiao Debug]" + Chinese);
            DantiaoMod.LOGGER.debug("§7[Dantiao Debug]" + English);
        }
    }

}
