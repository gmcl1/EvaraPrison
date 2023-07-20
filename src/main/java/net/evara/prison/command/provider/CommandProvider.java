package net.evara.prison.command.provider;


import net.evara.prison.command.context.ICommandContext;

import java.util.List;

/**
 * Represents a command provider.
 * @param <T> The type of the provider.
 */
public abstract class CommandProvider<T> {

    public abstract T convert(String input, ICommandContext context);
    public abstract List<String> tabComplete();

}
