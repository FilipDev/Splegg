/*
 *  Created by Filip P. on 3/1/15 2:38 PM.
 */

/*
 *  Created by Filip P. on 2/20/15 8:54 PM.
 */

package me.pauzen.splegg.inventory.elements;

import me.pauzen.splegg.inventory.elements.listeners.ClickListener;
import me.pauzen.splegg.inventory.misc.ClickType;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class InteractableElement extends Element {

    private ClickListener clickListener;

    public InteractableElement(ClickListener clickListener, Material material) {
        super(material);
        this.clickListener = clickListener;
    }

    public InteractableElement(ClickListener clickListener, ItemStack item) {
        super(item);
        this.clickListener = clickListener;
    }

    public void onClick(Player clicker, ClickType clickType, Inventory inventory) {
        clickListener.onClick(clicker, clickType, inventory);
    }

    public InteractableElement(ItemStack itemStack) {
        super(itemStack);
    }

}
