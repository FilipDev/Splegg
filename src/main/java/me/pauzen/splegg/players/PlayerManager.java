/*
 *  Created by Filip P. on 2/27/15 5:45 PM.
 */

/*
 *  Created by Filip P. on 2/2/15 11:16 PM.
 */

package me.pauzen.splegg.players;

import me.pauzen.alphacore.inventory.items.ItemBuilder;
import me.pauzen.splegg.arena.Arena;
import me.pauzen.splegg.listeners.ListenerImplementation;
import me.pauzen.splegg.misc.InvisibleID;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PlayerManager extends ListenerImplementation {

    private static PlayerManager manager;

    public static Collection<CorePlayer> getCorePlayers() {
        return manager.players.values();
    }

    @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
    public void onPlayerJoin(PlayerJoinEvent e) {
        if (getWrapper(e.getPlayer()) == null) {
            registerPlayer(e.getPlayer());
            CorePlayer corePlayer = CorePlayer.get(e.getPlayer());
            
            corePlayer.getPlayer().getInventory().setItem(36, ItemBuilder.from(Material.BEACON)
                                                                         .name("ArenaThing")
                                                                         .lore("Use me to select an arena!")
                                                                         .lore(InvisibleID.generate().getId())
                                                                         .build());
        }
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onPlayerQuit(PlayerQuitEvent e) {
        destroyWrapper(e.getPlayer());
    }

    public static void register() {
        manager = new PlayerManager();
    }

    public static PlayerManager getManager() {
        return manager;
    }

    private Map<UUID, CorePlayer> players = new HashMap<>();

    public void registerPlayer(Player player) {
        this.players.put(player.getUniqueId(), new CorePlayer(player));
    }

    public CorePlayer getWrapper(Player player) {
        return players.get(player.getUniqueId());
    }

    public void destroyWrapper(Player player) {

        CorePlayer corePlayer = CorePlayer.get(player);
        if (corePlayer.isInArena()) {
            Arena currentArena = corePlayer.getCurrentArena();
            currentArena.lose(corePlayer);
        }
        
        this.players.remove(player.getUniqueId());
    }

}
