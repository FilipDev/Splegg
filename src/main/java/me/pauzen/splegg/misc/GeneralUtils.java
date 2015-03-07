/*
 *  Created by Filip P. on 3/4/15 8:49 PM.
 */

/*
 *  Created by Filip P. on 2/7/15 1:02 PM.
 */

package me.pauzen.splegg.misc;

import org.bukkit.Bukkit;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.io.File;
import java.util.Collection;

public class GeneralUtils {

    public static String toFileName(String... path) {
        StringBuilder pathBuilder = new StringBuilder();

        for (String location : path) {
            pathBuilder.append(File.separatorChar);
            pathBuilder.append(location);
        }

        return pathBuilder.toString();
    }

    public static <T> boolean toggleContainment(Collection<T> collection, T object) {
        if (collection.contains(object)) {
            collection.remove(object);
            return false;
        }
        else {
            collection.add(object);
            return true;
        }
    }

    public static <T> boolean setContainment(Collection<T> collection, T object, boolean containment) {
        if (containment) {
            if (!collection.contains(object)) {
                collection.add(object);
            }
        }
        else {
            collection.remove(object);
        }
        return containment;
    }

    public static int firstEmpty(Object[] someArray) {
        for (int i = someArray.length - 1; i >= 0; i--) {
            if (someArray[i] != null) {
                return i + 1;
            }
        }

        return -1;
    }

    public static ItemMeta getMeta(ItemStack itemStack) {

        if (!itemStack.hasItemMeta()) {
            itemStack.setItemMeta(Bukkit.getItemFactory().getItemMeta(itemStack.getType()));
        }

        return itemStack.getItemMeta();
    }

}
