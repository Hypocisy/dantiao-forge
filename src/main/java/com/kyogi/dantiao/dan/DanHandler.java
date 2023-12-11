package com.kyogi.dantiao.dan;

import com.kyogi.dantiao.DantiaoMod;
import com.kyogi.dantiao.caches.DanCache;
import com.kyogi.dantiao.configuration.ConfigManager;
import com.kyogi.dantiao.configuration.DataFile;
import com.kyogi.dantiao.configuration.DataManager;
import net.minecraft.server.level.ServerPlayer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DanHandler {
    List<CustomDan> customDans = new ArrayList<>();
    Map<ServerPlayer, CustomDan> playerDans = new HashMap<>();
    int threshold;

    public DanHandler() {
        loadCustomDanFromConfig();
    }

    public CustomDan getDanByNum(int num) {
        try {
            return customDans.get(num);
        } catch (Exception e) {
            return null;
        }
    }

    public CustomDan getDanByName(String name) {
        for (CustomDan dan : customDans) {
            if (dan.getEditName().equals(name)) {
                return dan;
            }
        }
        return null;
    }

    public CustomDan getPlayerDan(ServerPlayer player) throws IOException {
        if (!playerDans.containsKey(player)) {
            refreshPlayerDan(player);
        }
        return playerDans.get(player);
    }

    public void refreshPlayerDan(ServerPlayer player) throws IOException {
        DanCache cache = DantiaoMod.getCacheHandler().getDan();
        CustomDan playerDan = null;
        int exp = cache.get(player);
        if (exp > threshold) {
            for (CustomDan customDan : customDans) {
                if (exp < customDan.getExp()) {
                    break;
                }
                playerDan = customDan;
            }
        }
        playerDans.put(player, playerDan);
    }

    public int getNeedExpToLevelUp(ServerPlayer player) throws IOException {
        int expNow = DantiaoMod.getCacheHandler().getDan()
                .get(player);
        int expNeed;
        CustomDan playerDan = getPlayerDan(player);
        if (playerDan == null) {
            expNeed = threshold - expNow;
        } else {
            int index = customDans.indexOf(playerDan);
            if (index == customDans.size() - 1) {
                expNeed = 0;
            } else {
                expNeed = customDans.get(index + 1).getExp() - expNow;
            }
        }
        return expNeed;
    }

    public List<CustomDan> getCustomDans() {
        return customDans;
    }

    public int getThreshold() {
        return threshold;
    }

    public void loadCustomDanFromConfig() throws IOException {
        boolean isUseDefault = false;
        int useDefaultReason = 0;

        List<String> editNameList = new ArrayList<>();
        List<?> customDanList= ConfigManager.getCustomDan();
        customDanList.stream().map()
        if (editNameList.isEmpty()) {
            useDefaultReason = 1;
            isUseDefault = true;
        }

        List<CustomDan> customDans = new ArrayList<CustomDan>();

        List<String> customDanNameList = new ArrayList<String>();
        int expMark = 0;
        for (int n = 0; n < editNameList.size(); n++) {
            String editName = editNameList.get(n);

            String danName = config.getString("Dan.CustomDan." + editName
                    + ".Name");
            if (customDanNameList.contains(danName)) {
                isUseDefault = true;
                useDefaultReason = 2;
                break;
            } else {
                customDanNameList.add(danName);
            }

            int exp = config.getInt("Dan.CustomDan." + editName + ".Exp");
            if (exp <= expMark) {
                isUseDefault = true;
                useDefaultReason = 3;
                break;
            }

            customDans.add(new CustomDan(editName, danName, exp));
        }
        if (isUseDefault) {
            this.customDans = DefaultDanLoader.get();
            this.threshold = 50;
            Bukkit.getConsoleSender().sendMessage("§8[§bDantiao§8]");
            Bukkit.getConsoleSender().sendMessage("§c自定义段位加载失败！");
            Bukkit.getConsoleSender().sendMessage(
                    "§cFailed to load the custom dans!");
            if (useDefaultReason == 1) {
                Bukkit.getConsoleSender()
                        .sendMessage(
                                "§6[原因(Reason)] §e未发现任何自定义段位名，你可以尝试输入/dt reload c解决这个问题！"
                                        + "(Cannot find any custom dan,you can try to use /dt reload c for fixing this problem!)");
            }
            if (useDefaultReason == 2) {
                Bukkit.getConsoleSender().sendMessage(
                        "§6[原因(Reason)] §e自定义段位的段位名出现了重复！"
                                + "(Duplicate dan names of the custom dans!)");
            }
            if (useDefaultReason == 3) {
                Bukkit.getConsoleSender()
                        .sendMessage(
                                "§6[原因(Reason)] §e自定义段位请根据其所需经验值的多少来排序而设置，从少到多"
                                        + "(If you want to have custom dans,you must order them according to their exp of need when you edit dans)");
                Bukkit.getConsoleSender().sendMessage("§6[示例(Example)]");
                Bukkit.getConsoleSender().sendMessage("§fDan:");
                Bukkit.getConsoleSender().sendMessage("§f  myCustomDan1:");
                Bukkit.getConsoleSender().sendMessage(
                        "§f    name: '&aPVP LEVEL I");
                Bukkit.getConsoleSender().sendMessage("§f    exp: 50");
                Bukkit.getConsoleSender().sendMessage("§f  myCustomDan2:");
                Bukkit.getConsoleSender().sendMessage(
                        "§f    name: '&6PVP LEVEL II");
                Bukkit.getConsoleSender().sendMessage("§f    exp: 200");
            }
        } else {
            this.customDans = customDans;
            this.threshold = customDans.get(0).getExp();
        }
        if (!this.customDans.isEmpty()) {
            for (ServerPlayer player : DantiaoMod.getCacheHandler().getDan().keySet()) {
                refreshPlayerDan(player);
            }
        }
    }
}
