/*
 *  Created by Filip P. on 3/1/15 3:39 PM.
 */

package me.pauzen.splegg.arena.events;

import me.pauzen.splegg.arena.Arena;
import me.pauzen.splegg.events.CallableEvent;
import org.bukkit.event.HandlerList;

import java.util.ArrayList;
import java.util.List;

public class RegisterArenasEvent extends CallableEvent {

    private static final HandlerList handlers = new HandlerList();

    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    private List<Arena> arenas = new ArrayList<>();
    
    public void registerArena(Arena arena) {
        this.arenas.add(arena);
    }

    public List<Arena> getArenas() {
        return arenas;
    }
}
