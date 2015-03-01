/*
 *  Created by Filip P. on 2/27/15 5:51 PM.
 */

/*
 *  Created by Filip P. on 2/13/15 1:06 PM.
 */

package me.pauzen.splegg.listeners;

import me.pauzen.splegg.events.CallablePlayerContainerEvent;
import me.pauzen.splegg.players.CorePlayer;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerMoveEvent;

public class EfficientMoveEvent extends CallablePlayerContainerEvent {

    private static final HandlerList handlers = new HandlerList();

    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    private PlayerMoveEvent event;

    public EfficientMoveEvent(PlayerMoveEvent event, CorePlayer corePlayer) {
        super(corePlayer);
        this.event = event;
    }

    public PlayerMoveEvent getEvent() {
        return event;
    }
}
