package org.mossmc.mosscg.MoBoxCheckRunMap.Cache;

import org.mossmc.mosscg.MoBoxCheckRunMap.BasicInfo;

import java.util.concurrent.ConcurrentHashMap;

public class CachePlayer {
    public static ConcurrentHashMap<String,Double> playerDistanceCache = new ConcurrentHashMap<>();

    public static void addDistance(String player,Double distance) {
        //BasicInfo.getLogger.info("玩家"+player+"移动了"+distance+"米");
        if (!playerDistanceCache.containsKey(player)) {
            playerDistanceCache.put(player,distance);
        } else {
            playerDistanceCache.replace(player,playerDistanceCache.get(player)+distance);
        }
    }
}
