/*
 *  Created by Filip P. on 3/1/15 2:38 PM.
 */

/*
 *  Created by Filip P. on 2/20/15 8:35 PM.
 */

package me.pauzen.splegg.inventory.elements;

import me.pauzen.splegg.inventory.InventoryMenu;
import me.pauzen.splegg.inventory.elements.listeners.ToggleListener;
import me.pauzen.splegg.inventory.items.ItemBuilder;
import me.pauzen.splegg.inventory.misc.Coordinate;
import me.pauzen.splegg.misc.Tuple;
import me.pauzen.splegg.players.CorePlayer;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.function.Predicate;

public class ToggleableElement extends Element {

    private Coordinate inventoryCoordinate;

    public static Element ON_ELEMENT  = new Element(ItemBuilder.from(Material.INK_SACK).durability(10).name("%s%sEnabled", ChatColor.GREEN, ChatColor.BOLD).build());
    public static Element OFF_ELEMENT = new Element(ItemBuilder.from(Material.INK_SACK).durability(8).name("%s%sDisabled", ChatColor.RED, ChatColor.BOLD).build());

    private boolean                                 currentState;
    private Predicate<Tuple<CorePlayer, Inventory>> predicate;


    private InventoryMenu  menu;
    private ToggleListener toggleListener;

    /**
     * @param menu         InventoryMenu instance which to apply modifications to (toggles)
     * @param coordinate   Coordinate where to modify the ItemStack.
     * @param defaultState The default state of the element.
     */
    public ToggleableElement(ToggleListener toggleListener, InventoryMenu menu, Coordinate coordinate, boolean defaultState) {
        super(defaultState ? ON_ELEMENT.getRepresentation() : OFF_ELEMENT.getRepresentation());
        currentState = defaultState;
        inventoryCoordinate = coordinate;
        this.menu = menu;
        this.toggleListener = toggleListener;
    }

    public ToggleableElement(ToggleListener toggleListener, InventoryMenu menu, Coordinate coordinate, Predicate<Tuple<CorePlayer, Inventory>> predicate) {
        super(Material.AIR);
        inventoryCoordinate = coordinate;
        this.menu = menu;
        this.predicate = predicate;
    }

    /**
     * Gets the Element which represents the on or off state.
     *
     * @param state Toggle status
     * @return Pre-made on or off element.
     */
    public Element getState(boolean state) {
        return state ? ON_ELEMENT : OFF_ELEMENT;
    }

    /**
     * Invoke this method to toggle element state
     *
     * @param player Player that toggled the element.
     */
    public void toggle(Player player, Inventory inventory) {
        currentState = !currentState;
        menu.updateElement(inventory, inventoryCoordinate, toState(currentState));
        onToggle(player, currentState);
    }
    
    public ItemStack toState(boolean state) {
        return state ? ON_ELEMENT.getRepresentation().clone() : OFF_ELEMENT.getRepresentation().clone();
    }
    
    public void testPredicate(CorePlayer corePlayer, Inventory inventory) {
        if (predicate != null) {
            currentState = predicate.test(new Tuple<>(corePlayer, inventory));
            setRepresentation(toState(currentState));
        }
    }

    /**
     * Called when the element is toggled to allow further processing.
     *
     * @param player   Player that toggled element state.
     * @param newState New element state.
     */
    public void onToggle(Player player, boolean newState) {
        toggleListener.onToggle(player, newState);
    }

    public static void toggleAt(InventoryMenu menu, Coordinate coordinate, Player player, Inventory inventory) {

        Element element = menu.getElementAt(coordinate);

        if (element instanceof ToggleableElement) {
            ToggleableElement toggleable = (ToggleableElement) element;

            toggleable.toggle(player, inventory);
        }
    }
}
