/*
 *  Created by Filip P. on 2/28/15 6:16 PM.
 */

package me.pauzen.splegg.game.events;

import me.pauzen.splegg.events.CallablePlayerContainerEvent;
import me.pauzen.splegg.players.CorePlayer;
import org.bukkit.block.Block;
import org.bukkit.event.HandlerList;

public class BlockDestroyEvent extends CallablePlayerContainerEvent {

    private static final HandlerList handlers = new HandlerList();

    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
    
    private Block block;

    public BlockDestroyEvent(CorePlayer corePlayer, Block block) {
        super(corePlayer);
        this.block = block;
    }
}
