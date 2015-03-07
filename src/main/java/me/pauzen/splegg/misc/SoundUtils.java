/*
 *  Created by Filip P. on 2/28/15 6:08 PM.
 */

/*
 *  Created by Filip P. on 2/6/15 6:05 PM.
 */

package me.pauzen.splegg.misc;

import me.pauzen.splegg.SpleggCore;
import me.pauzen.splegg.players.CorePlayer;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.entity.Player;

public final class SoundUtils {

    private SoundUtils() {
    }

    public static void playSound(CorePlayer corePlayer, Sound sound, float volume, float pitch) {
        playSound(corePlayer.getPlayer(), sound, volume, pitch);
    }

    public static void playSound(Player player, Sound sound, float volume, float pitch) {
        player.playSound(player.getLocation(), sound, volume, pitch);
    }

    public static void playSound(Location location, Sound sound, float volume, float pitch) {
        location.getWorld().playSound(location, sound, volume, pitch);
    }

    public static void playSound(World world, Sound sound, final float volume, final float pitch) {
        world.getPlayers().forEach(player -> playSound(player, sound, volume, pitch));
    }

    public static void playSound(CorePlayer corePlayer, Sound sound, float volume, float pitch, int delay) {
        Bukkit.getScheduler().runTaskLater(SpleggCore.getCore(), () -> {
            playSound(corePlayer, sound, volume, pitch);
        }, delay);
    }
    
}
