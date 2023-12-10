package org.mossmc.mosscg.MoBoxCheckRunMap.Cache;

import org.bukkit.Location;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import org.mossmc.mosscg.MoBoxCheckRunMap.BasicInfo;
import org.mossmc.mosscg.MoBoxCheckRunMap.Config.ConfigGet;

import java.util.HashMap;
import java.util.Map;

public class CacheLocation {
    public static Map<String, Location> playerLocationCache = new HashMap<>();

    public static void updateLocation(String player, Location to) {
        if (!playerLocationCache.containsKey(player)) {
            playerLocationCache.put(player,to);
        } else {
            Location from = playerLocationCache.get(player);
            if (to == null) return;
            if (from.getWorld() == null) return;
            if (to.getWorld() == null) return;
            if (!from.getWorld().getName().equals(to.getWorld().getName())) return;
            Double distance = playerLocationCache.get(player).distance(to);
            playerLocationCache.replace(player,to);
            CachePlayer.addDistance(player,distance);
        }
    }

    public static BukkitTask task;
    public static void runTask() {
        if (ConfigGet.getBoolean("asyncTask")) {
            task = new BukkitRunnable() {
                @Override
                public void run() {
                    taskVoid();
                }
            }.runTaskTimerAsynchronously(BasicInfo.getInstance, ConfigGet.getInt("locationTime")*20L, ConfigGet.getInt("locationTime")*20L);
        } else {
            task = new BukkitRunnable() {
                @Override
                public void run() {
                    taskVoid();
                }
            }.runTaskTimer(BasicInfo.getInstance, ConfigGet.getInt("locationTime")*20L, ConfigGet.getInt("locationTime")*20L);
        }
    }

    public static void taskVoid() {
        BasicInfo.getInstance.getServer().getOnlinePlayers().forEach(player -> {
            updateLocation(player.getName(),player.getLocation());
        });
    }
}
