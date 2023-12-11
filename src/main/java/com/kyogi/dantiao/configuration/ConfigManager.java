package com.kyogi.dantiao.configuration;

import net.minecraft.nbt.Tag;
import net.minecraftforge.common.ForgeConfigSpec;
import org.apache.commons.lang3.tuple.Pair;

import java.util.List;
import java.util.Objects;

public class ConfigManager {
    private static ConfigManager Instance;
    private static Pair<DantiaoConfig.Common, ForgeConfigSpec> dantiaoConfigPair;
    private static ForgeConfigSpec config;
    private static DantiaoConfig.Common loadedModConfig;

    private static void reload() {
        dantiaoConfigPair = new DantiaoConfig().getConfigSpec();
        loadedModConfig = getConfig();
        config = getConfigSpec();
    }

    private static DantiaoConfig.Common getConfig() {
        return dantiaoConfigPair.getLeft();
    }

    public static ForgeConfigSpec getConfigSpec() {
        return dantiaoConfigPair.getRight();
    }

    public static ConfigManager getInstance() {
        return Instance;
    }

    public static void setInstance(ConfigManager instance) {
        Instance = instance;
    }

    public static DantiaoConfig.Common getLoadedModConfig() {
        return loadedModConfig;
    }

    public static List<Objects> getCustomDan() {
        return config.get("Dan.CustomDan");
    }

    public String getMysqlUser() {
        return config.get("Mysql.User");
    }

    public String getMysqlPassword() {
        return config.get("Mysql.Password");
    }

    public String getMysqlUrl() {
        return config.get("Mysql.getMysqlUrl");
    }

    public Boolean isEnableEnergy() {
        return config.get("Energy.isEnableEnergy");
    }

    public Integer getEnergyConsumptionValue() {
        return config.get("Energy.energyConsumptionValue");
    }

    public Double getEnergyRecoverPerSecond() {
        return config.get("Energy.energyRecoverPerSecond");
    }

    public Double getMaxEnergy() {
        return config.get("Energy.energyMax");
    }

    public Double getEnergyNeeds() {
        return config.get("Energy.energyNeeds");
    }

    public Boolean isEnableWorldWhitelist() {
        return config.get("WorldWhitelist.isEnableWorldWhitelist");
    }

    public List<String> getWorldWhitelist() {
        return config.get("WorldWhitelist.Worlds");
    }

    public List<String> getGameCreatingToolMaterial() {
        return config.get("Game.ArenaCreating.gameCreatingToolMaterial");
    }

    public Boolean isArenaForceChangeGameMode() {
        return config.get("Game.Start.isArenaForceChangeGameMode");
    }

    public List<String> getAllowArenaSearchingTime() {
        return config.get("Game.Start.Timetable.allowArenaSearchingTime");
    }

    public List<String> getAllowArenaInviteTime() {
        return config.get("Game.Start.Timetable.allowArenaInviteTime");
    }

    public List<String> getArenaLimitedItem() {
        return config.get("Game.Start.ItemLimit.arenaLimitedItem");
    }

    public List<Tag> getArenaLimitedLore() {
        return config.get("Game.Start.ItemLimit.arenaLimitedLore");
    }

    public Integer getBroadcastWinningStreakTimes() {
        return config.get("Game.Finish.BroadcastWinningStreakTimes");
    }

    public Integer getArenaProtectionExp() {
        return config.get("Game.Finish.arenaProtectionExp");
    }

    public Integer getArenaRewardPoints() {
        return config.get("Game.Rewards.arenaRewardPoints");
    }

    public Integer getArenaInvitingRewardPoints() {
        return config.get("Game.Rewards.arenaInvitingRewardPoints");
    }

    public Integer getArenaCompulsionRewardPoints() {
        return config.get("Game.Rewards.arenaCompulsionRewardPoints");
    }

    public Integer getArenaWinnerExp() {
        return config.get("Game.Rewards.arenaWinnerExp");
    }

    public Integer getArenaWinnerMaxExp() {
        return config.get("Game.Rewards.arenaWinnerMaxExp");
    }

    public Integer getArenaDrawsExp() {
        return config.get("Game.Rewards.arenaDrawsExp");
    }

    public Boolean isArenaWinnerPlayFirework() {
        return config.get("Game.Rewards.arenaWinnerPlayFirework");
    }

    public Boolean isArenaBroadcastWinningStreakTimes() {
        return config.get("Game.Rewards.arenaBroadcastWinningStreakTimes");
    }

    public Integer getArenaInvitingWinnerExp() {
        return config.get("Game.Inviting.arenaWinnerExp");
    }

    public Integer getArenaInvitingWinnerMaxExp() {
        return config.get("Game.Inviting.arenaWinnerMaxExp");
    }

    public Integer getArenaInvitingDrawsExp() {
        return config.get("Game.Inviting.arenaDrawsExp");
    }

    public Boolean isArenaInvitingWinnerPlayFirework() {
        return config.get("Game.Inviting.arenaWinnerPlayFirework");
    }

    public Boolean isArenaInvitingBroadcastWinningStreakTimes() {
        return config.get("Game.Inviting.arenaBroadcastWinningStreakTimes");
    }

    public Integer getArenaCompulsionWinnerExp() {
        return config.get("Game.Compulsion.arenaWinnerExp");
    }

    public Integer getArenaCompulsionWinnerMaxExp() {
        return config.get("Game.Compulsion.arenaWinnerMaxExp");
    }

    public Integer getArenaCompulsionDrawsExp() {
        return config.get("Game.Compulsion.arenaDrawsExp");
    }

    public Boolean isArenaCompulsionWinnerPlayFirework() {
        return config.get("Game.Compulsion.arenaWinnerPlayFirework");
    }

    public Boolean isArenaCompulsionBroadcastWinningStreakTimes() {
        return config.get("Game.Compulsion.arenaBroadcastWinningStreakTimes");
    }

    public Boolean isShowDanPrefix() {
        return config.get("Dan.Settings.isShowDanPrefix");
    }

    public String getDanBasicPrefix() {
        return config.get("Dan.Settings.danBasicPrefix");
    }

    public boolean isEnableDebug() {
        return config.get("isEnableDebug");
    }

    public Integer getAutoSaveCachesTime() {
        return config.getInt("AutoSaveCachesTime");
    }

    public boolean isClickPlayerToSendRequest() {
        return config.get("isClickPlayerToSendRequest");
    }

    public String getServerName() {
        return config.get("serverName");
    }
}
