/*
 *  Created by Filip P. on 3/1/15 2:38 PM.
 */

/*
 *  Created by Filip P. on 2/22/15 11:59 PM.
 */

package me.pauzen.splegg.inventory.elements.listeners;

import me.pauzen.splegg.inventory.misc.ClickType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public interface ClickListener {

    public void onClick(Player clicker, ClickType clickType, Inventory inventory);

}
