/*
 *  Created by Filip P. on 2/27/15 5:46 PM.
 */

/*
 *  Created by Filip P. on 2/13/15 4:52 PM.
 */

package me.pauzen.splegg.listeners;

public class ListenerRegisterer {

    public static void register() {
        new LandListener();
        new PlayerMoveListener();
    }

}