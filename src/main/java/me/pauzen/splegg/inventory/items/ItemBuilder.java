/*
 *  Created by Filip P. on 3/1/15 2:38 PM.
 */

/*
 *  Created by Filip P. on 2/20/15 8:57 PM.
 */

package me.pauzen.splegg.inventory.items;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class ItemBuilder {

    private ItemStack itemStack;
    private ItemMeta  itemMeta;

    private ItemBuilder(ItemStack itemStack) {

        this.itemStack = itemStack;

        if (itemStack.hasItemMeta()) {
            this.itemMeta = itemStack.getItemMeta();
        }
        else {
            itemMeta = Bukkit.getItemFactory().getItemMeta(itemStack.getType());
        }
    }

    private ItemBuilder(Material material) {

        this.itemStack = new ItemStack(material);

        itemMeta = Bukkit.getItemFactory().getItemMeta(material);
    }

    public ItemBuilder amount(int amount) {
        this.itemStack.setAmount(amount);
        return this;
    }

    public ItemBuilder durability(int durability) {
        this.itemStack.setDurability((short) durability);
        return this;
    }

    public ItemBuilder enchant(Enchantment enchantment, int level) {
        itemStack.addUnsafeEnchantment(enchantment, level);
        return this;
    }

    public ItemBuilder name(String name) {
        itemMeta.setDisplayName(name);
        return this;
    }

    public ItemBuilder name(String unformattedName, Object... varargs) {
        name(String.format(unformattedName, varargs));
        return this;
    }

    public ItemBuilder addLore(String lore) {
        List<String> lore1 = getLore();
        lore1.add(lore);
        itemMeta.setLore(lore1);
        return this;
    }
    
    private List<String> getLore() {
        if (!itemMeta.hasLore()) {
            itemMeta.setLore(new ArrayList<>());
        }
        return itemMeta.getLore();
    }

    public ItemBuilder clearLore() {
        itemMeta.setLore(new ArrayList<>());
        return this;
    }

    public ItemStack build() {
        this.itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    public static ItemBuilder from(Material material) {
        return new ItemBuilder(material);
    }

    public static ItemBuilder from(ItemStack itemStack) {
        return new ItemBuilder(itemStack);
    }

    public ItemBuilder data(byte data) {
        itemStack.setDurability(data);
        return this;
    }
}
