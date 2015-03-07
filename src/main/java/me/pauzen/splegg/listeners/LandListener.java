/*
 *  Created by Filip P. on 2/27/15 5:46 PM.
 */

package me.pauzen.splegg.listeners;

import me.pauzen.splegg.SpleggCore;
import me.pauzen.splegg.misc.BlockUtils;
import me.pauzen.splegg.players.CorePlayer;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;

public class LandListener extends ListenerImplementation {

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onFall(EntityDamageEvent e) {
        /*if (!(e.getEntity() instanceof Player)) {
            return;
        }

        if (e.getCause() != EntityDamageEvent.DamageCause.FALL) {
            return;
        }

        Player player = (Player) e.getEntity();

        CorePlayer corePlayer = CorePlayer.get(player);

        if (corePlayer.getCurrentArena() == null) {
            return;
        }

        if (!corePlayer.getCurrentArena().hasStarted()) {
            return;
        }

        List<Block> relative = new ArrayList<Block>() {{
            add(player.getLocation().getBlock().getRelative(BlockFace.DOWN));
        }};
        
        if (relative.get(0).getType() == Material.AIR) {
            relative.addAll(BlockUtils.getSupportingBlocks(player, 0.6));
        }

        for (Block block : relative) {
            if (block.getType() == Material.STATIONARY_WATER) {
                lose(corePlayer);
                return;
            }
        }
        
        destroyBlocks(relative);
        updateTracker(corePlayer);
        relaunch(player);
        
        e.setCancelled(true);*/
    }

    private void destroyBlocks(List<Block> blocks) {
        blocks.forEach(this::destroyBlock);
    }

    @EventHandler
    public void onPlayerMove(EfficientMoveEvent e) {

        Player player = e.getPlayer().getPlayer();

        CorePlayer corePlayer = e.getPlayer();

        if (corePlayer.getCurrentArena() == null) {
            return;
        }

        if (!corePlayer.getCurrentArena().hasStarted()) {
            return;
        }

        List<Block> relative = new ArrayList<Block>() {{
            add(player.getLocation().getBlock().getRelative(BlockFace.DOWN));
        }};

        if (relative.get(0).getType() == Material.AIR) {
            relative.addAll(BlockUtils.getSupportingBlocks(player, 0.6));
        }

        for (Block block : relative) {
            if (block.getType() == Material.STATIONARY_WATER) {
                lose(corePlayer);
                return;
            }
        }

        if (corePlayer.)
        destroyBlocks(relative);
        updateTracker(corePlayer);
    }
    
    /*
        @EventHandler
    public void onPlayerMove(PlayerMoveEvent e) {

        Player player = e.getPlayer();

        CorePlayer corePlayer = CorePlayer.get(player);

        if (corePlayer.getCurrentArena() == null) {
            return;
        }

        if (!corePlayer.getCurrentArena().hasStarted()) {
            return;
        }

        Block relative = player.getLocation().getBlock().getRelative(BlockFace.DOWN);

        if (relative.getType() == Material.STATIONARY_WATER) {
            lose(corePlayer);
        }
    }
     */

    public void lose(CorePlayer corePlayer) {
        corePlayer.getCurrentArena().lose(corePlayer);
    }

    public void destroyBlock(Block block) {
        block.getWorld().spawnFallingBlock(block.getLocation(), block.getType(), block.getData());
        Bukkit.getScheduler().runTaskLater(SpleggCore.getCore(), () -> block.setType(Material.AIR), 15);
        //SoundUtils.playSound(block.getLocation(), Sound.ZOMBIE_WOODBREAK, 6, 4);
    }

    public void updateTracker(CorePlayer corePlayer) {
        corePlayer.getTrackers().get("block_destroy").addValue(1);
    }

    public void relaunch(Player player) {
        player.setVelocity(new Vector(0, 1.5 + (0.07 * CorePlayer.get(player).getTrackers().get("block_destroy").getValue()), 0));
    }

}
