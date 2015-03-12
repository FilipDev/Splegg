/*
 *  Created by Filip P. on 2/27/15 5:39 PM.
 */

package me.pauzen.splegg.arena;

import me.pauzen.splegg.SpleggCore;
import me.pauzen.splegg.game.states.GameState;
import me.pauzen.splegg.messages.ChatMessage;
import me.pauzen.splegg.messages.ErrorMessage;
import me.pauzen.splegg.misc.SoundUtils;
import me.pauzen.splegg.players.CorePlayer;
import me.pauzen.splegg.players.data.trackers.BlockDestroyTracker;
import me.pauzen.splegg.players.data.trackers.ShouldDestroyBlockTracker;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;

public class Arena {

    private List<CorePlayer> players = new ArrayList<>();
    private int playerLimit;
    private int pregameDelay;
    private String name;
    
    private GameState state;

    private Location spawnPoint;

    public Arena(String name, Location spawnPoint, int playerLimit, int pregameDelay) {
        this.spawnPoint = spawnPoint;
        this.playerLimit = playerLimit;
        this.pregameDelay = pregameDelay;
        this.name = name;
    }

    public void addPlayer(CorePlayer corePlayer) {
        
        if (state == GameState.CLOSED) {
            ErrorMessage.NO_JOIN.sendMessage(corePlayer);
            return;
        }
        
        players.add(corePlayer);
        ChatMessage.JOINED.sendMessage(corePlayer, name);
        SoundUtils.playSound(corePlayer, Sound.ORB_PICKUP, 6, 8);
        
        corePlayer.setCurrentArena(this);
        
        if (playerLimit == players.size()) {
            startGame();
        }
    }

    public void removePlayer(CorePlayer corePlayer) {
        
        if (this.hasStarted()) {
            lose(corePlayer);
            return;
        }
        
        players.remove(corePlayer);
        ChatMessage.LEFT.sendMessage(corePlayer);
        SoundUtils.playSound(corePlayer, Sound.ORB_PICKUP, 6, 3);

        corePlayer.setCurrentArena(null);
    }

    public void startGame() {
        for (CorePlayer player : players) {
            player.setPreviousLocation(player.getPlayer().getLocation());
            player.getPlayer().teleport(spawnPoint);
            ChatMessage.ABOUT_TO_START.sendMessage(player, String.valueOf(pregameDelay / 20));

            ShouldDestroyBlockTracker shouldDestroyBlockTracker = new ShouldDestroyBlockTracker();
            shouldDestroyBlockTracker.setBoolean(false);

            player.getTrackers().put("block_destroy", new BlockDestroyTracker());
            player.getTrackers().put("should_destroy", shouldDestroyBlockTracker);
            
            player.getPlayer().getInventory().addItem(new ItemStack(Material.BONE, 2));
            player.getPlayer().getInventory().addItem(new ItemStack(Material.ARROW, 2));
        }

        Bukkit.getScheduler().runTaskLater(SpleggCore.getCore(), () -> {
            this.state = GameState.STARTED;
            for (CorePlayer player : players) {
                player.getPlayer().setVelocity(new Vector(0, (Math.random() * 0.5D) + 1, 0));
                player.getTrackers().get("should_destroy").setValue(1);
            }
        }, pregameDelay);
    }

    public void endGame() {
        this.state = GameState.CLOSED;
    }
    
    public void win(CorePlayer corePlayer) {
        ChatMessage.WON.sendMessage(corePlayer);
        revert(corePlayer);
        
        endGame();
    }
    
    public void lose(CorePlayer corePlayer) {
        ChatMessage.LOST.sendMessage(corePlayer);
        SoundUtils.playSound(corePlayer, Sound.ORB_PICKUP, 6, 2);
        SoundUtils.playSound(corePlayer, Sound.ORB_PICKUP, 6, 1, 3);
        revert(corePlayer);
        corePlayer.setCurrentArena(null);

        checkPlayers();
    }
    
    public void checkPlayers() {
        if (players.size() == 1) {
            win(players.get(0));
        }
    }
    
    public void revert(CorePlayer corePlayer) {
        corePlayer.getPlayer().teleport(corePlayer.getPreviousLocation());
        corePlayer.getPlayer().setHealth(corePlayer.getPlayer().getMaxHealth());
        players.remove(corePlayer);
    }

    public boolean hasStarted() {
        return GameState.STARTED == state;
    }
    
    public void close() {
        state = GameState.CLOSED;
    }
    
    public GameState getState() {
        return state;
    }

    public String getName() {
        return name;
    }
}
