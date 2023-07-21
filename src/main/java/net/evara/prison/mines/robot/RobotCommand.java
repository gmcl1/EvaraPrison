package net.evara.prison.mines.robot;

import me.lucko.helper.utils.Players;
import net.evara.prison.PrisonCore;
import net.evara.prison.command.PrisonCommand;
import net.evara.prison.command.context.ICommandContext;
import net.evara.prison.command.parameter.CommandParameter;
import net.evara.prison.command.provider.IntegerProvider;
import net.evara.prison.command.provider.PlayerProvider;
import net.evara.prison.customitem.items.RobotContainerItem;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class RobotCommand extends PrisonCommand {

    public RobotCommand(PrisonCore plugin) {
        super(plugin, "robot");
        addChild(new RobotContainerCommand(plugin));
        addChild(new RobotCombineCommand(plugin));
    }

    @Override
    public void execute(ICommandContext context) {

    }

    public static class RobotCombineCommand extends PrisonCommand {

            public RobotCombineCommand(PrisonCore plugin) {
                super(plugin, "combine");
            }

            @Override
            public void execute(ICommandContext context) {
               Player player = (Player) context.getSender();
               new RobotCombineUI(player).open();
            }
    }

    private static class RobotContainerCommand extends PrisonCommand {

        public RobotContainerCommand(PrisonCore plugin) {
            super(plugin, "container");
            addParameters(
                    CommandParameter.builder()
                            .name("tier")
                            .provider(
                                    new RobotContainerTierProvider()
                            )
                            .index(0)
                            .build(),
                    CommandParameter
                            .builder()
                            .name("amount")
                            .provider(
                                    new IntegerProvider()
                            )
                            .index(1)
                            .build(),
                    CommandParameter.builder()
                            .name("player")
                            .provider(
                                    new PlayerProvider()
                            )
                            .index(2)
                            .build()
            );
        }

        @Override
        public void execute(ICommandContext context) {
            RobotContainerTier tier = parse(context, 0);
            int amount = parse(context, 1);
            Player player = parse(context, 2);

            ItemStack container = new RobotContainerItem(tier, amount).build();
            player.getInventory().addItem(container);

            Players.msg(player, "&5&lROBOT &7• You have received a " + tier.getDisplayName() + "&7.");

        }

    }

}
