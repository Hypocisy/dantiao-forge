package com.kyogi.dantiao.configuration;

import net.minecraftforge.fml.loading.FMLPaths;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;

import java.io.*;
import java.nio.file.Path;
import java.util.Map;

public class YamlFileManager {
    private final Yaml yaml;

    public YamlFileManager(){
        this.yaml = new Yaml();
        DumperOptions options = new DumperOptions();
        options.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
    }
    public Map<String, Object> readYamlFile(Path filePath) throws IOException {
        try (InputStream inputStream = new FileInputStream(FMLPaths.CONFIGDIR.get().resolve(filePath).toFile())){
            return yaml.load(inputStream);
        }
    }
    public void writeYamlFile(Path filePath, Map<String, Object> data) throws IOException {
        try (OutputStream outputStream = new FileOutputStream(FMLPaths.CONFIGDIR.get().resolve(filePath).toFile())){
            yaml.dump(data, new OutputStreamWriter(outputStream));
        }
    }
}
