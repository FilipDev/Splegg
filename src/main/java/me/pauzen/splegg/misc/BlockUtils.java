/*
 *  Created by Filip P. on 3/2/15 5:09 PM.
 */

package me.pauzen.splegg.misc;

import me.pauzen.splegg.misc.math.Line;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;

import java.util.ArrayList;
import java.util.List;

public final class BlockUtils {

    private BlockUtils() {}
    
    public static List<Block> getSupportingBlocks(Entity entity, double width) {
        
        Location location = entity.getLocation();
        location.subtract(0, 1, 0);
        
        List<Block> supportingBlocks = new ArrayList<>();
        Block under = location.getBlock();
        supportingBlocks.add(under);
        Line xLine = new Line(location.getX() - width / 2, location.getX() + width / 2);
        Line zLine = new Line(location.getZ() - width / 2, location.getZ() + width / 2);

        if (xLine.contains(Math.floor(location.getX()) - 0.0001)) {
            supportingBlocks.add(location.subtract(1, 0, 0).getBlock());
        }

        if (xLine.contains(Math.ceil(location.getX()) + 0.0001)) {
            supportingBlocks.add(location.subtract(-1, 0, 0).getBlock());
        }
        
        if (zLine.contains(Math.floor(location.getZ()) - 0.0001)) {
            supportingBlocks.add(location.subtract(0, 0, 1).getBlock());
        }

        if (zLine.contains(Math.ceil(location.getZ()) + 0.0001)) {
            supportingBlocks.add(location.subtract(0, 0, -1).getBlock());
        }
        
        return supportingBlocks;
    }
}
