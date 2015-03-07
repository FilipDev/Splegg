/*
 *  Created by Filip P. on 3/1/15 9:08 PM.
 */

package me.pauzen.splegg.listeners;

import me.pauzen.splegg.arena.ArenaManager;
import me.pauzen.splegg.inventory.InventoryMenu;
import me.pauzen.splegg.inventory.elements.AnimatedElement;
import me.pauzen.splegg.inventory.elements.Element;
import me.pauzen.splegg.inventory.elements.InteractableElement;
import me.pauzen.splegg.inventory.items.ItemBuilder;
import me.pauzen.splegg.misc.GeneralUtils;
import me.pauzen.splegg.misc.InvisibleID;
import me.pauzen.splegg.players.CorePlayer;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class PlayerInteractListener extends ListenerImplementation {

    private InventoryMenu menu = new InventoryMenu("Select an Action!", 5) {
        @Override
        public void registerElements() {
            setBorder((coordinate) -> new AnimatedElement(ItemBuilder.from(Material.STAINED_GLASS_PANE).name("Some border").build(), this, (inventoryMenu, inventory) -> {
                ItemStack item = inventory.getItem(coordinate.toSlot());
                item.setDurability((short) ((item.getDurability() + 1) % 16));
            }, 1));

            setElementAt(2, 2, new InteractableElement((clicker, clickType, inventory) -> {
                        ArenaManager.getManager().getCurrentArena().addPlayer(CorePlayer.get(clicker));
                    }, ItemBuilder.from(Material.INK_SACK)
                                  .name("Join the Arena!")
                                  .durability(10)
                                  .addLore("Click here to join the current arena.")
                                  .build())
            );

            setElementAt(6, 2, new InteractableElement((clicker, clickType, inventory) -> {
                        ArenaManager.getManager().getCurrentArena().removePlayer(CorePlayer.get(clicker));
                    }, ItemBuilder.from(Material.INK_SACK)
                                  .name("Leave the currently joined arena.")
                                  .durability(1)
                                  .addLore("Click here to leave the current arena.")
                                  .build())
            );
        }

        @Override
        public void onOpen(CorePlayer corePlayer) {
            setElementAt(4, 2, new Element(ItemBuilder.from(Material.INK_SACK).name(corePlayer.isInArena() ? ChatColor.GREEN + "You are in the arena " + corePlayer.getCurrentArena().getName() + "." : ChatColor.RED + "You are not currently in an arena.").durability(corePlayer.isInArena() ? 10 : 1).build()));
        }
    };

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent e) {
        ItemStack item = e.getItem();

        if (item == null) {
            return;
        }

        if (item.getType() == Material.AIR) {
            return;
        }

        if (!item.hasItemMeta()) {
            return;
        }

        if (!item.getItemMeta().hasLore()) {
            return;
        }

        if (CorePlayer.get(e.getPlayer()).isInArena()) {

            if (InvisibleID.hasInvisibleID(getItemName(item))) {
                if (item.getType() == Material.ARROW) {
                    item.setAmount(item.getAmount() - 1);
                    if (item.getAmount() == 0) {
                        item.setType(Material.AIR);
                        return;
                    }

                    new  PotionEffect(PotionEffectType.SPEED, 3, 1).apply(e.getPlayer());
                }
                
                if (item.getType() == Material.BONE) {
                    item.setAmount(item.getAmount() - 1);
                    if (item.getAmount() == 0) {
                        item.setType(Material.AIR);
                        return;
                    }
                    
                    
                }
                
            }
        }

        if ((item.getItemMeta().getLore().size() < 2)) {
            return;
        }
        
        if (InvisibleID.hasInvisibleID(item.getItemMeta().getLore().get(1))) {
            menu.show(e.getPlayer());
        }
    }

    private String getItemName(ItemStack itemStack) {

        if (itemStack == null || itemStack.getType() == Material.AIR) {
            return "";
        }

        GeneralUtils.getMeta(itemStack);

        if (!itemStack.getItemMeta().hasDisplayName()) {
            return "";
        }

        return itemStack.getItemMeta().getDisplayName();

    }

}
