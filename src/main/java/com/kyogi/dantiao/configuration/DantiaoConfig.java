package com.kyogi.dantiao.configuration;

import com.kyogi.dantiao.DantiaoMod;

import net.minecraftforge.common.ForgeConfig;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.common.Mod;
import oshi.util.tuples.Pair;

import java.util.ArrayList;
import java.util.List;

@Mod.EventBusSubscriber(modid = DantiaoMod.MOD_ID,bus = Mod.EventBusSubscriber.Bus.MOD)
public class DantiaoConfig {

    public static class Common{
        public static final ForgeConfigSpec.IntValue configVersion;
        public static final ForgeConfigSpec.BooleanValue CheckVersion;
        public static final ForgeConfigSpec.ConfigValue<String> serverName;

        public static final ForgeConfigSpec.BooleanValue isEnableDebug;

        public static final ForgeConfigSpec.IntValue autoSaveCachesTime;
        public static final ForgeConfigSpec.BooleanValue isEnableMySQL;
        public static final ForgeConfigSpec.ConfigValue<String> mySQLURL;
        public static final ForgeConfigSpec.ConfigValue<String> mysqlUserName;

        public static final ForgeConfigSpec.ConfigValue<String> mySQLPassword;
        public static final ForgeConfigSpec.ConfigValue<String> messagePerfix;
        public static final ForgeConfigSpec.ConfigValue<String> messageLang;
        public static final ForgeConfigSpec.BooleanValue isUseArenaData;
        public static final ForgeConfigSpec.BooleanValue isUseBlacklistData;
        public static final ForgeConfigSpec.BooleanValue isUseDanData;
        public static final ForgeConfigSpec.BooleanValue isUseEnergyData;
        public static final ForgeConfigSpec.BooleanValue isUseLanguageFileData;
        public static final ForgeConfigSpec.BooleanValue isUsePointData;
        public static final ForgeConfigSpec.BooleanValue isUsePointShopData;
        public static final ForgeConfigSpec.BooleanValue isUseRecordData;
        public static final ForgeConfigSpec.BooleanValue isEnableEnergy;
        public static final ForgeConfigSpec.IntValue energyConsumptionValue;
        public static final ForgeConfigSpec.IntValue energyMaxValue;
        public static final ForgeConfigSpec.DoubleValue energyRecoverPerSecond;

        public static final ForgeConfigSpec.BooleanValue isEnableWorldWhitelist;
        public static final ForgeConfigSpec.ConfigValue<List<? extends String>> worldWhitelist;
        public static final ForgeConfigSpec.IntValue hologramRefreshInterval;
        public static final ForgeConfigSpec.ConfigValue<String> gameCreatingToolMaterial;
        public static final ForgeConfigSpec.BooleanValue arenaForceChangeGameMode;

        public static final ForgeConfigSpec.ConfigValue<List<? extends String>> allowArenaSerchingTime;
        public static final ForgeConfigSpec.ConfigValue<List<? extends String>> allowArenaInviteTime;

        public static final ForgeConfigSpec.BooleanValue isClickPlayerToSendRequest;
        public static final ForgeConfigSpec.ConfigValue<List<? extends String>> arenaLimitedItem;

        public static final ForgeConfigSpec.ConfigValue<List<? extends String>> arenaLimitedLore;
        // The reward of the game which started with the searching panel
        // 匹配赛的奖励方案

        public static final ForgeConfigSpec.IntValue arenaBroadcastWinningStreakTimes;
        public static final ForgeConfigSpec.LongValue arenaProtectionExp;
        public static final ForgeConfigSpec.IntValue arenaRewardPoints;
        public static final ForgeConfigSpec.IntValue arenaWinnerExp;
        public static final ForgeConfigSpec.IntValue arenaWinnerMaxExp;
        public static final ForgeConfigSpec.IntValue arenaDrawsExp;
        public static final ForgeConfigSpec.IntValue arenaWinnerPlayFirework;
        // The reward of the game which started with one player sending duel request to the other
        // 邀请赛的奖励方案

        public static final ForgeConfigSpec.BooleanValue arenaInvitingAllowBroadcast;
        public static final ForgeConfigSpec.LongValue arenaInvitingProtectionExp;
        public static final ForgeConfigSpec.IntValue arenaInvitingRewardPoints;
        public static final ForgeConfigSpec.IntValue arenaInvitingWinnerExp;
        public static final ForgeConfigSpec.IntValue arenaInvitingWinnerMaxExp;
        public static final ForgeConfigSpec.IntValue arenaInvitingDrawsExp;
        public static final ForgeConfigSpec.IntValue arenaInvitingWinnerPlayFirework;
        public static final ForgeConfigSpec.BooleanValue arenaInvitingAllowBroadcast;
        // The reward of the game which started with the command '/dt game'
        // 强制赛的奖励方案
        public static final ForgeConfigSpec.BooleanValue arenaCompulsionAllowBroadcast;

        public static final ForgeConfigSpec.LongValue arenaCompulsionProtectionExp;
        public static final ForgeConfigSpec.IntValue arenaCompulsionRewardPoints;
        public static final ForgeConfigSpec.IntValue arenaCompulsionWinnerExp;
        public static final ForgeConfigSpec.IntValue arenaCompulsionWinnerMaxExp;
        public static final ForgeConfigSpec.IntValue arenaCompulsionDrawsExp;
        public static final ForgeConfigSpec.IntValue arenaCompulsionWinnerPlayFirework;
        public static final ForgeConfigSpec.BooleanValue arenaCompulsionAllowBroadcast;
        // DUEL LEVEL settings
        // 段位设置
        public static final ForgeConfigSpec.BooleanValue isShowDanPerfix;

        public static final ForgeConfigSpec.ConfigValue<String> danInitalPerfix;
        public static final ForgeConfigSpec.ConfigValue<Pair<String,Integer>> danPerfixsAndExp;



        Common(ForgeConfigSpec.Builder builder){
            configVersion = (ForgeConfigSpec.IntValue) builder.comment("The version of this config.").define("ConfigVersion", 3);
            serverName = builder.comment("The version of this config.").define("ConfigVersion", "&bServer");
            isEnableDebug = builder.comment("The server name.When a game ends,the name of the server which player locate in will be recorded.This function is applicable for group servers")
                    .define("isEnableDebug", true);
            autoSaveCachesTime = builder.comment("The time interval in seconds for automatically saving caches data").defineInRange("AutoSaveCachesTime", 600,600, 1200);

            builder.push("Message");
            {
                messagePerfix = builder.comment("The prefix of all message").define("Prefix", "&8&l[&bPVP&8&l] ");
                messageLang = builder.comment("The default language environment").define("messageLang", "&8&l[&bPVP&8&l] ");
            }
            builder.pop();

            builder.push("MySQL");
            {
                isEnableMySQL = builder.comment("Whether you use MySQL the store data").define("isEnableMySQL", false);
                mySQLURL = builder.comment("Define Mysql connect url").define("mySQLURL", "jdbc:mysql://localhost:3306/test");
                mysqlUserName = builder.comment("Define Mysql connect User name").define("User", "root");
                mySQLPassword = builder.comment("Define Mysql connect User name").define("Password", "1234");

                builder.comment("Area: 是否使用数据库储存竞技场位置、大厅位置、全息图位置的数据\n" +
                        "Blacklist: 是否使用数据库储存黑名单的数据\n" +
                        "Dan: 是否使用数据库储存玩家的段位经验数据\n" +
                        "Energy: 是否使用数据库储存玩家的精力值数据\n" +
                        "LanguageFile: 是否使用数据库储存玩家的所选用的语言文件的数据\n" +
                        "Point: 是否使用数据库储存玩家的单挑积分数据\n" +
                        "PointShop: 是否使用数据库储存积分商城的商品数据\n" +
                        "Record: 是否使用数据库储存玩家的战斗记录以及全服的胜场数排行榜、KD值排行榜数据")
                        .push("Function");
                {
                    isUseArenaData = builder.define("isUseArenaData", false);
                    isUseBlacklistData = builder.define("isUseBlacklistData", true);
                    isShowDanPerfix = builder.define("isShowDanPerfix", false);
                    isUseEnergyData = builder.define("isShowDanPerfix", true);
                    isUseLanguageFileData = builder.define("isUseLanguageFileData", true);
                    isUsePointData = builder.define("isUsePointData", true);
                    isUsePointShopData = builder.define("isUsePointShopData", false);
                    isUseRecordData = builder.define("isUseRecordData", true);
                }
            }
            builder.pop(2);
            builder.comment("Energy Settings")
                    .push("Energy");
            {
                isEnableEnergy = builder.comment("Whether enable this function").define("isEnableEnergy", true);
                energyConsumptionValue = builder.comment("The amount of energy consumed in each game").defineInRange("energyConsumptionValue", 90,10, 300);
                energyMaxValue = builder.comment("The max energy stored").defineInRange("energyConsumptionValue", 300,10, 1000);
                energyRecoverPerSecond = builder.comment("The amount of energy resumed per second").defineInRange("energyRecoverPerSecond", 0.5,0.1,100);
            }
            builder.pop();
            builder.comment("Player will not be allowed to have a game if he(she) is not in the designated world")
                    .push("WorldWhitelist");
            {
                isEnableWorldWhitelist = builder.comment("Whether enable this function").define("isEnableWorldWhitelist", false);
                worldWhitelist = builder.comment("White list of the world").defineList("Worlds", List.of("example_pvp_world1", "example_pvp_world1"),obj -> obj instanceof String);

            }
            builder.pop();
        }
    }
}
