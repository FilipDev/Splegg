/*
 *  Created by Filip P. on 2/27/15 5:37 PM.
 */

package me.pauzen.splegg;

import me.pauzen.splegg.arena.ArenaManager;
import me.pauzen.splegg.listeners.ListenerRegisterer;
import me.pauzen.splegg.players.PlayerManager;
import org.bukkit.plugin.java.JavaPlugin;

public class SpleggCore extends JavaPlugin {

    private static SpleggCore core;

    public static SpleggCore getCore() {
        return core;
    }

    @Override
    public void onEnable() {
        core = this;

        registerManagers();
    }

    @Override
    public void onDisable() {
        unregisterManagers();

        core = null;
    }

    private void registerManagers() {
        ListenerRegisterer.register();
        ArenaManager.register();
        PlayerManager.register();
    }
    
    private void unregisterManagers() {
        
    }
    
}
