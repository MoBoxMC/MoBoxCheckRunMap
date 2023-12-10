package org.mossmc.mosscg.MoBoxCheckRunMap.Cache;

import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import org.mossmc.mosscg.MoBoxCheckRunMap.BasicInfo;
import org.mossmc.mosscg.MoBoxCheckRunMap.Config.ConfigGet;

import java.util.*;

public class CacheThread {
    public static BukkitTask task;
    public static void runTask() {
        if (ConfigGet.getBoolean("asyncTask")) {
            task = new BukkitRunnable() {
                @Override
                public void run() {
                    taskVoid();
                }
            }.runTaskTimerAsynchronously(BasicInfo.getInstance, ConfigGet.getInt("checkTime")*1200L, ConfigGet.getInt("checkTime")*1200L);
        } else {
            task = new BukkitRunnable() {
                @Override
                public void run() {
                    taskVoid();
                }
            }.runTaskTimer(BasicInfo.getInstance, ConfigGet.getInt("checkTime")*1200L, ConfigGet.getInt("checkTime")*1200L);
        }
    }

    public static void reloadTask() {
        task.cancel();
        runTask();
    }

    public static void taskVoid() {
        //发送头信息
        BasicInfo.getInstance.getServer().broadcastMessage(ConfigGet.getString("infoPrefix").replace("[time]",ConfigGet.getString("checkTime")));
        //复制一份缓存
        Map<String,Double> cacheMap = new LinkedHashMap<>(CachePlayer.playerDistanceCache);
        CachePlayer.playerDistanceCache.clear();
        //清除过小数据
        List<String> removePlayer = new ArrayList<>();
        cacheMap.forEach((player, distance) -> {if (cacheMap.get(player) < ConfigGet.getInt("minDistance")) removePlayer.add(player);});
        removePlayer.forEach(cacheMap::remove);
        //获取循环次数
        int rankTime = ConfigGet.getInt("maxPlayer");
        if (cacheMap.size() < rankTime) rankTime = cacheMap.size();
        //若没有人跑图
        if (rankTime == 0) BasicInfo.getInstance.getServer().broadcastMessage(ConfigGet.getString("infoNoPlayer"));
        //循环输出排行
        for (int i = 1; i <=rankTime ; i++) {
            String player = getMax(cacheMap);
            Double distance = cacheMap.get(player);
            String info = ConfigGet.getString("infoContent");
            info = info.replace("[rank]",String.valueOf(i));
            info = info.replace("[player]",player);
            info = info.replace("[distance]",String.valueOf(distance.intValue()));
            BasicInfo.getInstance.getServer().broadcastMessage(info);
        }
        //发送尾信息
        BasicInfo.getInstance.getServer().broadcastMessage(ConfigGet.getString("infoSuffix"));
    }

    public static String getMax(Map<String,Double> cacheMap) {
        final double[] maxDistance = {0.0};
        final String[] maxPlayer = {null};
        cacheMap.forEach((player, distance) -> {
            if (maxDistance[0] < distance) {
                maxDistance[0] = distance;
                maxPlayer[0] = player;
            }
        });
        return maxPlayer[0];
    }
}
