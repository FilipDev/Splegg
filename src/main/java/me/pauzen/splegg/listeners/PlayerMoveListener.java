/*
 *  Created by Filip P. on 2/27/15 5:51 PM.
 */

/*
 *  Created by Filip P. on 2/13/15 1:06 PM.
 */

package me.pauzen.splegg.listeners;

import me.pauzen.splegg.players.CorePlayer;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerMoveEvent;

public class PlayerMoveListener extends ListenerImplementation {

    private static final HandlerList handlers = new HandlerList();

    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onPlayerMove(PlayerMoveEvent e) {
        Location from = e.getFrom(), to = e.getTo();
        if (!areSimilar(from, to)) {
            e.setCancelled(new EfficientMoveEvent(e, CorePlayer.get(e.getPlayer())).call().isCancelled());
        }
    }

    public boolean areSimilar(Location location1, Location location2) {
        return location1.getBlockY() == location2.getBlockY() && location1.getBlockX() == location2.getBlockX() && location1.getBlockZ() == location2.getBlockZ();
    }

}
