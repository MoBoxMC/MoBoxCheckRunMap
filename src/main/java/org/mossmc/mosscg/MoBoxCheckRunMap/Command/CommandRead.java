package org.mossmc.mosscg.MoBoxCheckRunMap.Command;

import org.bukkit.command.CommandSender;
import org.mossmc.mosscg.MoBoxCheckRunMap.Cache.CacheThread;
import org.mossmc.mosscg.MoBoxCheckRunMap.Main;

public class CommandRead {
    public static void read(CommandSender sender, String[] strings) {
        if (!sender.hasPermission("mbcrm.op")) {
            sender.sendMessage("你没有权限喵~");
            return;
        }
        if (strings.length == 0) {
            sender.sendMessage("未知指令喵~ 使用/mbcrm help查看帮助喵~");
            return;
        }
        switch (strings[0]) {
            case "check":
                sender.sendMessage("开始强制检查......");
                CacheThread.taskVoid();
                break;
            case "help":
                sender.sendMessage("MoBoxCheckRunMap指令帮助");
                sender.sendMessage("/mbcrm check 强制进行检查");
                sender.sendMessage("/mbcrm help 查看帮助");
                sender.sendMessage("/mbcrm reload 重载插件");
                break;
            case "reload":
                Main.reload();
                break;
            default:
                sender.sendMessage("未知指令喵~ 使用/mbcrm help查看帮助喵~");
                break;
        }
    }
}
