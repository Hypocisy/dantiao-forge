package com.kyogi.dantiao.data;

import com.kyogi.dantiao.DantiaoMod;
import net.minecraft.world.phys.Vec3;

import java.util.ArrayList;
import java.util.List;

public class Data {
    public static boolean useDatabase;

    public static boolean areaB = false, blacklistB = false, danB = false,
    energyB = false, languageFillB = false, pointB = false;

    public static initalB(ConfigManager configManager){
        if (configManager.isUseMySQL()){
            areaB = configManager.isAreaUseMySQL();
            blacklistB = configManager.isBlacklistUseMySQL();
            danB = configManager.isDanUseMySQL();
            energyB = configManager.isEnergyUseMySQL();
            languageFileB = configManager.isLanguageFileUseMySQL();
            pointB = configManager.isPointUseMySQL();
            pointShopB = configManager.isPointShopUseMySQL();
            recordB = configManager.isRecordUseMySQL();
        }
    }

    // 获取所有竞技场的编辑名
    public static List<String> getArenas(){
        useDatabase = areaB;
        if (useDatabase) {
            return ().getMySQL().getArenas();
        } else {
            List<String> arenaList = new ArrayList<String>();
            if (!areasFile.exists()) {
                return arenaList;
            }
            ConfigurationSection section = areas
                    .getConfigurationSection("Arenas");
            if (section == null) {
                return arenaList;
            }
            section.getKeys(false).forEach(key -> {
                arenaList.add(key);
            });
            return arenaList;
        }
    }

    public static String getArenaDisplayName(String editName) { // 获取某个竞技场的展示名
        useDatabase = areaB;
        if (useDatabase) {
            return DantiaoMod.getInstance().getMySQL().getArenaDisplayName(editName);
        } else {
            return areas.getString("Arenas." + editName + ".Name").replace("&",
                    "§");
        }
    }
    public static Vec3 getArenaPointA(String editName) { // 获取某个竞技场的A点
        useDatabase = areaB;
        if (useDatabase) {
            return DantiaoMod.getInstance().getMySQL().getArenaPointA(editName);
        } else {
            World world = Bukkit.getWorld(areas.getString("Arenas." + editName
                    + ".A.World"));
            double x = areas.getDouble("Arenas." + editName + ".A.X"), y = areas
                    .getDouble("Arenas." + editName + ".A.Y"), z = areas
                    .getDouble("Arenas." + editName + ".A.Z");
            float yaw = (float) areas
                    .getDouble("Arenas." + editName + ".A.YAW"), pitch = (float) areas
                    .getDouble("Arenas." + editName + ".A.PITCH");
            return new Location(world, x, y, z, yaw, pitch);
        }
    }

}
