package net.evara.prison.command.context;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bukkit.command.CommandSender;

@RequiredArgsConstructor
@Getter
public class ICommandContext {

    private final CommandSender sender;

    private final String[] args;
    public boolean hasPermission(String permission) {
        return sender.hasPermission(permission);
    }

    public void sendMessage(String message) {
        sender.sendMessage(message);
    }

    public void sendMessage(String... messages) {
        sender.sendMessage(messages);
    }

    public void sendMessage(String message, Object... objects) {
        sender.sendMessage(String.format(message, objects));
    }

    public void sendMessage(String[] messages, Object... objects) {
        for (String message : messages) {
            sender.sendMessage(String.format(message, objects));
        }
    }

}
