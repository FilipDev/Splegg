/*
 *  Created by Filip P. on 3/1/15 2:38 PM.
 */

/*
 *  Created by Filip P. on 2/23/15 9:41 PM.
 */

package me.pauzen.splegg.inventory.elements.listeners;

import me.pauzen.splegg.inventory.InventoryMenu;
import org.bukkit.inventory.Inventory;

public interface UpdateListener {

    public void onUpdate(InventoryMenu menu, Inventory inventory);
}
