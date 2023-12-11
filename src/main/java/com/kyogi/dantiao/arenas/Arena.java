package com.kyogi.dantiao.arenas;

import com.kyogi.dantiao.DantiaoMod;
import com.kyogi.dantiao.dan.CustomDan;
import com.kyogi.dantiao.encapsulation.ArenaInfo;
import com.kyogi.dantiao.util.Positions;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.fml.common.Mod;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Mod.EventBusSubscriber
public class Arena {

    public int arenaCurrentlyStage = -1;
    // 竞技场名称
    private final String arenaName;
    // 玩家1
    private ServerPlayer player1;
    // 玩家2
    private ServerPlayer player2;
    // 观战玩家
    private List<ServerPlayer> watchers;
    // 竞技场是否启动
    private boolean isArenaEnable = false;
    //    private task timer;
    private int areaId;
    private int time;
    private int countDown;
    private int timeout;
    private boolean enable = false;

    private double player1Damage;
    private double player1MaxDamage;
    private double player2Damage;
    private double player2MaxDamage;

    private int player1Exp;
    private int player2Exp;

    private int player1MultiHit;
    private int player2MultiHit;

    private Positions player1Position;
    private Positions player2Position;

    private CustomDan player1Dan;
    private CustomDan player2Dan;

    private List<ItemStack> player1InventoryItems;
    private List<ItemStack> player2InventoryItems;
    private ItemStack player1Helmet;
    private ItemStack player2Helmet;
    private ItemStack player1ChestPlate;
    private ItemStack player2ChestPlate;
    private ItemStack player1Leggings;
    private ItemStack player2Leggings;
    private ItemStack player1Boots;
    private ItemStack player2Boots;
    private ItemStack player1OffHandItem;
    private ItemStack player2OffHandItem;

    private boolean isPlayersCanTeleport;

    private boolean isWatchersCanTeleport;

    private int startWay;


    public Arena(String arenaName) {
        this.arenaName = arenaName;
    }

    public void start(ServerPlayer player1, ServerPlayer player2, int startWay) throws IOException {
        this.player1 = player1;
        this.player2 = player2;
        this.startWay = startWay;
        watchers = new ArrayList<>();
        isPlayersCanTeleport = false;
        isWatchersCanTeleport = false;
        isArenaEnable = true;
        time = getCountDown();

        this.player1.sendSystemMessage(Component.literal("&7比赛即将开始.."));
        this.player2.sendSystemMessage(Component.literal("&7比赛即将开始.."));

        ArenaInfo arenaInfo = DantiaoMod.getCacheHandler().getArenaInfo();
    }

    public double getPlayer1Damage() {
        return player1Damage;
    }

    public double getPlayer1MaxDamage() {
        return player1MaxDamage;
    }

    public int getArenaCurrentlyStage() {
        return arenaCurrentlyStage;
    }

    public int getCountDown() {
        if (countDown == 0) {
            return -6;
        } else {
            return -countDown;
        }
    }

    public void setCountDown(int countDown) {
        this.countDown = countDown;
    }

    public boolean isArenaEnable() {
        return isArenaEnable;
    }

    public boolean isPlayer1(ServerPlayer player) {
        return player1.is(player);
    }

    public boolean isPlayer2(ServerPlayer player) {
        return player2.equals(player);
    }

    public void addWatcher(ServerPlayer watcher) {
        if (watchers.contains(watcher)) {
            return;
        }
        watchers.add(watcher);
    }

    public double getDamage(ServerPlayer player) {
        //如果是玩家1，则返回玩家1的伤害，反之返回玩家2的伤害
        return isPlayer1(player) ? player1Damage : player2Damage;
    }

    public double getMaxDamage(ServerPlayer player) {
        // 如果是玩家1，则返回玩家1的最大伤害，反之返回玩家2的最大伤害
        return isPlayer1(player) ? player1MaxDamage : player2MaxDamage;
    }

    public void setMaxDamage(ServerPlayer player, double maxDamage) {
        if (isPlayer1(player)) {
            player1MaxDamage = maxDamage;
        } else {
            player2MaxDamage = maxDamage;
        }
    }

    public int getExp(ServerPlayer player) {
        if (isPlayer1(player)) {
            return player1Exp;
        } else {
            return player2Exp;
        }
    }

    public void addExp(ServerPlayer player, int exp) {
        if (isPlayer1(player)) {
            player1MultiHit = player1MultiHit + 1;
            player2MultiHit = 0;
        } else {
            player2MultiHit = player2MultiHit + 1;
            player1MultiHit = 0;
        }
    }

    public void setLocation(ServerPlayer player1, ServerPlayer player2) {
        if (player1 == null || player2 == null) {
            return;
        }
        player1Position = new Positions(player1);
        player2Position = new Positions(player2);
    }

    public Positions getLocation(ServerPlayer player) {
        if (isPlayer1(player)) {
            return player1Position;
        }
        return player2Position;
    }

    public CustomDan getPlayerDan(ServerPlayer player) {
        if (isPlayer1(player)) {
            return player1Dan;
        } else {
            return player2Dan;
        }
    }

    public void setDan(ServerPlayer player, CustomDan dan) {
        if (isPlayer1(player)) {
            player1Dan = dan;
        } else {
            player2Dan = dan;
        }
    }

    public List<ItemStack> getPlayerInventoryItems(ServerPlayer player) {
        if (isPlayer1(player)) {
            return player1InventoryItems;
        } else {
            return player2InventoryItems;
        }
    }

    public ItemStack getPlayerHelmet(ServerPlayer player) {
        if (isPlayer1(player)) {
            return player1Helmet;
        } else {
            return player2Helmet;
        }
    }

    public ItemStack getPlayerChestPlate(ServerPlayer player) {
        if (isPlayer1(player)) {
            return player1ChestPlate;
        } else {
            return player2ChestPlate;
        }
    }

    public ItemStack getPlayerLeggings(ServerPlayer player) {
        if (isPlayer1(player)) {
            return player1Leggings;
        } else {
            return player2Leggings;
        }
    }

    public ItemStack getPlayerBoots(ServerPlayer player) {
        if (isPlayer1(player)) {
            return player1Boots;
        } else {
            return player2Boots;
        }
    }

    public ItemStack getPlayerOffHandItem(ServerPlayer player) {
        if (isPlayer1(player)) {
            return player1OffHandItem;
        } else {
            return player2OffHandItem;
        }
    }

    public boolean isPlayersCanTeleport() {
        return isPlayersCanTeleport;
    }

    protected void setCanTeleport(boolean isPlayersCanTeleport) {
        this.isPlayersCanTeleport = isPlayersCanTeleport;
    }

    public boolean isWatchersCanTeleport() {
        return isWatchersCanTeleport;
    }

    protected void setWatchersTeleport(boolean watchersTeleport) {
        this.isWatchersCanTeleport = watchersTeleport;
    }

    public int getTimeout() {
        return timeout;
    }

    public String getArenaName() {
        return arenaName;
    }

    public List<ServerPlayer> getWatchers() {
        return watchers;
    }

    public ServerPlayer getPlayer1() {
        return player1;
    }

    public ServerPlayer getPlayer2() {
        return player2;
    }

    public int getStartWay() {
        return startWay;
    }

    public void finish() {// 结束这个竞技场的比赛
        timer.cancel();
        enable = false;
        player1 = null;
        player2 = null;
        watchers = null;
        player1Damage = 0;
        player1MaxDamage = 0;
        player2Damage = 0;
        player2MaxDamage = 0;
        player1Exp = 0;
        player2Exp = 0;
        player1MultiHit = 0;
        player2MultiHit = 0;
        player1Position = null;
        player2Position = null;
        arenaCurrentlyStage = -1;
        isWatchersCanTeleport = false;
    }
}
