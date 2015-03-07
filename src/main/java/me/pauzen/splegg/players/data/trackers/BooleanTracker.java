/*
 *  Created by Filip P. on 3/2/15 11:36 PM.
 */

/*
 *  Created by Filip P. on 3/2/15 11:29 PM.
 */

package me.pauzen.splegg.players.data.trackers;


import me.pauzen.splegg.players.data.Tracker;

public class BooleanTracker extends Tracker {

    public BooleanTracker(String id, boolean initialValue) {
        super(id, initialValue ? 1 : 0);
    }

    public void setBoolean(boolean value) {
        setValue(value ? 1 : 0);
    }
    
    public boolean getBoolean() {
        return getValue() == 1;
    }
    
    public void toggleBoolean() {
        setBoolean(!getBoolean());
    }

}
