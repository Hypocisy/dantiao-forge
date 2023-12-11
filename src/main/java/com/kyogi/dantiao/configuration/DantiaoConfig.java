package com.kyogi.dantiao.configuration;

import com.kyogi.dantiao.dan.DanConfigs;
import com.kyogi.dantiao.dan.DefaultDanLoader;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.registries.ForgeRegistries;
import org.apache.commons.lang3.tuple.Pair;

import java.util.List;

public class DantiaoConfig {
    private static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
//    private static final Common se = new Common(BUILDER);

    public Pair<Common, ForgeConfigSpec> getConfigSpec() {
        return BUILDER.configure(DantiaoConfig.Common::new);
    }

    public static class Common {
        public final ForgeConfigSpec.ConfigValue<List<? extends String>> allowArenaInviteTime;
        private final ForgeConfigSpec.IntValue configVersion;
        private final ForgeConfigSpec.ConfigValue<String> serverName;
        private final ForgeConfigSpec.BooleanValue isEnableDebug;
        private final ForgeConfigSpec.IntValue autoSaveCachesTime;
        private final ForgeConfigSpec.ConfigValue<String> messagePrefix;
        private final ForgeConfigSpec.ConfigValue<String> messageLang;
        private final ForgeConfigSpec.BooleanValue isEnableEnergy;
        private final ForgeConfigSpec.DoubleValue energyConsumption;
        private final ForgeConfigSpec.DoubleValue energyMax;
        private final ForgeConfigSpec.DoubleValue energyRecoverPerSecond;
        private final ForgeConfigSpec.BooleanValue isEnableWorldWhitelist;
        private final ForgeConfigSpec.ConfigValue<List<? extends String>> worldWhitelist;
        private final ForgeConfigSpec.ConfigValue<List<? extends String>> gameCreatingToolMaterial;
        private final ForgeConfigSpec.BooleanValue isArenaForceChangeGameMode;
        private final ForgeConfigSpec.ConfigValue<List<? extends String>> allowArenaSearchingTime;
        private final ForgeConfigSpec.BooleanValue isClickPlayerToSendRequest;
        private final ForgeConfigSpec.ConfigValue<List<? extends String>> arenaLimitedItem;

        private final ForgeConfigSpec.ConfigValue<List<? extends String>> arenaLimitedLore;
        // The reward of the game which started with the searching panel
        // 匹配赛的奖励方案
        private final ForgeConfigSpec.BooleanValue arenaAllowBroadcast;
        private final ForgeConfigSpec.IntValue arenaBroadcastWinningStreakTimes;
        private final ForgeConfigSpec.IntValue arenaProtectionExp;
        private final ForgeConfigSpec.IntValue arenaRewardPoints;
        private final ForgeConfigSpec.IntValue arenaWinnerExp;
        private final ForgeConfigSpec.IntValue arenaWinnerMaxExp;
        private final ForgeConfigSpec.IntValue arenaDrawsExp;
        private final ForgeConfigSpec.BooleanValue arenaWinnerPlayFirework;
        // The reward of the game which started with one player sending duel request to the other
        // 邀请赛的奖励方案

        private final ForgeConfigSpec.BooleanValue arenaInvitingAllowBroadcast;
        private final ForgeConfigSpec.IntValue arenaInvitingRewardPoints;
        private final ForgeConfigSpec.IntValue arenaInvitingWinnerExp;
        private final ForgeConfigSpec.IntValue arenaInvitingWinnerMaxExp;
        private final ForgeConfigSpec.IntValue arenaInvitingDrawsExp;
        private final ForgeConfigSpec.BooleanValue arenaInvitingWinnerPlayFirework;

        // The reward of the game which started with the command '/dt game'
        // 强制赛的奖励方案
        private final ForgeConfigSpec.BooleanValue arenaCompulsionAllowBroadcast;
        private final ForgeConfigSpec.IntValue arenaCompulsionRewardPoints;
        private final ForgeConfigSpec.IntValue arenaCompulsionWinnerExp;
        private final ForgeConfigSpec.IntValue arenaCompulsionWinnerMaxExp;
        private final ForgeConfigSpec.IntValue arenaCompulsionDrawsExp;
        private final ForgeConfigSpec.BooleanValue arenaCompulsionWinnerPlayFirework;
        // DUEL LEVEL settings
        // 段位设置
//        private final ForgeConfigSpec.BooleanValue isUseDanPrefixData;
        private final ForgeConfigSpec.ConfigValue<String> danBasicPrefix;
        private final List<DanConfigs> danConfigs;
        private final ForgeConfigSpec.BooleanValue isShowDanPrefix;
        private final ForgeConfigSpec.IntValue danValueOfPrefix;


        Common(ForgeConfigSpec.Builder builder) {
            configVersion = builder.comment("The version of this config.").defineInRange("ConfigVersion", 3, 1, 3);
            serverName = builder.comment("The server name.When a game ends,the name of the server which player locate in will be recorded.This function is applicable for group servers.").define("serverName", "&bServer");
            isEnableDebug = builder.comment("The server name.When a game ends,the name of the server which player locate in will be recorded.This function is applicable for group servers")
                    .define("isEnableDebug", true);
            autoSaveCachesTime = builder.comment("The time interval in seconds for automatically saving caches data").defineInRange("AutoSaveCachesTime", 600, 600, 1200);

            builder.push("Message");
            {
                messagePrefix = builder.comment("The prefix of all message").define("Prefix", "&8&l[&bPVP&8&l] ");
                messageLang = builder.comment("The default language environment").define("messageLang", "&8&l[&bPVP&8&l] ");
            }
            builder.pop();

            builder.comment("Energy Settings").push("Energy");
            {
                isEnableEnergy = builder.comment("Whether enable this function").define("isEnableEnergy", true);
                energyConsumption = builder.comment("The amount of energy consumed in each game").defineInRange("energyConsumptionValue", 90.0, 10.0, 300.0);
                energyMax = builder.comment("The max energy stored").defineInRange("energyMax", 300.0, 0, 1000);
                energyRecoverPerSecond = builder.comment("The amount of energy resumed per second").defineInRange("energyRecoverPerSecond", 0.5, 0.1, 100);
            }
            builder.pop();
            builder.comment("Player will not be allowed to have a game if he(she) is not in the designated world").push("WorldWhitelist");
            {
                isEnableWorldWhitelist = builder.comment("Whether enable this function").define("isEnableWorldWhitelist", false);
                worldWhitelist = builder.comment("White list of the world").defineList("Worlds", List.of("example_pvp_world1", "example_pvp_world1"), obj -> obj instanceof String);
            }
            builder.pop();
            builder.comment("Game settings").push("Game");
            {
                builder.push("ArenaCreating");
                {
                    gameCreatingToolMaterial = builder.comment("The material of arena creator").defineList("gameCreatingToolMaterial", List.of(Items.STICK.toString()), Common::validateItemName);
                }
                builder.pop();
                builder.push("Start");
                {
                    isArenaForceChangeGameMode = builder.comment("Whether change the player's game mode to SURVIVAL compulsorily when the game starts").define("isArenaForceChangeGameMode", true);
                    isClickPlayerToSendRequest = builder.comment("Enable it or not that players are allowed to click others to send a DUEL request when holding a sword").define("isClickPlayerToSendRequest", true);
                    builder.push("Timetable");
                    {
                        allowArenaSearchingTime = builder.comment("Searching stands for the period during which players are allowed to use SEARCHING PANEL").define("allowArenaSearchingTime", List.of("0:00-23:55"), obj -> obj instanceof String);
                        allowArenaInviteTime = builder.comment("Invite stands for the period during which players are allowed to send DUEL request to others").define("allowArenaInviteTime", List.of("0:00-23:55"), obj -> obj instanceof String);
                    }
                    builder.pop();

                    builder.push("ItemLimit");
                    {
                        arenaLimitedItem = builder.comment("Ban the item which you don't want to be brought to arena").defineList("arenaLimitedItem", List.of(Items.SNOWBALL.toString()), Common::validateItemName);
                        arenaLimitedLore = builder.comment("Ban the Lore which you don't want to be brought to arena").defineList("arenaLimitedLore", List.of("&aTest Lore"), obj -> obj instanceof String);
                    }
                    builder.pop(2);
                }

                builder.push("Finish");
                {
                    arenaBroadcastWinningStreakTimes = builder.comment("When a player's winning streak times reaches this value or more than it,a congratulation message will be broadcast").defineInRange("BroadcastWinningStreakTimes", 3, 3, 10000);
                    arenaProtectionExp = builder.comment("""
                            If the difference between the two players' original DUEL EXP value more than this value,
                            the protection mechanism will be enabled,which means both players will not gain or be deducted EXP""").defineInRange("arenaProtectionExp", 2000, 1, Integer.MAX_VALUE);
                }
                builder.pop();
                builder.push("Rewards");
                {
                    arenaRewardPoints = builder.comment("The reward of the game which started with the searching panel").defineInRange("arenaRewardPoints", 3, 3, Integer.MAX_VALUE);
                    arenaWinnerExp = builder.comment("""
                            The basic value of DUEL EXP awarded to the winner.
                            The total value of DUEL EXP = basic value + extra value according to game performance""").defineInRange("arenaWinnerExp", 60, 1, Integer.MAX_VALUE);
                    arenaWinnerMaxExp = builder.comment("The max total value of DUEL EXP awarded to the winner").defineInRange("arenaWinnerMaxExp", 2000, 1, Integer.MAX_VALUE);
                    arenaDrawsExp = builder.comment("The value of DUEL EXP awarded to both the players when the game reaches a draw").defineInRange("arenaDrawsExp", 25, 1, Integer.MAX_VALUE);
                    arenaWinnerPlayFirework = builder.comment("Whether set off a firework for the winner or not").define("arenaWinnerPlayFirework", true);
                    arenaAllowBroadcast = builder.comment("Whether broadcast the result of the game").define("arenaBroadcastWinningStreakTimes", true);
                }
                builder.pop();
                builder.push("Inviting");
                {
                    arenaInvitingRewardPoints = builder.comment("The reward of the game which started with the Inviting game").defineInRange("arenaInvitingRewardPoints", 3, 3, 10000);
                    arenaInvitingWinnerExp = builder.comment("""
                            The basic value of DUEL EXP awarded to the winner.
                            The total value of DUEL EXP = basic value + extra value according to game performance""").defineInRange("arenaInvitingWinnerExp", 60, 1, Integer.MAX_VALUE);
                    arenaInvitingWinnerMaxExp = builder.comment("The max total value of DUEL EXP awarded to the winner").defineInRange("arenaInvitingWinnerMaxExp", 2000, 1, Integer.MAX_VALUE);
                    arenaInvitingDrawsExp = builder.comment("The value of DUEL EXP awarded to both the players when the game reaches a draw").defineInRange("arenaDrawsExp", 25, 1, Integer.MAX_VALUE);
                    arenaInvitingWinnerPlayFirework = builder.comment("Whether set off a firework for the winner or not").define("arenaWinnerInvitingPlayFirework", false);
                    arenaInvitingAllowBroadcast = builder.comment("Whether broadcast the result of the game").define("arenaBroadcastWinningStreakTimes", true);
                }
                builder.pop();
                builder.push("Compulsion");
                {
                    arenaCompulsionRewardPoints = builder.comment("The reward of the game which started with the Compulsion game").defineInRange("arenaCompulsionRewardPoints", 3, 3, Integer.MAX_VALUE);
                    arenaCompulsionWinnerExp = builder.comment("""
                            The basic value of DUEL EXP awarded to the winner.
                            The total value of DUEL EXP = basic value + extra value according to game performance""").defineInRange("arenaCompulsionWinnerExp", 60, 1, Integer.MAX_VALUE);
                    arenaCompulsionWinnerMaxExp = builder.comment("The max total value of DUEL EXP awarded to the winner").defineInRange("arenaCompulsionWinnerMaxExp", 2000, 1, Integer.MAX_VALUE);
                    arenaCompulsionDrawsExp = builder.comment("The value of DUEL EXP awarded to both the players when the game reaches a draw").defineInRange("arenaDrawsExp", 25, 1, Integer.MAX_VALUE);
                    arenaCompulsionWinnerPlayFirework = builder.comment("Whether set off a firework for the winner or not").define("arenaWinnerCompulsionPlayFirework", true);
                    arenaCompulsionAllowBroadcast = builder.comment("Whether broadcast the result of the game").define("arenaBroadcastWinningStreakTimes", true);
                }
                builder.pop(2);
            }

            builder.comment("Dan LEVEL settings").push("Dan");
            {
                builder.push("Settings");
                isShowDanPrefix = builder.comment("""
                        Whether show the DUEL LEVEL prefix when chatting
                        If you have installed some chatting expansion plugin,this function will be out of action
                        To be sure,you can still show the level by PAPI variable of this plugin.You can look up them in document""").define("isShowDanPrefix", true);
                danBasicPrefix = builder.comment("""
                        The context of DUEL LEVEL prefix when a player's DUEL LEVEL is zero
                        For example:
                        InitialDanName: '&7[DUEL LEVEL 0]'""").define("danBasicPrefix", "&7[无段位]");

                builder.pop();
                builder.push("CustomDan");
                {
                    danValueOfPrefix = builder.defineInRange("danValueOfPrefix", 13, 13, 50);
                    danConfigs = new DefaultDanLoader(builder).get();
                }
                builder.pop();
            }
            builder.pop();
        }

        public static boolean validateItemName(final Object obj) {
            return obj instanceof final String itemName && ForgeRegistries.ITEMS.containsKey(new ResourceLocation(itemName));
        }
    }
}
