/*
 *  Created by Filip P. on 3/1/15 2:47 PM.
 */

/*
 *  Created by Filip P. on 2/2/15 11:12 PM.
 */

package me.pauzen.splegg.misc;

public class Tuple<A, B> {

    private A a;
    private B b;

    public Tuple(A a, B b) {
        this.a = a;
        this.b = b;
    }

    public A getFirst() {
        return a;
    }

    public B getSecond() {
        return b;
    }
}
