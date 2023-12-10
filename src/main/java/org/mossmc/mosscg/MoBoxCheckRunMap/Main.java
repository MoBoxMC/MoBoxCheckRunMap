package org.mossmc.mosscg.MoBoxCheckRunMap;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import org.mossmc.mosscg.MoBoxCheckRunMap.Cache.CacheLocation;
import org.mossmc.mosscg.MoBoxCheckRunMap.Cache.CachePlayer;
import org.mossmc.mosscg.MoBoxCheckRunMap.Cache.CacheThread;
import org.mossmc.mosscg.MoBoxCheckRunMap.Command.CommandRead;
import org.mossmc.mosscg.MoBoxCheckRunMap.Config.ConfigGet;
import org.mossmc.mosscg.MoBoxCheckRunMap.Config.ConfigManager;
import org.mossmc.mosscg.MoBoxCheckRunMap.Listeners.ListenerMove;

public class Main extends JavaPlugin {
    @Override
    public void onEnable() {
        BasicInfo.getInstance = this;
        BasicInfo.getDataFolder = this.getDataFolder().toPath();
        BasicInfo.getLogger = this.getLogger();

        BasicInfo.getLogger.info("正在初始化"+BasicInfo.pluginName+"......");
        PluginManager pluginManager = this.getServer().getPluginManager();

        BasicInfo.getLogger.info("正在加载配置文件......");
        ConfigManager.checkConfig();
        ConfigManager.loadConfig();

        if (ConfigGet.getString("mode").equals("MOVE")) {
            BasicInfo.getLogger.info("插件运行模式：MOVE");
            BasicInfo.getLogger.info("正在载入监听器......");
            pluginManager.registerEvents(new ListenerMove(),this);
        } else {
            BasicInfo.getLogger.info("插件运行模式：LOCATION");
            BasicInfo.getLogger.info("正在载入任务......");
            CacheLocation.runTask();
        }

        BasicInfo.getLogger.info("正在加载任务......");
        CacheThread.runTask();

        BasicInfo.getLogger.info(BasicInfo.pluginName+"初始化完成！");
    }

    public static void reload() {
        BasicInfo.getLogger.info("正在重载"+BasicInfo.pluginName+"......");

        BasicInfo.getLogger.info("正在取消监听器......");
        PlayerMoveEvent.getHandlerList().unregister(BasicInfo.getInstance);

        BasicInfo.getLogger.info("正在取消任务......");
        if (CacheLocation.task != null && !CacheLocation.task.isCancelled()) {
            CacheLocation.task.cancel();
            BasicInfo.getLogger.info("坐标计算任务已取消");
        }

        BasicInfo.getLogger.info("正在清除缓存......");
        CacheLocation.playerLocationCache.clear();
        CachePlayer.playerDistanceCache.clear();

        BasicInfo.getLogger.info("正在重载配置文件......");
        ConfigManager.loadConfig();

        if (ConfigGet.getString("mode").equals("MOVE")) {
            BasicInfo.getLogger.info("插件运行模式：MOVE");
            BasicInfo.getLogger.info("正在载入监听器......");
            BasicInfo.getInstance.getServer().getPluginManager().registerEvents(new ListenerMove(),BasicInfo.getInstance);
        } else {
            BasicInfo.getLogger.info("插件运行模式：LOCATION");
            BasicInfo.getLogger.info("正在载入任务......");
            CacheLocation.runTask();
        }

        BasicInfo.getLogger.info("正在重载任务......");
        CacheThread.reloadTask();

        BasicInfo.getLogger.info(BasicInfo.pluginName+"重载完成！");
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
        CommandRead.read(sender,args);
        return true;
    }
}