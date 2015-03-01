/*
 *  Created by Filip P. on 2/28/15 8:57 PM.
 */

/*
 *  Created by Filip P. on 2/2/15 11:24 PM.
 */

package me.pauzen.splegg.messages;

import org.bukkit.ChatColor;

public enum ChatMessage implements Message {

    JOINED(ChatColor.GREEN + "Joined the game of Splegg! Arena: %s"),
    LEFT(ChatColor.RED + "Left the game of Splegg!"),
    LOST(ChatColor.RED + "You lost the game."),
    WON(ChatColor.GREEN + "You won the game."),
    ;

    private String message;

    private ChatMessage(String message) {
        this.message = message;
    }

    @Override
    public String getPrefix() {
        return ChatColor.GRAY + "Server: " + ChatColor.WHITE;
    }

    @Override
    public String getRawMessage() {
        return this.message;
    }
}