/*
 *  Created by Filip P. on 2/27/15 5:51 PM.
 */

/*
 *  Created by Filip P. on 2/2/15 11:24 PM.
 */

package me.pauzen.splegg.events;

import me.pauzen.splegg.players.CorePlayer;

public abstract class CallablePlayerContainerEvent extends CallableEvent {

    private CorePlayer corePlayer;

    public CallablePlayerContainerEvent(CorePlayer corePlayer) {
        this.corePlayer = corePlayer;
    }

    public CorePlayer getPlayer() {
        return corePlayer;
    }
}
