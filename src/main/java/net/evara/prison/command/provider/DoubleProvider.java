package net.evara.prison.command.provider;


import net.evara.prison.command.context.ICommandContext;

import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of {@link CommandProvider} for {@link Integer}.
 * <p>
 * Converts a {@link String} to an {@link Integer}.
 *
 * @see CommandProvider
 * @see Double
 */
public class DoubleProvider extends CommandProvider<Double> {

    @Override
    public Double convert(String input, ICommandContext context) {
        return Double.parseDouble(input);
    }

    @Override
    public List<String> tabComplete() {
        return new ArrayList<>(
                List.of(
                        "1",
                        "2",
                        "3",
                        "4",
                        "5",
                        "6",
                        "7",
                        "8"
                )
        );
    }
}
