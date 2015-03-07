/*
 *  Created by Filip P. on 3/2/15 6:02 PM.
 */

package me.pauzen.splegg.misc.math;

public class Line {
    
    private double a, b;

    public double getA() {
        return a;
    }

    public double getB() {
        return b;
    }

    public Line(double a, double b) {
        this.a = a;
        this.b = b;
    }
    
    public boolean contains(double value) {
        return value >= a && value <= b;
    }
}
