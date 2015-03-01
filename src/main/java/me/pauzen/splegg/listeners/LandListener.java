/*
 *  Created by Filip P. on 2/27/15 5:46 PM.
 */

package me.pauzen.splegg.listeners;

import me.pauzen.splegg.misc.SoundUtils;
import me.pauzen.splegg.players.CorePlayer;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.util.Vector;

public class LandListener extends ListenerImplementation {
    
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerMove(EfficientMoveEvent e) {
        
        if (e.getPlayer().getCurrentArena() == null) {
            return;
        }
        
        if (!e.getPlayer().getCurrentArena().hasStarted()) {
            return;
        }


        Block relative = e.getPlayer().getPlayer().getLocation().getBlock().getRelative(BlockFace.DOWN);
        
        if (e.getPlayer().isOnGround()) {
            destroyBlock(relative);
            relaunch(e.getPlayer().getPlayer());
        } else  if (relative.getType() == Material.STATIONARY_WATER) {
            lose(e.getPlayer());
        }
    }
    
    public void lose(CorePlayer corePlayer) {
        corePlayer.getCurrentArena().lose(corePlayer);
    }
    
    public void destroyBlock(Block block) {
        block.setType(Material.AIR);
        SoundUtils.playSound(block.getLocation(), Sound.ZOMBIE_WOODBREAK, 6, 4);
    }
    
    public void relaunch(Player player) {
        player.setVelocity(new Vector(0, 4, 0));
    }
    
}
