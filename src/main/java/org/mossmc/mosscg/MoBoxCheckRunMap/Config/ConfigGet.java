package org.mossmc.mosscg.MoBoxCheckRunMap.Config;

import org.mossmc.mosscg.MoBoxCheckRunMap.BasicInfo;

public class ConfigGet {
    public static int getInt(String key) {
        return (int) BasicInfo.getConfig.get(key);
    }

    public static boolean getBoolean(String key) {
        return (boolean) BasicInfo.getConfig.get(key);
    }

    public static String getString(String key) {
        return BasicInfo.getConfig.get(key).toString().replace("&","§");
    }
}
