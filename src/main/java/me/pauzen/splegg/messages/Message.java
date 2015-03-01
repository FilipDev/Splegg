/*
 *  Created by Filip P. on 2/28/15 8:57 PM.
 */

/*
 *  Created by Filip P. on 2/2/15 11:24 PM.
 */

package me.pauzen.splegg.messages;

import me.pauzen.splegg.players.CorePlayer;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;

public interface Message {

    String getPrefix();

    String getRawMessage();

    default String getMessage() {
        return getPrefix() + getRawMessage();
    }

    default void sendMessage(CommandSender commandSender, String... strings) {
        if (!new MessageSendEvent(commandSender).call().isCancelled()) {
            commandSender.sendMessage(String.format(this.getMessage(), strings));
        }
    }

    default void sendMessage(CorePlayer corePlayer, String... strings) {
        sendMessage(corePlayer.getPlayer(), strings);
    }

    default void sendRawMessage(CommandSender commandSender, String... strings) {
        if (!new MessageSendEvent(commandSender).call().isCancelled()) {
            commandSender.sendMessage(String.format(this.getRawMessage(), strings));
        }
    }

    default void sendRawMessage(CorePlayer corePlayer, String... strings) {
        sendRawMessage(corePlayer.getPlayer(), strings);
    }

    default void sendConsole(String... strings) {
        sendMessage(Bukkit.getConsoleSender(), strings);
    }
}
