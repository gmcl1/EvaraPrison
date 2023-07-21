package net.evara.prison.mines;

import net.evara.prison.PrisonCore;
import net.evara.prison.command.PrisonCommand;
import net.evara.prison.command.context.ICommandContext;
import net.evara.prison.mines.ui.PrivateMineListUI;
import net.evara.prison.mines.ui.PrivateMineUI;
import net.evara.prison.player.PlayerManager;
import org.bukkit.entity.Player;

public class MineCommand extends PrisonCommand {

    private final MineManager manager;
    private final PlayerManager playerManager;

    public MineCommand(PrisonCore plugin) {
        super(plugin, "mine");
        this.manager = plugin.getManager();
        this.playerManager = plugin.getPlayerManager();
        addChild(new MineListCommand(plugin));
    }

    @Override
    public void execute(ICommandContext context) {
        Player player = (Player) context.getSender();
        PrivateMine mine = manager.getMine(player);
        new PrivateMineUI(player, playerManager, mine, manager).open();
    }

    private static class MineListCommand extends PrisonCommand {

        private final MineManager manager;
        private final PlayerManager playerManager;

        public MineListCommand(PrisonCore plugin) {
            super(plugin, "list");
            this.manager = plugin.getManager();
            this.playerManager = plugin.getPlayerManager();
        }

        @Override
        public void execute(ICommandContext context) {
            Player player = (Player) context.getSender();
            new PrivateMineListUI(player, playerManager, manager).open();
        }
    }


}
