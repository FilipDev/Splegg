/*
 *  Created by Filip P. on 3/1/15 2:42 PM.
 */

/*
 *  Created by Filip P. on 2/20/15 10:42 PM.
 */

package me.pauzen.splegg.misc;

import org.bukkit.ChatColor;

import java.util.concurrent.ThreadLocalRandom;

public class InvisibleID {

    private static String HEADER = ChatColor.UNDERLINE + "" + ChatColor.STRIKETHROUGH + "" + ChatColor.BOLD;

    private String id;

    private InvisibleID() {

        StringBuilder idBuilder = new StringBuilder(HEADER);

        ThreadLocalRandom random = ThreadLocalRandom.current();

        for (int i = 0; i < 4; i++) {
            ChatColor generatedColor = ChatColor.getByChar(Integer.toHexString(random.nextInt(16)));

            idBuilder.append(generatedColor.toString());
        }

        this.id = idBuilder.toString();

    }

    public static boolean hasInvisibleID(String string) {
        return string.contains(HEADER);
    }

    public static String getIDFrom(String string) {
        return string.substring(string.indexOf(HEADER));
    }

    public String getId() {
        return id;
    }

    public static InvisibleID generate() {
        return new InvisibleID();
    }

}
