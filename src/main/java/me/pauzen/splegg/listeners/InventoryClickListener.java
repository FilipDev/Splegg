/*
 *  Created by Filip P. on 3/1/15 3:55 PM.
 */

package me.pauzen.splegg.listeners;

import me.pauzen.splegg.inventory.InventoryMenu;
import me.pauzen.splegg.inventory.elements.AnimatedElement;
import me.pauzen.splegg.inventory.elements.InteractableElement;
import me.pauzen.splegg.inventory.items.ItemBuilder;
import me.pauzen.splegg.misc.InvisibleID;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class InventoryClickListener extends ListenerRegisterer {

    private InventoryMenu menu = new InventoryMenu("Select an Action!", 5) {
        @Override
        public void registerElements() {
            setBorder((coordinate) -> new AnimatedElement(Material.STAINED_GLASS_PANE, this, (inventoryMenu, inventory) -> {
                ItemStack item = inventory.getItem(coordinate.toSlot());
                item.setDurability((short) ((item.getDurability() + 1) % 16));
            }, 1));

            setElementAt(3, 3, new InteractableElement((clicker, clickType, inventory) -> {
                
            }, ItemBuilder.from(Material.INK_SACK)
                          .name("Join the Arena!")
                          .durability(DyeColor.GREEN.getData())
                          .addLore("Click here to join the current arena.")
                          .build()));
        }
    };

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {

        if (e.getRawSlot() != 35) {
            return;
        }

        if (InvisibleID.hasInvisibleID(e.getCurrentItem().getItemMeta().getLore().get(1))) {

        }
    }

}
