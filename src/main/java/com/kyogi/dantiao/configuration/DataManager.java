package com.kyogi.dantiao.configuration;

import com.kyogi.dantiao.DantiaoMod;
import com.kyogi.dantiao.encapsulation.Good;
import com.kyogi.dantiao.encapsulation.Record;
import com.kyogi.dantiao.util.Positions;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.Mth;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static com.kyogi.dantiao.util.Positions.getServerLevelByName;

public class DataManager {
    public static DataFile dataFile = new DataFile();

    // 获取所有竞技场的编辑名
    public static List<String> getArenas(){
        Set<?> data = (Set<?>) dataFile.arenasData.get("Arenas");
        return data.stream().map(Object::toString).toList();
    }

    public static String getArenaDisplayName(String editName) { // 获取某个竞技场的展示名
        return dataFile.arenasData.get("Arenas." + editName + ".Name").toString().replace("&", "§");
    }

    public static Positions getArenaPointA(String editName) { // 获取某个竞技场的A点
        Level world = getServerLevelByName(DantiaoMod.getMinecraftServer(), dataFile.arenasData.get("Arenas." + editName + ".A.World").toString());
        int x = Integer.parseInt(dataFile.arenasData.get("Arenas." + editName + ".A.X").toString());
        int y = Integer.parseInt(dataFile.arenasData.get("Arenas." + editName + ".A.Y").toString());
        int z = Integer.parseInt(dataFile.arenasData.get("Arenas." + editName + ".A.Z").toString());
        float yaw = Float.parseFloat(dataFile.arenasData.get("Arenas." + editName + ".A.YAW").toString());
        float pitch = Float.parseFloat(dataFile.arenasData.get("Arenas." + editName + ".A.PITCH").toString());
        return new Positions(new BlockPos(x, y, z), new Vec3(Mth.cos(yaw), Mth.sin(pitch), Mth.sin(yaw)), world);
    }

    public static Positions getArenaPointB(String editName) { // 获取某个竞技场的B点
        Level world = getServerLevelByName(DantiaoMod.getMinecraftServer(), dataFile.arenasData.get("Arenas." + editName + ".B.World").toString());
        int x = Integer.parseInt(dataFile.arenasData.get("Arenas." + editName + ".B.X").toString());
        int y = Integer.parseInt(dataFile.arenasData.get("Arenas." + editName + ".B.Y").toString());
        int z = Integer.parseInt(dataFile.arenasData.get("Arenas." + editName + ".B.Z").toString());
        float yaw = Float.parseFloat(dataFile.arenasData.get("Arenas." + editName + ".B.YAW").toString());
        float pitch = Float.parseFloat(dataFile.arenasData.get("Arenas." + editName + ".B.PITCH").toString());
        return new Positions(new BlockPos(x, y, z), new Vec3(Mth.cos(yaw), Mth.sin(pitch), Mth.sin(yaw)), world);
    }

    public static List<String> getArenaCommands(String editName) { // 获取某个竞技场的指令组
        return List.of(dataFile.arenasData.get("Arenas." + editName + ".Commands").toString());
    }

    public static Positions getArenaWatchingPoint(String editName) { // 获取某个竞技场的观战点
        Level world = getServerLevelByName(DantiaoMod.getMinecraftServer(), dataFile.arenasData.get("Arenas." + editName + ".B.World").toString());
        int x = Integer.parseInt(dataFile.arenasData.get("Arenas." + editName + ".WatchingPoint.World").toString());
        int y = Integer.parseInt(dataFile.arenasData.get("Arenas." + editName + ".WatchingPoint.X").toString());
        int z = Integer.parseInt(dataFile.arenasData.get("Arenas." + editName + ".WatchingPoint.Z").toString());
        float yaw = Float.parseFloat(dataFile.arenasData.get("Arenas." + editName + ".WatchingPoint.YAW").toString());
        float pitch = Float.parseFloat(dataFile.arenasData.get("Arenas." + editName + ".WatchingPoint.PITCH").toString());
        return new Positions(new BlockPos(x, y, z), new Vec3(Mth.cos(yaw), Mth.sin(pitch), Mth.sin(yaw)), world);
    }

    public static void setArenaCommands(String editName, List<String> list) throws IOException { // 设置某竞技场的指令组
        dataFile.arenasData.put("Arenas." + editName + ".Commands", list);
        dataFile.saveArenas();
    }

    public static void setArenaWatchingPoint(String editName, Positions positions) throws IOException { // 设置某竞技场的观战点
        if (positions == null) {
            dataFile.arenasData.put("Arenas." + editName + ".WatchingPoint", null);
        } else {
            dataFile.arenasData.put("Arenas." + editName + ".WatchingPoint.World", positions.getLevel().toString());
            dataFile.arenasData.put("Arenas." + editName + ".WatchingPoint.X", positions.getPosition().getX());
            dataFile.arenasData.put("Arenas." + editName + ".WatchingPoint.Y", positions.getPosition().getY());
            dataFile.arenasData.put("Arenas." + editName + ".WatchingPoint.Z", positions.getPosition().getZ());
            dataFile.arenasData.put("Arenas." + editName + ".WatchingPoint.YAW", (float) positions.getRotation().y());
            dataFile.arenasData.put("Arenas." + editName + ".WatchingPoint.PITCH", (float) positions.getRotation().x());
        }
        dataFile.saveArenas();
    }

    public static void deleteArena(String editName) throws IOException { // 删除某个竞技场
        dataFile.arenasData.put("Arenas." + editName, null);
        dataFile.saveArenas();
    }

    public static Positions getHologramLocation(int type) { // 获取排行榜全息图的所在位置
        String prefix;
        if (type == 0) {
            prefix = "Dantiao-HD-Win.";
        } else {
            prefix = "Dantiao-HD-KD.";
        }
        return getPositions(prefix);
    }

    public static void setHologramLocation(int type, Positions positions) throws IOException { // 设置某排行榜全息图的所在位置
        String prefix;
        if (type == 0) {
            prefix = "Dantiao-HD-Win.";
        } else {
            prefix = "Dantiao-HD-KD.";
        }
        if (positions == null) {
            dataFile.arenasData.put(prefix.replace(".", ""), null);
        } else {
            dataFile.arenasData.put(prefix + "World", positions.getLevelComponent().toString());
            dataFile.arenasData.put(prefix + "X", positions.getPosition().getX());
            dataFile.arenasData.put(prefix + "Y", positions.getPosition().getY());
            dataFile.arenasData.put(prefix + "Z", positions.getPosition().getZ());
        }
        dataFile.saveArenas();
    }

    public static Positions getLobbyLocation() { // 获取大厅传送点的所在位置
        String prefix = "Dantiao-LobbyPoint.";
        return getPositions(prefix);
    }

    public static void setLobbyLocation(Positions positions) throws IOException { // 设置大厅传送点的所在位置
        if (positions == null) {
            dataFile.arenasData.put("Dantiao-LobbyPoint", null);
        } else {
            String prefix = "Dantiao-LobbyPoint.";
            dataFile.arenasData.put(prefix + "World", positions.getLevelComponent().toString());
            dataFile.arenasData.put(prefix + "X", positions.getPosition().getX());
            dataFile.arenasData.put(prefix + "Y", positions.getPosition().getY());
            dataFile.arenasData.put(prefix + "Z", positions.getPosition().getZ());
        }
        dataFile.saveArenas();
    }

    @Nullable
    private static Positions getPositions(String prefix) {
        String world = dataFile.arenasData.get(prefix + "World").toString();
        if (world == null) {
            return null;
        }
        int x = (int) dataFile.arenasData.get(prefix + "X");
        int y = (int) dataFile.arenasData.get(prefix + "Y");
        int z = (int) dataFile.arenasData.get(prefix + "Z");
        return new Positions(new BlockPos(x, y, z), world);
    }

    public static List<String> getBlacklist() throws IOException {
        List<?> blackList = (List<?>) dataFile.blacklistData.get("BlackList");
        return blackList.stream().map(Object::toString).toList();
    }

    public static void setBlacklist(List<String> blacklist) throws IOException {
        dataFile.blacklistData.put("BlackList", blacklist);
        dataFile.saveBlackList();
    }

    public static int getDanExp(String playerName) {
        return (int) dataFile.playersData.get(playerName + ".Exp");
    }

    public static void setDanExp(String playerName, int exp) throws IOException {
        dataFile.playersData.put(playerName + ".Exp", exp);
        dataFile.savePlayerData();
    }

    public static String getLanguageFile(String playerName) {
        return dataFile.playersData.get(playerName + ".Language").toString();
    }

    public static void setLanguageFile(String playerName, String language) throws IOException {
        dataFile.playersData.put(playerName + ".Language", language);
        dataFile.savePlayerData();
    }

    public static double getPoint(String playerName) {
        return (double) dataFile.playersData.get(playerName);
    }

    public static void setPoint(String playerName, double point) throws IOException {
        dataFile.playersData.put(playerName + ".Points", point);
        dataFile.savePlayerData();
    }

    public static int getHistoryGood() { // 获取历史商品总数
        if (dataFile.shopData.isEmpty()) {
            return -1;
        }
        return (int) dataFile.shopData.get("Num");
    }

    public static List<Good> getGoodList() { // 获取所有商品
        Set<?> set = (Set<?>) dataFile.shopData.get("Shop");
        return set.stream()
                .filter(key -> !key.toString().equals("Num"))
                .map(key -> {
                    ItemStack item = ItemStack.of((CompoundTag) dataFile.shopData.get(key + ".Item"));
                    double price = Double.parseDouble(dataFile.shopData.get(key + ".Price").toString());
                    String broadcast = dataFile.shopData.get(key + ".Broadcast").toString();
                    String description = dataFile.shopData.get(key + ".Description").toString();
                    int salesVolume = Integer.parseInt(dataFile.shopData.get(key + ".SalesVolume").toString());
                    int num = Integer.parseInt(key.toString().replace("n", ""));
                    return new Good(num, item, price, broadcast, description, salesVolume);
                })
                .toList();
    }

    public static void setBroadcastForGood(int num, String broadcast) throws IOException { // 为某个商品设置广播信息
        dataFile.shopData.put("n" + num + ".Broadcast", broadcast);
        dataFile.saveShop();
    }
    public static void setDescriptionForGood(int num, String description) throws IOException { // 为某个商品设置备注信息
        dataFile.shopData.put("n" + num + ".description", description);
        dataFile.saveShop();
    }
    public static void updateSalesVolumn(int num) throws IOException { // 更新销量
        int now = Integer.parseInt(dataFile.shopData.get("n" + num + ".description").toString());
        dataFile.shopData.get("n" + num + ".SalesVolume");
        dataFile.saveShop();
    }
    public static void updateHistoryGood() throws IOException { // 更新历史商品总数
        dataFile.shopData.put("Num", Integer.parseInt(dataFile.shopData.get("Num").toString()) + 1);
        dataFile.saveShop();
    }
    public static void addGood(int num, ItemStack item, double price) throws IOException { // 上架一个商品
        dataFile.shopData.put("n" + num + ".Item", item);
        dataFile.shopData.put("n" + num + ".Price", price);
        dataFile.saveShop();
    }
    public static void removeGood(int num) throws IOException { // 删除一个商品
        dataFile.shopData.remove("n" + num);
        dataFile.saveShop();
    }

    public static List<String> getWinRanking(){
        Set<?> winRankingSet = (Set<?>) dataFile.rankingData.get("Win");
        return winRankingSet.stream().map(Object::toString).toList();
    }
    public static List<String> getKDRanking(){
        Set<?> kdRankingSet = (Set<?>) dataFile.rankingData.get("KD");
        return kdRankingSet.stream().map(Object::toString).toList();
    }
    public static void setRanking(int type, List<String> ranking) throws IOException {
        if (type == 0) {
            dataFile.rankingData.put("Win", ranking);
        } else {
            dataFile.rankingData.put("KD", ranking);
        }
        dataFile.saveRanking();
    }
    public static int getWins(String playerName){ // 获取某玩家的胜利场数
        return Integer.parseInt(dataFile.recordsData.get(playerName + ".Win").toString());
    }
    public static int getDraws(String playerName){ // 获取某玩家的平局场数
        return Integer.parseInt(dataFile.recordsData.get(playerName + ".Draw").toString());
    }
    public static int getLoses(String playerName){ // 获取某玩家的失败场数
        return Integer.parseInt(dataFile.recordsData.get(playerName + ".Lose").toString());
    }

    public static int getWinningStreakTimes(String playerName){ // 获取某玩家的失败场数
        return Integer.parseInt(dataFile.recordsData.get(playerName + ".Winning-Streak-Times").toString());
    }
    public static int getMaxWinningStreakTimes(String playerName){ // 获取某玩家的失败场数
        return Integer.parseInt(dataFile.recordsData.get(playerName + ".Max-Winning-Streak-Times").toString());
    }
    public static void setWins(String name, int value) throws IOException { // 设置某玩家的胜利场数
        dataFile.recordsData.put(name + ".Win", value);
        dataFile.saveRanking();
    }

    public static void setLoses(String name, int value) throws IOException { // 设置某玩家的平局场数
        dataFile.recordsData.put(name + ".Lose", value);
        dataFile.saveRanking();
    }

    public static void setDraws(String name, int value) throws IOException { // 设置某玩家的平局场数
        dataFile.recordsData.put(name + ".Draws", value);
        dataFile.saveRanking();
    }

    public static void setWinningStreakTimes(String name, int value) throws IOException { // 设置某玩家的连胜次数
        dataFile.recordsData.put(name + ".Winning-Streak-Times", value);
        dataFile.saveRanking();
    }

    public static void setMaxWinningStreakTimes(String name, int value) throws IOException { // 设置某玩家的最大连胜次数
        dataFile.recordsData.put(name + ".Max-Winning-Streak-Times", value);
        dataFile.saveRanking();
    }
    public static void addRecord(String playerName, String date, String opponent,
                                 String server, int time, double damage, double maxDamage,
                                 int result, int startWay, int expChange, String arenaEditName) throws IOException { // 新增一条记录
        int logWins = Integer.parseInt(dataFile.recordsData.get(playerName + ".Win").toString());
        int logLoses = Integer.parseInt(dataFile.recordsData.get(playerName + ".Lose").toString());
        int logDraws = Integer.parseInt(dataFile.recordsData.get(playerName + ".Lose").toString());
        int logGameTimes = logWins + logLoses + logDraws;
        dataFile.recordsData.put(playerName + ".Record." + logGameTimes + ".player", opponent);
        dataFile.recordsData.put(playerName + ".Record." + logGameTimes + ".time", time);
        dataFile.recordsData.put(playerName + ".Record." + logGameTimes + ".date", date);
        dataFile.recordsData.put(playerName + ".Record." + logGameTimes + ".damage", damage);
        dataFile.recordsData.put(playerName + ".Record." + logGameTimes + ".maxdamage",
                maxDamage);
        switch (result){
            case 0: // 胜利
                dataFile.recordsData.put(playerName + ".Record." + logGameTimes + ".isWin", true);
                dataFile.recordsData.put(playerName + ".Record." + logGameTimes + ".isDraw", false);
                break;
            case 1: // 失败
                dataFile.recordsData.put(playerName + ".Record." + logGameTimes + ".isWin", false);
                dataFile.recordsData.put(playerName + ".Record." + logGameTimes + ".isDraw", false);
                break;
            case 2: // 平局
                dataFile.recordsData.put(playerName + ".Record." + logGameTimes + ".isWin", false);
                dataFile.recordsData.put(playerName + ".Record." + logGameTimes + ".isDraw", true);
                break;
        }
        dataFile.recordsData.put(playerName + ".Record." + logGameTimes + ".startWay",
                startWay);
        dataFile.recordsData.put(playerName + ".Record." + logGameTimes + ".expChange",
                expChange);
        dataFile.recordsData.put(playerName + ".Record." + logGameTimes + ".arenaEditName",
                arenaEditName);
        dataFile.recordsData.put(playerName + ".Record." + logGameTimes + ".server",
                server);
        dataFile.saveRecords();
    }
    public static int getRecordNumber(String name) { // 获取某玩家比赛记录的总条数
        int wins = Integer.parseInt(dataFile.recordsData.get(name + ".Win").toString());
        int loses = Integer.parseInt(dataFile.recordsData.get(name + ".Lose").toString());
        int draws = Integer.parseInt(dataFile.recordsData.get(name + ".Draw").toString());
        return wins + loses + draws;
    }
    public static List<Record> getRecordList(String playerName) { // 获取某条比赛记录
        Set<?> section = (Set<?>) dataFile.recordsData.get(playerName
                + ".Record");
        if (section != null) {
            return section.stream()
                    .map(subKey -> {
                        String prefix = dataFile.recordsData.get(playerName + ".Record." + subKey).toString();

                        String date = dataFile.recordsData.get(prefix + ".date").toString();
                        String opponent = dataFile.recordsData.get(prefix + ".player").toString();
                        String server = dataFile.recordsData.get(prefix + ".server").toString();

                        int time = Integer.parseInt(dataFile.recordsData.get(prefix + ".time").toString());
                        int damage = Integer.parseInt(dataFile.recordsData.get(prefix + ".damage").toString());
                        int maxDamage = Integer.parseInt(dataFile.recordsData.get(prefix + ".maxDamage").toString());
                        int result;
                        if (dataFile.recordsData.get(prefix + ".isWin").equals(true)) {
                            result = 0; // 胜利
                        } else {
                            if (dataFile.recordsData.get(prefix + ".isDraw").equals(true)) {
                                result = 2; // 失败
                            } else {
                                result = 1; // 平局
                            }
                        }
                        int startWay = Integer.parseInt(dataFile.recordsData.get(prefix + ".startWay").toString());
                        int expChange = Integer.parseInt(dataFile.recordsData.get(prefix + ".expChange").toString());
                        String arenaEditName = dataFile.recordsData.get(prefix + ".arenaEditName").toString();

                        return new Record(playerName, date, opponent, server, time, damage, maxDamage, result, startWay, expChange, arenaEditName);
                    }).toList();
        }
        // else return empty list
        return new ArrayList<>();
    }
    public static void setEnergy(String name, double energy) throws IOException { // 设置某玩家的精力值
        dataFile.playersData.put(name + ".Energy", energy);
        dataFile.savePlayerData();
    }
    public static double getEnergy(String name) { // 获取某玩家的精力值
        return Double.parseDouble(dataFile.playersData.get(name + ".Energy").toString());
    }

    public static List<ItemStack> getKitList(String kitName) {
        if (!dataFile.kitsData.isEmpty())
        {
            List<?> kitList = (List<?>) dataFile.kitsData.get(kitName + ".items");
            if (!kitList.isEmpty())
                return kitList.stream().map().toList();
        }

        ;
        return new ArrayList<>();
    }
}
