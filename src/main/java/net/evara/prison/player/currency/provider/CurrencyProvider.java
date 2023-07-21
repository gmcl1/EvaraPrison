package net.evara.prison.player.currency.provider;

import net.evara.prison.command.context.ICommandContext;
import net.evara.prison.command.provider.CommandProvider;
import net.evara.prison.player.currency.CurrencyType;

import java.util.Arrays;
import java.util.List;

public class CurrencyProvider extends CommandProvider<CurrencyType> {
    @Override
    public CurrencyType convert(String input, ICommandContext context) {
        return CurrencyType.valueOf(input.toUpperCase());
    }

    @Override
    public List<String> tabComplete() {
        return Arrays.stream(CurrencyType.values()).map(Enum::name).toList();
    }
}
