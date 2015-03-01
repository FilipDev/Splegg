/*
 *  Created by Filip P. on 2/27/15 5:39 PM.
 */

package me.pauzen.splegg.arena;

import me.pauzen.splegg.messages.ChatMessage;
import me.pauzen.splegg.misc.SoundUtils;
import me.pauzen.splegg.players.CorePlayer;
import org.bukkit.Location;
import org.bukkit.Sound;

import java.util.ArrayList;
import java.util.List;

public class Arena {

    private List<CorePlayer> players = new ArrayList<>();
    private int playerLimit;
    private int pregameDelay;
    private String name;
    
    private boolean started;

    private Location spawnPoint;

    public Arena(String name, Location spawnPoint, int playerLimit, int pregameDelay) {
        this.spawnPoint = spawnPoint;
        this.playerLimit = playerLimit;
        this.pregameDelay = pregameDelay;
        this.name = name;
    }

    public void addPlayer(CorePlayer corePlayer) {
        players.add(corePlayer);
        ChatMessage.JOINED.sendMessage(corePlayer, name);
        SoundUtils.playSound(corePlayer, Sound.ORB_PICKUP, 6, 8);
        
        if (playerLimit == players.size()) {
            startGame();
        }
    }

    public void removePlayer(CorePlayer corePlayer) {
        players.add(corePlayer);
        ChatMessage.LEFT.sendMessage(corePlayer);
        SoundUtils.playSound(corePlayer, Sound.ORB_PICKUP, 6, 3);
    }

    public void startGame() {
        for (CorePlayer player : players) {
            player.setPreviousLocation(player.getPlayer().getLocation());
            player.getPlayer().teleport(spawnPoint);
        }
        this.started = true;
    }

    public void endGame() {
        this.started = false;
    }
    
    public void win(CorePlayer corePlayer) {
        ChatMessage.WON.sendMessage(corePlayer);
        revert(corePlayer);
        
        endGame();
    }
    
    public void lose(CorePlayer corePlayer) {
        ChatMessage.LOST.sendMessage(corePlayer);
        SoundUtils.playSound(corePlayer, Sound.ORB_PICKUP, 6, 5);
        SoundUtils.playSound(corePlayer, Sound.ORB_PICKUP, 6, 2, 10);
        revert(corePlayer);
        
        checkPlayers();
    }
    
    public void checkPlayers() {
        if (players.size() == 1) {
            win(players.get(0));
        }
    }
    
    public void revert(CorePlayer corePlayer) {
        corePlayer.getPlayer().teleport(corePlayer.getPreviousLocation());
        players.remove(corePlayer);
    }

    public boolean hasStarted() {
        return started;
    }

    public String getName() {
        return name;
    }
}
