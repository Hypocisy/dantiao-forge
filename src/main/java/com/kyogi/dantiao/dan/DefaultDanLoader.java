package com.kyogi.dantiao.dan;

import java.util.ArrayList;
import java.util.List;

public class DefaultDanLoader {
    private final List<CustomDan> customDans = new ArrayList<>();

    public DefaultDanLoader() {
        customDans.add(new CustomDan(0, "dan0", "&f[&2青铜I&f]&r", 50));
        customDans.add(new CustomDan(1, "dan1", "&f[&2青铜II&f]&r", 150));
        customDans.add(new CustomDan(2, "dan2", "&f[&2青铜III&f]&r", 280));
        customDans.add(new CustomDan(3, "dan3", "&f[&3黑铁I&f]&r", 500));
        customDans.add(new CustomDan(4, "dan4", "&f[&3黑铁II&f]&r", 760));
        customDans.add(new CustomDan(5, "dan5", "&f[&3黑铁III&f]&r", 1200));
        customDans.add(new CustomDan(6, "dan6", "&f[&e黄金I&f]&r", 1550));
        customDans.add(new CustomDan(7, "dan7", "&f[&e黄金II&f]&r", 2100));
        customDans.add(new CustomDan(8, "dan8", "&f[&e黄金III&f]&r", 3500));
        customDans.add(new CustomDan(9, "dan9", "&f[&b钻石I&f]&r", 5000));
        customDans.add(new CustomDan(10, "dan10", "&f[&b钻石II&f]&r", 6666));
        customDans.add(new CustomDan(11, "dan11", "&f[&b钻石III&f]&r", 8888));
        customDans.add(new CustomDan(12, "dan12", "&f[&6&l战斗天尊&f]&r", 10000));
        customDans.add(new CustomDan(13, "dan13", "&f[&d&l荣耀王者&f]&r", 15000));
    }

    public List<CustomDan> get() {
        return customDans;
    }
}
