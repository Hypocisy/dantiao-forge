package com.kyogi.dantiao.util;

import com.kyogi.dantiao.DantiaoMod;
import net.minecraft.network.chat.Component;

public class Debug {
    public static void send(Component Chinese, Component English){
        if (DantiaoMod.getInstance().getConfigMananger.isDebug()){
            DantiaoMod.LOGGER.debug("§e[Dantiao Debug]");
            DantiaoMod.LOGGER.debug("§7[Dantiao Debug]" + Chinese.getString());
            DantiaoMod.LOGGER.debug("§7[Dantiao Debug]" + English.getString());
        }
    }

}
