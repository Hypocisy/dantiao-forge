package com.kyogi.dantiao.dan;

import net.minecraftforge.common.ForgeConfigSpec;

import java.util.ArrayList;
import java.util.List;

public class DefaultDanLoader {
    private static final List<DanConfigs> danConfigs = new ArrayList<>();

    public DefaultDanLoader(ForgeConfigSpec.Builder builder) {
        danConfigs.add(new DanConfigs("dan0", "&f[&2青铜I&f]&r", 50, builder));
        danConfigs.add(new DanConfigs("dan1", "&f[&2青铜II&f]&r", 150, builder));
        danConfigs.add(new DanConfigs("dan2", "&f[&2青铜III&f]&r", 280, builder));
        danConfigs.add(new DanConfigs("dan3", "&f[&3黑铁I&f]&r", 500, builder));
        danConfigs.add(new DanConfigs("dan4", "&f[&3黑铁II&f]&r", 760, builder));
        danConfigs.add(new DanConfigs("dan5", "&f[&3黑铁III&f]&r", 1200, builder));
        danConfigs.add(new DanConfigs("dan6", "&f[&e黄金I&f]&r", 1550, builder));
        danConfigs.add(new DanConfigs("dan7", "&f[&e黄金II&f]&r", 2100, builder));
        danConfigs.add(new DanConfigs( "dan8", "&f[&e黄金III&f]&r", 3500, builder));
        danConfigs.add(new DanConfigs( "dan9", "&f[&b钻石I&f]&r", 5000, builder));
        danConfigs.add(new DanConfigs( "dan10", "&f[&b钻石II&f]&r", 6666, builder));
        danConfigs.add(new DanConfigs( "dan11", "&f[&b钻石III&f]&r", 8888, builder));
        danConfigs.add(new DanConfigs( "dan12", "&f[&6&l战斗天尊&f]&r", 10000, builder));
        danConfigs.add(new DanConfigs( "dan13", "&f[&d&l荣耀王者&f]&r", 15000, builder));
    }

    public static List<DanConfigs> get() {
        return danConfigs;
    }
}
