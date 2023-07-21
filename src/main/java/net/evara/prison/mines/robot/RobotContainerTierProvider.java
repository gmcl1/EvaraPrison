package net.evara.prison.mines.robot;

import net.evara.prison.command.context.ICommandContext;
import net.evara.prison.command.provider.CommandProvider;

import java.util.Arrays;
import java.util.List;

public class RobotContainerTierProvider extends CommandProvider<RobotContainerTier> {
    @Override
    public RobotContainerTier convert(String input, ICommandContext context) {
        return RobotContainerTier.valueOf(input.toUpperCase());
    }

    @Override
    public List<String> tabComplete() {
        return Arrays.stream(RobotContainerTier.values())
                .map(Enum::name)
                .toList();
    }
}
