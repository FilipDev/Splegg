/*
 *  Created by Filip P. on 3/1/15 2:38 PM.
 */

/*
 *  Created by Filip P. on 2/20/15 8:34 PM.
 */

package me.pauzen.splegg.inventory.elements;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class Element {

    public static Element BLANK_ELEMENT = new Element(Material.AIR);

    private ItemStack representation;

    public Element(Material material) {
        this(new ItemStack(material));
    }

    public Element(ItemStack itemStack) {
        this.representation = itemStack;
    }
    
    protected void setRepresentation(ItemStack itemStack) {
        this.representation = itemStack;
    }

    public ItemStack getRepresentation() {
        return representation;
    }
}
