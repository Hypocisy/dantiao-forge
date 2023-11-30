package com.kyogi.dantiao.configuration;

import java.io.File;
import java.io.FileWriter;
import java.io.InputStream;
import java.nio.file.Path;
import java.util.Map;

import com.kyogi.dantiao.DantiaoMod;
import net.minecraftforge.fml.loading.FMLPaths;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.reader.StreamReader;

public class DataFile {
    // 记录区域：单挑大厅，KD全息图位置，WIN全息图位置，各个竞技场
    public static Path areasFile = FMLPaths.CONFIGDIR.get().resolve("areas.yml");
    // 记录积分商城中的商品
    public static Path shopFile = FMLPaths.CONFIGDIR.get().resolve("shop.yml");
    // 玩家的比赛记录
    public static Path recordsFile = FMLPaths.CONFIGDIR.get().resolve("records.yml");
    // 玩家数据，记录玩家积分，所用的语言文件
    public static Path playerDataFile = FMLPaths.CONFIGDIR.get().resolve("playersdata.yml");
    // 记录黑名单
    public static Path blacklistFile = FMLPaths.CONFIGDIR.get().resolve("blacklist.yml");
    // 特殊符号
    public static Path symbolsFile = FMLPaths.CONFIGDIR.get().resolve("symbols.yml");
    // 排行榜
    public static Path rankingFile = FMLPaths.CONFIGDIR.get().resolve("ranking.yml");

    // 排行榜
    public static Yaml areas;
    public static Yaml shop;
    public static Yaml records;
    public static Yaml playerData;
    public static Yaml blacklist;
    public static Yaml symbols;
    public static Yaml ranking;

    public static Yaml setYamlOptions() {
        DumperOptions options = new DumperOptions();
        options.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
        options.setPrettyFlow(true);
        return new Yaml(options);
    }

    public static void loadData() {
        try {
            Yaml yaml = setYamlOptions();
            areas = yaml.load((InputStream) areasFile);
            shop = yaml.load((InputStream) shopFile);
            records = yaml.load((InputStream) recordsFile);
            symbols = yaml.load((InputStream) symbolsFile);
            playerData = yaml.load((InputStream) playerDataFile);
            blacklist = yaml.load((InputStream) blacklistFile);
            ranking = yaml.load((InputStream) rankingFile);

        }catch (Exception e){
            DantiaoMod.LOGGER.error(e.toString());
        }
    }

    public static void saveAreas() {
        try (FileWriter writer = new FileWriter(areasFile.toFile())) {
            areas.serialize();
            areas.dump(areas, writer);

        } catch (Exception ignored) {
        }
    }

    public static void saveShop() {
        try (FileWriter writer = new FileWriter(shopFile.toFile())) {
            shop.dump(shop, writer);
        } catch (Exception ignored) {
        }
    }

    public static void saveRecords() {
        try (FileWriter writer = new FileWriter(recordsFile.toFile())) {
            records.dump(records, writer);
        } catch (Exception ignored) {
        }
    }

    public static void savepd() {
        try (FileWriter writer = new FileWriter(playerDataFile.toFile())) {
            playerData.dump(playerData, writer);
        } catch (Exception ignored) {
        }
    }

    public static void saveSymbols() {
        try (FileWriter writer = new FileWriter(symbolsFile.toFile())) {
            areas.dump(areas, writer);
        } catch (Exception ignored) {
        }
    }

    public static void saveBlackList() {
        try (FileWriter writer = new FileWriter(areasFile.toFile())) {
            areas.dump(areas, writer);
        } catch (Exception ignored) {
        }
    }

    public static void saveRanking() {
        try (FileWriter writer = new FileWriter(areasFile.toFile())) {
            areas.dump(areas, writer);
        } catch (Exception ignored) {
        }
    }

}
