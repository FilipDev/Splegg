/*
 *  Created by Filip P. on 2/27/15 5:45 PM.
 */

/*
 *  Created by Filip P. on 2/15/15 12:13 PM.
 */

/*
 *  Created by Filip P. on 2/13/15 8:01 PM.
 */

package me.pauzen.splegg.players.data.events;

import me.pauzen.splegg.events.CallableEvent;
import me.pauzen.splegg.players.data.Tracker;
import org.bukkit.event.HandlerList;

public class TrackerValueChangeEvent extends CallableEvent {

    private static final HandlerList handlers = new HandlerList();

    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    private int oldValue;
    private int newValue;

    private Tracker tracker;

    public TrackerValueChangeEvent(int oldValue, int newValue, Tracker tracker) {
        this.oldValue = oldValue;
        this.newValue = newValue;
        this.tracker = tracker;
    }

    public int getOldValue() {
        return oldValue;
    }

    public int getNewValue() {
        return newValue;
    }

    public Tracker getTracker() {
        return tracker;
    }
}
