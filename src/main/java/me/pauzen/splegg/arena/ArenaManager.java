/*
 *  Created by Filip P. on 3/1/15 2:57 PM.
 */

package me.pauzen.splegg.arena;

import me.pauzen.splegg.arena.events.RegisterArenasEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class ArenaManager {
    
    private static ArenaManager manager;
    
    public static void register() {
        manager = new ArenaManager();
    }
    
    public static ArenaManager getManager() {
        return manager;
    }

    private Map<String, Arena> registeredArenas = new HashMap<>();

    private Arena defaultArena = new Arena("Default Splegg Arena", , 2, 0);

    public ArenaManager() {
        RegisterArenasEvent registerArenasEvent = new RegisterArenasEvent();

        registerArenasEvent.call();

        registerArenasEvent.getArenas().forEach(this::registerArena);

        arenaGetter = (time) -> defaultArena;
        


    }

    private Function<Long, Arena> arenaGetter;

    private Arena getArena() {
        return arenaGetter.apply(System.currentTimeMillis());
    }

    private void registerArena(Arena arena) {
        this.registeredArenas.put(arena.getName(), arena);
    }

    private Arena currentArena;

    public void setCurrentArena(Arena currentArena) {
        this.currentArena = currentArena;
    }

    public Arena getCurrentArena() {
        return currentArena;
    }
}
