/*
 *  Created by Filip P. on 2/27/15 5:45 PM.
 */

/*
 *  Created by Filip P. on 2/2/15 11:16 PM.
 */

package me.pauzen.splegg.players;

import me.pauzen.splegg.arena.Arena;
import me.pauzen.splegg.players.data.Tracker;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

public class CorePlayer {


    private String   playerName;
    private Arena    currentArena;
    private Location previousLocation;

    /**
     * String is Tracker id.
     */
    private Map<String, Tracker> trackers = new HashMap<>();

    public CorePlayer(Player player) {
        this.playerName = player.getName();
    }

    public Player getPlayer() {
        return Bukkit.getPlayer(playerName);
    }

    public Arena getCurrentArena() {
        return currentArena;
    }

    public void setCurrentArena(Arena currentArena) {
        this.currentArena = currentArena;
    }

    public Location getPreviousLocation() {
        return previousLocation;
    }

    public void setPreviousLocation(Location previousLocation) {
        this.previousLocation = previousLocation;
    }

    public Map<String, Tracker> getTrackers() {
        return trackers;
    }

    public static CorePlayer get(Player player) {
        return PlayerManager.getManager().getWrapper(player);
    }

    public boolean isInArena() {
        return currentArena != null;
    }
}