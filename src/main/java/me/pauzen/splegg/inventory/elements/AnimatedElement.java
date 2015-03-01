/*
 *  Created by Filip P. on 3/1/15 2:38 PM.
 */

/*
 *  Created by Filip P. on 2/23/15 9:39 PM.
 */

package me.pauzen.splegg.inventory.elements;

import me.pauzen.splegg.SpleggSuckMyAss;
import me.pauzen.splegg.inventory.InventoryMenu;
import me.pauzen.splegg.inventory.elements.listeners.UpdateListener;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class AnimatedElement extends Element {

    private InventoryMenu  inventoryMenu;
    private UpdateListener updateListener;

    public AnimatedElement(Material material, InventoryMenu inventoryMenu, UpdateListener updateListener, long delay) {
        this(new ItemStack(material), inventoryMenu, updateListener, delay);
    }

    public AnimatedElement(ItemStack itemStack, InventoryMenu inventoryMenu, UpdateListener updateListener, long delay) {
        super(itemStack);
        this.inventoryMenu = inventoryMenu;
        this.updateListener = updateListener;
        startUpdating(delay);
    }
    
    public void startUpdating(long delay) {
        Bukkit.getScheduler().runTaskTimer(SpleggSuckMyAss.getCore(), () -> inventoryMenu.getOpen().forEach((inventory) -> getListener().onUpdate(inventoryMenu, inventory)), 0L, delay);
    }

    public UpdateListener getListener() {
        return updateListener;
    }
}
