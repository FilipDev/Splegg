/*
 *  Created by Filip P. on 3/1/15 2:38 PM.
 */

/*
 *  Created by Filip P. on 2/20/15 7:01 PM.
 */

package me.pauzen.splegg.inventory;

import me.pauzen.splegg.inventory.elements.Element;
import me.pauzen.splegg.inventory.elements.InteractableElement;
import me.pauzen.splegg.inventory.elements.ToggleableElement;
import me.pauzen.splegg.inventory.misc.ClickType;
import me.pauzen.splegg.inventory.misc.Coordinate;
import me.pauzen.splegg.inventory.misc.ElementGetter;
import me.pauzen.splegg.misc.InvisibleID;
import me.pauzen.splegg.players.CorePlayer;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.*;
import java.util.function.Predicate;

public abstract class InventoryMenu {

    private Map<Coordinate, Element> elementMap = new HashMap<>();

    private Map<Coordinate, Predicate<InventoryClickEvent>> allowedClicking = new HashMap<>();

    private InvisibleID inventoryID;

    private Map<UUID, Inventory> openedInventories = new HashMap<>();

    private String     name;
    private int        size;
    private Coordinate endCoordinate;

    /**
     * Creates new Inventory with null holder, size and name as specified with the name getting an invisible unique ID appended to the end of it.
     *
     * @param name The desired name of the inventory.
     * @param rows The desired amount of rows in the inventory.
     */
    public InventoryMenu(String name, int rows) {
        this.name = name;
        this.size = rows * 9;
        endCoordinate = Coordinate.fromSlot(size - 1);
        inventoryID = InvisibleID.generate();
        registerElements();
        fillRemaining();
        InventoryManager.getManager().registerMenu(this);
    }

    public void openInventory(Player player, Inventory inventory) {
        this.openedInventories.put(player.getUniqueId(), inventory);
    }

    public void closeInventory(Player player) {
        this.openedInventories.remove(player.getUniqueId());
    }

    public Inventory generateInventory(Player player) {

        Inventory inventory = Bukkit.createInventory(null, size, name + inventoryID.getId());

        onOpen(CorePlayer.get(player));
        
        elementMap.entrySet().forEach(entry -> {

            if (entry.getKey() == null || entry.getValue() == null) {
                return;
            }
            
            int i = entry.getKey().toSlot();

            if (entry.getValue() instanceof ToggleableElement) {
                ToggleableElement toggleableElement = (ToggleableElement) entry.getValue();

                toggleableElement.testPredicate(CorePlayer.get(player), inventory);
            }
            inventory.setItem(i, entry.getValue().getRepresentation());
        });

        return inventory;
    }

    /**
     * Processes InventoryClickEvent and looks for any interactable elements that the player clicked.
     *
     * @param event InventoryClickEvent to process.
     */
    public final void process(InventoryClickEvent event) {

        Coordinate clicked = toCoordinate(event.getRawSlot());

        Element clickedElement = elementMap.get(clicked);

        if (clickedElement instanceof InteractableElement) {
            InteractableElement interactableElement = (InteractableElement) clickedElement;

            interactableElement.onClick((Player) event.getWhoClicked(), event.getAction() == InventoryAction.PICKUP_ALL ? ClickType.INVENTORY_LEFT :
                    event.getAction() == InventoryAction.PICKUP_HALF ? ClickType.INVENTORY_RIGHT : ClickType.OTHER, event.getInventory());
        }
        if (!shouldAllowClick(event)) {
            event.setCancelled(true);
            event.setResult(Event.Result.DENY);
        }
    }

    /**
     * Returns whether the InventoryClickEvent should be cancelled or not.
     *
     * @param e The InventoryClickEvent to get the outcome of.
     * @return Whether or not to cancel the event.
     */
    public boolean shouldAllowClick(InventoryClickEvent e) {

        if (e.getRawSlot() >= size) {
            return true;
        }

        Coordinate inventoryCoordinate = toCoordinate(e.getRawSlot());

        Predicate<InventoryClickEvent> inventoryClickEventPredicate = allowedClicking.get(inventoryCoordinate);

        return inventoryClickEventPredicate != null && inventoryClickEventPredicate.test(e);
    }

    /**
     * Where to register elements and such.
     */
    public abstract void registerElements();

    /**
     * Triggered when a player opens an inventory.
     * @param corePlayer Player that opened the inventory.
     */
    public void onOpen(CorePlayer corePlayer) {}

    /**
     * Fills unregistered element slots with blank elements.
     */
    public void fillRemaining() {
        for (int i = 0; i < size; i++) {

            Coordinate coordinate = toCoordinate(i);

            elementMap.putIfAbsent(coordinate, Element.BLANK_ELEMENT);
        }
    }

    /**
     * Shows the inventory to a player.
     *
     * @param player Player to show the inventory to.
     */
    public void show(Player player) {
        Inventory inventory = generateInventory(player);
        player.openInventory(inventory);
        openInventory(player, inventory);
    }

    /**
     * Shows the inventory to a player.
     *
     * @param corePlayer Player to show the inventory to.
     */
    public void show(CorePlayer corePlayer) {
        show(corePlayer.getPlayer());
    }

    /**
     * Updates an element in an already open inventory.
     *
     * @param coordinate Where to execute the update.
     * @param item       What to update the item to.
     */
    public void updateElement(Inventory inventory, Coordinate coordinate, ItemStack item) {
        inventory.setItem(coordinate.toSlot(), item);
    }

    /**
     * Updates an element in an already open inventory.
     *
     * @param coordinate Where to execute the update.
     */
    public void updateElement(Inventory inventory, Coordinate coordinate) {
        updateElement(inventory, coordinate, elementMap.get(coordinate).getRepresentation());
    }

    /**
     * Creates a condition that must be met if shouldAllowClick were to return true.
     *
     * @param inventoryCoordinate The coordinates where the allowance condition should be checking.
     * @param predicate           The condition that must be met.
     */
    public void createAllowanceConditionForClickingAt(Coordinate inventoryCoordinate, Predicate<InventoryClickEvent> predicate) {
        allowedClicking.put(inventoryCoordinate, predicate);
    }

    /**
     * Creates a condition that must be met if shouldAllowClick were to return true.
     *
     * @param predicate The condition that must be met.
     */
    public void createAllowanceConditionForClickingAt(int x, int y, Predicate<InventoryClickEvent> predicate) {
        createAllowanceConditionForClickingAt(new Coordinate(x, y), predicate);
    }
    
    public void createAllowanceConditionForClickingAt(int x, int y) {
        createAllowanceConditionForClickingAt(x, y, (clickEvent) -> true);
    }
    
    public void allowClickingWithin(Coordinate coordinate1, Coordinate coordinate2, Predicate<InventoryClickEvent> predicate) {
        for (Coordinate coordinate : getCoordinatesWithin(coordinate1, coordinate2)) {
            createAllowanceConditionForClickingAt(coordinate, predicate);
        }
    }
    
    public Coordinate[] getCoordinatesWithin(Coordinate coordinate1, Coordinate coordinate2) {
        int minX = Math.min(coordinate1.getX(), coordinate2.getX());
        int minY = Math.min(coordinate1.getY(), coordinate2.getY());
        int maxX = Math.max(coordinate1.getX(), coordinate2.getX());
        int maxY = Math.max(coordinate1.getY(), coordinate2.getY());

        Coordinate[] coordinates = new Coordinate[(maxX - minX + 1) * (maxY - minY + 1)];

        int curr = 0;

        for (int y = minY; y <= maxY; y++) {
            for (int x = minX; x <= maxX; x++) {
                coordinates[curr] = Coordinate.coordinate(x, y);
                curr++;
            }
        }
        return coordinates;
    }
    
    public Coordinate[] getBorder(Coordinate a, Coordinate b) {

        int minX = Math.min(a.getX(), b.getX());
        int minY = Math.min(a.getY(), b.getY());
        
        int maxX = Math.max(a.getX(), b.getX());
        int maxY = Math.max(a.getY(), b.getY());

        Coordinate[] coordinates = new Coordinate[(maxX - minX) * 2 + (maxY - minY) * 2];

        int curr = 0;

        for (Coordinate coordinate : getCoordinatesWithin(Coordinate.coordinate(minX, minY), Coordinate.coordinate(minX, maxY))) {
            coordinates[curr] = coordinate;
            curr++;
        }

        for (Coordinate coordinate : getCoordinatesWithin(Coordinate.coordinate(maxX, minY), Coordinate.coordinate(maxX, maxY))) {
            coordinates[curr] = coordinate;
            curr++;
        }

        for (Coordinate coordinate : getCoordinatesWithin(Coordinate.coordinate(minX + 1, maxY), Coordinate.coordinate(maxX - 1, maxY))) {
            coordinates[curr] = coordinate;
            curr++;
        }

        for (Coordinate coordinate : getCoordinatesWithin(Coordinate.coordinate(minX + 1, minY), Coordinate.coordinate(maxX - 1, minY))) {
            coordinates[curr] = coordinate;
            curr++;
        }

        return coordinates;
    }

    /**
     * Gets all elements surrounding a coordinate.
     *
     * @param coordinate The coordinate to get the elements around.
     * @return The found elements.
     */
    public Element[] getElementsAround(Coordinate coordinate) {
        Element[] foundElements = new Element[8];
        for (int i = 0; i < Coordinate.Direction.values().length; i++) {
            foundElements[i] = getElementAt(Coordinate.Direction.values()[i].getRelative(coordinate));
        }
        return foundElements;
    }

    /**
     * Gets all elements between two points.
     * @param x1 x value of first point.
     * @param y1 y value of first point.
     * @param x2 x value of second point.
     * @param y2 y value of second point.
     * @return An array of elements found between the two points.
     */
    
    public Element[] getElementsBetween(int x1, int y1, int x2, int y2) {
        
        Element[] elements = new Element[size];
        
        int slot = 0;

        for (Coordinate coordinate : getCoordinatesWithin(Coordinate.coordinate(x1, y1), Coordinate.coordinate(x2, y2))) {
            elements[slot] = getElementAt(coordinate);
            slot++;
        }

        return elements;
    }

    private static Coordinate toCoordinate(int x, int y) {
        return Coordinate.coordinate(x, y);
    }

    private static Coordinate toCoordinate(int inventorySlot) {
        return Coordinate.fromSlot(inventorySlot);
    }

    public String getName() {
        return name;
    }

    public String getID() {
        return inventoryID.getId();
    }

    public Element getElementAt(Coordinate coordinate) {
        return elementMap.get(coordinate);
    }

    public Element getElementAt(int x, int y) {
        return elementMap.get(new Coordinate(x, y));
    }
    
    public void setTypeAt(int x, int y, Material type) {
        setElementAt(x, y, new Element(type));
    }
    
    public void setItemAt(int x, int y, ItemStack itemStack) {
        setElementAt(x, y, new Element(itemStack));
    }

    /**
     * Set all elements between two points to an element.
     * @param x1 x value of first point.
     * @param y1 y value of first point.
     * @param x2 x value of second point.
     * @param y2 y value of second point.
     * @param elementGetter A functional element that retrieves to do element based on a coordinate.
     */
    public void setElementsBetween(int x1, int y1, int x2, int y2, ElementGetter elementGetter) {
        for (Coordinate coordinate : getCoordinatesWithin(Coordinate.coordinate(x1, y1), Coordinate.coordinate(x2, y2))) {
            setElementAt(coordinate, elementGetter.getElement(coordinate));
        }
    }
    
    public void setElementsBetween(Coordinate coordinate1, Coordinate coordinate2, ElementGetter elementGetter) {
        setElementsBetween(coordinate1.getX(), coordinate1.getY(), coordinate2.getX(), coordinate2.getY(), elementGetter);
    }
    
    public void setBorder(ElementGetter elementGetter) {
        for (Coordinate coordinate : getBorder(Coordinate.coordinate(0, 0), endCoordinate)) {
            setElementAt(coordinate, elementGetter.getElement(coordinate));
        }
    }
    
    public ItemStack getItemAt(int x, int y) {
        Element element = getElementAt(x, y);
        if (element == null) {
            return new ItemStack(Material.AIR);
        }
        return element.getRepresentation();
    }

    public Material getTypeAt(int x, int y) {
        return getItemAt(x, y).getType();
    }

    public ItemStack getItemAt(Inventory inventory, int x, int y) {
        return inventory.getItem(Coordinate.asSlot(x, y));
    }

    public Material getTypeAt(Inventory inventory, int x, int y) {
        return getItemAt(inventory, x, y).getType();
    }

    public ItemStack getItemAt(Inventory inventory, Coordinate coordinate) {
        return getItemAt(inventory, coordinate.getX(), coordinate.getY());
    }

    public Material getTypeAt(Inventory inventory, Coordinate coordinate) {
        return getItemAt(inventory, coordinate).getType();
    }
    
    public void setElementAt(Coordinate coordinate, Element element) {
        this.elementMap.put(coordinate, element);
    }

    public void setElementAt(int x, int y, Element element) {
        this.elementMap.put(new Coordinate(x, y), element);
    }

    /**
     * Gets all elements in the menu.
     * @return A list of all elements.
     */
    public List<Element> getElements() {
        
        List<Element> elements = new ArrayList<>();
        
        elementMap.entrySet().stream().map(Map.Entry::getValue).forEach(elements::add);
        
        return elements;
    }

    public Collection<Inventory> getOpen() {
        return openedInventories.values();
    }
}
