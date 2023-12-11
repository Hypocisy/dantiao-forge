package com.kyogi.dantiao.arenas;

import com.kyogi.dantiao.DantiaoMod;
import com.kyogi.dantiao.event.arena.ArenaStartEvent;
import com.kyogi.dantiao.configuration.ConfigManager;
import com.kyogi.dantiao.configuration.DataManager;
import com.kyogi.dantiao.util.Positions;
import com.kyogi.dantiao.dan.DanHandler;
import com.kyogi.dantiao.request.RequestsHandler;
import com.kyogi.dantiao.util.ItemChecker;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.GameType;
import net.minecraftforge.common.MinecraftForge;

import java.io.IOException;
import java.util.List;
import java.util.Random;

import static com.kyogi.dantiao.arenas.ArenaManager.arenas;
import static com.kyogi.dantiao.arenas.ArenaManager.busyArenasName;
import static com.kyogi.dantiao.configuration.DantiaoConfig.Common.validateItemName;
import static com.kyogi.dantiao.util.Positions.getLevelComponent;

public class StartGame {

    /*
     * p1：选手1号 p2：选手2号 arenaName：指定竞技场，null则为随机 starter：强制开启比赛的管理员，null则为正常情况的开赛
     */
    public StartGame(ServerPlayer player1, ServerPlayer player2, String editName, ServerPlayer starter, int startWay) throws IOException {
        if (player1 == null || player2 == null) {// 玩家遁地了？！
            starter.sendSystemMessage(Component.literal("&c[x]警告：开赛时发生异常，不予开赛！"));
            return;
        }
        if (DataManager.getArenas().isEmpty()) {// 居然不设置竞技场？！
            player1.sendSystemMessage(Component.literal("&c服务器内没有设置任何竞技场！请联系管理员！"));
            player2.sendSystemMessage(Component.literal("&c服务器内没有设置任何竞技场！请联系管理员！"));
            return;
        }
        ConfigManager configManager = DantiaoMod.getConfigManager();
        if (configManager.isEnableWorldWhitelist()) {
            List<String> worldWhitelist = configManager.getWorldWhitelist();
            if (worldWhitelist != null) {
                if (!worldWhitelist.contains(getLevelComponent(player1).toString())) {
                    player2.sendSystemMessage(Component.literal("&c[x]你所在的世界已被禁止比赛，请移动到允许比赛的世界再开赛"));
                    player1.sendSystemMessage(Component.literal("&c[x]对手" + player2.getName() + "所在的世界已被禁止比赛，请等待TA移动到允许比赛的世界再开赛"));
                    return;
                }
                if (!worldWhitelist.contains(getLevelComponent(player2).toString())) {
                    player1.sendSystemMessage(Component.literal("&c[x]你所在的世界已被禁止比赛，请移动到允许比赛的世界再开赛"));
                    player2.sendSystemMessage(Component.literal("&c[x]对手" + player1.getName() + "所在的世界已被禁止比赛，请等待TA移动到允许比赛的世界再开赛"));
                    return;
                }
            }
        }

        List<String> itemNameList = configManager.getArenaLimitedItem();
        if (!itemNameList.isEmpty()) {
            for (String itemName : itemNameList) {
                if (validateItemName(itemName)) {
                    ItemChecker ic1 = new ItemChecker(player1, ItemChecker.getItemstark(itemName));
                    if (ic1.isHasItem()) {
                        player1.sendSystemMessage(Component.literal("&c[x]你的背包里携带有违禁品！不予开赛"));
                        player2.sendSystemMessage(Component.literal("&c[x]对手" + player1.getName() + "的背包里携带有违禁品！不予开赛"));
//						player1.closeInventory();
//						player2.closeInventory();
                        return;
                    }
                    ItemChecker ic2 = new ItemChecker(player2, ItemChecker.getItemstark(itemName));
                    if (ic2.isHasItem()) {
                        player2.sendSystemMessage(Component.literal("&c[x]你的背包里携带有违禁品！不予开赛"));
                        player1.sendSystemMessage(Component.literal("&c[x]对手" + player2.getName() + "的背包里携带有违禁品！不予开赛"));
//						player1.closeInventory();
//						player2.closeInventory();
                        return;
                    }
                }
            }
        }

        List<Tag> loreList = configManager.getArenaLimitedLore();
        if (!loreList.isEmpty()) {
            for (Tag lore : loreList) {
                ItemChecker ic1 = new ItemChecker(player1, lore);
                if (ic1.isHasItem()) {
                    player1.sendSystemMessage(Component.literal("&c[x]你的背包里携带有违禁品！不予开赛"));
                    player2.sendSystemMessage(Component.literal("&c[x]对手" + player1.getName() + "的背包里携带有违禁品！不予开赛"));
//					player1.closeInventory();
//					player2.closeInventory();
                    return;
                }
                ItemChecker ic2 = new ItemChecker(player2, lore);
                if (ic2.isHasItem()) {
                    player2.sendSystemMessage(Component.literal("&c[x]你的背包里携带有违禁品！不予开赛"));
                    player1.sendSystemMessage(Component.literal("&c[x]对手" + player2.getName() + "的背包里携带有违禁品！不予开赛"));
//					player1.closeInventory();
//					player2.closeInventory();
                    return;
                }
            }
        }

        if (editName == null) { // 非指定竞技场，即随机
            if (busyArenasName.size() == arenas.size()) {// 竞技场都满了
//				player1.closeInventory();
//				player2.closeInventory();"
                player1.sendSystemMessage(Component.literal("&c所有竞技场都满了！请稍后再试~&e(小提示：输入/dt ainfo可以查看所有竞技场的实时信息)"));
                player2.sendSystemMessage(Component.literal("&c所有竞技场都满了！请稍后再试~&e(小提示：输入/dt ainfo可以查看所有竞技场的实时信息)"));
                return;
            }
        } else {
            if (!arenas.contains(DantiaoMod.getArenaManager().getArena(
                    editName))) {
                starter.sendSystemMessage(Component.literal("&c不存在这个竞技场，请检查输入"));
                return;
            }
            if (busyArenasName.contains(editName)) {// 竞技场满了
                starter.sendSystemMessage(Component.literal("&c这个竞技场正在比赛中！请换一个试试"));
                return;
            }
        }
        Arena arena = null;
        if (editName == null) {
            arena = getRandomArena();
        }// 随机获取一个竞技场
        else {
            arena = DantiaoMod.getArenaManager().getArena(editName);
        }// 获取OP强制开始时指定的竞技场
        if (arena == null) {
            player1.sendSystemMessage(Component.literal("&c[x]警告：开赛时发生异常，不予开赛！"));
            player2.sendSystemMessage(Component.literal("&c[x]警告：开赛时发生异常，不予开赛！"));

//			player1.closeInventory();
//			player2.closeInventory();
            return;
        }

        ArenaStartEvent event = new ArenaStartEvent(player1, player2, arena);

        editName = arena.getArenaName();

        arena.setLocation(player1, player2);

        DanHandler danHandler = DantiaoMod.getDanHandler();
        arena.setDan(player1, danHandler.getPlayerDan(player1));
        arena.setDan(player2, danHandler.getPlayerDan(player2));
        Positions positionA = DataManager.getArenaPointA(editName);
        Positions positionB = DataManager.getArenaPointA(editName);

        player1.teleportTo(positionA.getPosition().getX(), positionA.getPosition().getY(), positionA.getPosition().getZ());
        player2.teleportTo(positionB.getPosition().getX(), positionB.getPosition().getY(), positionB.getPosition().getZ());
        if (player1.getAbilities().flying) {
            if (!player1.hasPermissions(2)) {
                player1.getAbilities().mayfly = false;
            }
        }
        if (player2.getAbilities().flying) {
            if (!player2.hasPermissions(2)) {
                player2.getAbilities().mayfly = false;
            }
        }
        player1.setHealth(player1.getMaxHealth());
        player2.setHealth(player2.getMaxHealth());
        player1.getFoodData().setFoodLevel(20);
        player1.getFoodData().setFoodLevel(20);
        player1.setGameMode(GameType.SURVIVAL);
        player2.setGameMode(GameType.SURVIVAL);

        arena.start(player1, player2, startWay);
        busyArenasName.add(editName);

        String arenaDisplayName = DantiaoMod.getCacheHandler()
                .getArenaInfoCache().get(editName).getDisplayName();
        if (arenaDisplayName.isEmpty()) {
            arenaDisplayName = "";
        }
        player1.sendSystemMessage(Component.literal("&b您已进入竞技场" + "Arena name:" + arenaDisplayName + player1.getName().getString()));
        player2.sendSystemMessage(Component.literal("&b您已进入竞技场" + "Arena name:" + arenaDisplayName + player1.getName().getString()));


        RequestsHandler requestsHandler = DantiaoMod.getInstance().getRequestsHandler();
        requestsHandler.clearRequests(player1, 0, player2);
        requestsHandler.clearRequests(player2, 0, player1);

        MinecraftForge.EVENT_BUS.post(event);

        if (starter != null) {
            starter.sendSystemMessage(Component.literal("&a[v]已强制开始"));
        }
    }

    public Arena getRandomArena() {
        if (DataManager.getArenas().isEmpty()) {
            return null;
        }
        List<String> freeArenasName = DataManager.getArenas();
        freeArenasName.removeAll(busyArenasName);
        if (freeArenasName.isEmpty()) {
            return null;
        }
        Random random = new Random();
        return DantiaoMod.getArenaManager().getArena(
                freeArenasName.get(random.nextInt(freeArenasName.size())));
    }
}
