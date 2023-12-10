package org.mossmc.mosscg.MoBoxCheckRunMap.Config;

import org.mossmc.mosscg.MoBoxCheckRunMap.BasicInfo;
import org.yaml.snakeyaml.Yaml;

import java.io.*;
import java.nio.file.Files;
import java.util.Map;

public class ConfigManager {
    public static String getConfigPath() {
        return BasicInfo.getDataFolder +"/config.yml";
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    public static void checkConfig(){
        try {
            File dir = new File(BasicInfo.getDataFolder.toString());
            if (!dir.exists()) {
                dir.mkdir();
            }
            File file = new File(getConfigPath());
            if (file.exists()) {
                return;
            }
            InputStream input = ConfigManager.class.getClassLoader().getResourceAsStream("config.yml");
            assert input != null;
            Files.copy(input, file.toPath());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void loadConfig() {
        Yaml yaml = new Yaml();
        FileInputStream input = null;
        try {
            input = new FileInputStream(getConfigPath());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        BasicInfo.getConfig = yaml.loadAs(input, Map.class);
    }
}
