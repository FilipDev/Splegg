/*
 *  Created by Filip P. on 2/27/15 5:46 PM.
 */

/*
 *  Created by Filip P. on 2/2/15 11:24 PM.
 */

package me.pauzen.splegg.listeners;

import me.pauzen.splegg.SpleggCore;
import org.bukkit.Bukkit;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;

public abstract class ListenerImplementation implements Listener {

    public ListenerImplementation() {
        Bukkit.getPluginManager().registerEvents(this, SpleggCore.getCore());
    }

    public void unregister(HandlerList handlerList) {
        handlerList.unregister(this);
    }

}
