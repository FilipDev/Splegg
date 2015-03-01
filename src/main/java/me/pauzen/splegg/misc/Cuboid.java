/*
 *  Created by Filip P. on 2/27/15 5:40 PM.
 */

package me.pauzen.splegg.misc;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.util.Vector;

public class Cuboid {

    private Vector point1, point2;
    private World world;

    public Cuboid(Vector point1, Vector point2, World world) {
        this.point1 = point1;
        this.point2 = point2;
        this.world = world;
    }
    
    public boolean isWithin(Location location) {
        return world == location.getWorld() && location.toVector().isInAABB(point1, point2);
    }

    public Vector getPoint1() {
        return point1;
    }

    public Vector getPoint2() {
        return point2;
    }

    public World getWorld() {
        return world;
    }
}
