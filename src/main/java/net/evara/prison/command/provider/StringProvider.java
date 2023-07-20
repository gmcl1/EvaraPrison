package net.evara.prison.command.provider;


import net.evara.prison.command.context.ICommandContext;

import java.util.ArrayList;
import java.util.List;

public class StringProvider extends CommandProvider<String> {

    @Override
    public String convert(String input, ICommandContext context) {
        return input;
    }

    @Override
    public List<String> tabComplete() {
        return new ArrayList<>();
    }

}
