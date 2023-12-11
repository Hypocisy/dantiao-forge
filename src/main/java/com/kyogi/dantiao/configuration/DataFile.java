package com.kyogi.dantiao.configuration;

import com.kyogi.dantiao.DantiaoMod;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Map;

public class DataFile {
    private final Path arenasPath = Path.of("data/areas.yml");
    // 记录区域：单挑大厅，KD全息图位置，WIN全息图位置，各个竞技场
    private final Path shopPath = Path.of("data/shop.yml");
    // 记录积分商城中的商品
    private final Path recordsPath = Path.of("data/records.yml");
    // 玩家的比赛记录
    private final Path playersdataPath = Path.of("data/playersdata.yml");
    // 玩家数据，记录玩家积分，所用的语言文件
    private final Path blacklistPath = Path.of("data/blacklist.yml");
    // 记录黑名单
    private final Path symbolsPath = Path.of("data/symbols.yml");
    // 特殊符号
    private final Path rankingPath = Path.of("data/ranking.yml");
    private final Path kitsPath = Path.of("data/kits.yml");
    // 记录区域：单挑大厅，KD全息图位置，WIN全息图位置，各个竞技场
    public YamlFileManager yamlFileManager;
    public Map<String, Object> arenasData;
    public Map<String, Object> shopData;
    public Map<String, Object> recordsData;
    public Map<String, Object> playersData;
    public Map<String, Object> blacklistData;
    public Map<String, Object> symbolsData;
    public Map<String, Object> rankingData;
    public Map<String, Object> kitsData;

    public DataFile() {
        this.yamlFileManager = new YamlFileManager();
        try {
            this.arenasData = yamlFileManager.readYamlFile(arenasPath);
            this.shopData = yamlFileManager.readYamlFile(shopPath);
            this.recordsData = yamlFileManager.readYamlFile(recordsPath);
            this.playersData = yamlFileManager.readYamlFile(playersdataPath);
            this.symbolsData = yamlFileManager.readYamlFile(symbolsPath);
            this.rankingData = yamlFileManager.readYamlFile(rankingPath);
            this.blacklistData = yamlFileManager.readYamlFile(arenasPath);
        } catch (IOException e) {
            DantiaoMod.LOGGER.error(e.toString());
        }
    }

    public void saveArenas() throws IOException {
        this.yamlFileManager.writeYamlFile(arenasPath, arenasData);
    }

    public void saveShop() throws IOException {
        this.yamlFileManager.writeYamlFile(shopPath, shopData);
    }

    public void saveRecords() throws IOException {
        this.yamlFileManager.writeYamlFile(recordsPath, recordsData);
    }

    public void savePlayerData() throws IOException {
        this.yamlFileManager.writeYamlFile(playersdataPath, playersData);
    }

    public void saveSymbols() throws IOException {
        this.yamlFileManager.writeYamlFile(symbolsPath, symbolsData);
    }

    public void saveBlackList() throws IOException {
        this.yamlFileManager.writeYamlFile(blacklistPath, blacklistData);
    }

    public void saveRanking() throws IOException {
        this.yamlFileManager.writeYamlFile(rankingPath, rankingData);
    }
    public void saveKitsList() throws IOException {
        this.yamlFileManager.writeYamlFile(kitsPath, kitsData);
    }

}
