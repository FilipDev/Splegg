/*
 *  Created by Filip P. on 2/27/15 5:51 PM.
 */

/*
 *  Created by Filip P. on 2/2/15 11:24 PM.
 */

package me.pauzen.splegg.events;

import org.bukkit.Bukkit;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;

public abstract class CallableEvent extends Event implements Cancellable {

    private boolean cancelled = false;

    @Override
    public boolean isCancelled() {
        return this.cancelled;
    }

    @Override
    public void setCancelled(boolean set) {
        this.cancelled = set;
    }

    public CallableEvent call() {
        Bukkit.getPluginManager().callEvent(this);
        return this;
    }
}
