package net.evara.prison.mines;

import net.evara.prison.PrisonCore;
import net.evara.prison.command.PrisonCommand;
import net.evara.prison.command.context.ICommandContext;
import net.evara.prison.mines.ui.PrivateMineListUI;
import net.evara.prison.mines.ui.PrivateMineUI;
import org.bukkit.entity.Player;

public class MineCommand extends PrisonCommand {

    private final MineManager manager;

    public MineCommand(PrisonCore plugin) {
        super(plugin, "mine");
        this.manager = plugin.getManager();
        addChild(new MineListCommand(plugin));
    }

    @Override
    public void execute(ICommandContext context) {
        Player player = (Player) context.getSender();
        PrivateMine mine = manager.getMine(player);
        new PrivateMineUI(player, mine, manager).open();
    }

    private static class MineListCommand extends PrisonCommand {

        private final MineManager manager;

        public MineListCommand(PrisonCore plugin) {
            super(plugin, "list");
            this.manager = plugin.getManager();
        }

        @Override
        public void execute(ICommandContext context) {
            Player player = (Player) context.getSender();
            new PrivateMineListUI(player, manager).open();
        }
    }


}
