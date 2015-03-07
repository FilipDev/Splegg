/*
 *  Created by Filip P. on 2/27/15 5:45 PM.
 */

/*
 *  Created by Filip P. on 2/9/15 5:32 PM.
 */

package me.pauzen.splegg.players.data;

import me.pauzen.splegg.players.CorePlayer;

public abstract class Milestone {

    private int value;

    public Milestone(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public abstract void onReach(CorePlayer corePlayer, Tracker tracker);

    public void add(Tracker tracker) {
        tracker.addMilestone(this);
    }
}
