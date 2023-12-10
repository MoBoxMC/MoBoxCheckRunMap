package org.mossmc.mosscg.MoBoxCheckRunMap.Listeners;

import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.mossmc.mosscg.MoBoxCheckRunMap.Cache.CachePlayer;

public class ListenerMove implements Listener {
    @EventHandler
    public static void onPlayerMove(PlayerMoveEvent event) {
        try {
            Location from = event.getFrom();
            Location to = event.getTo();
            if (to == null) return;
            if (from.getWorld() == null) return;
            if (to.getWorld() == null) return;
            if (!from.getWorld().getName().equals(to.getWorld().getName())) return;
            Double distance = from.distance(to);
            String player = event.getPlayer().getName();
            CachePlayer.addDistance(player,distance);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
