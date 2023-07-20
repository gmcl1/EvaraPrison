package net.evara.prison.player.enchants;

import net.evara.prison.PrisonCore;
import net.evara.prison.command.PrisonCommand;
import net.evara.prison.command.context.ICommandContext;
import net.evara.prison.player.EvaraPlayer;
import net.evara.prison.player.PlayerManager;
import net.evara.prison.player.enchants.ui.EnchantUI;
import org.bukkit.entity.Player;

public class EnchantCommand extends PrisonCommand {

    public EnchantCommand(PrisonCore plugin) {
        super(plugin, "enchants");
    }


    @Override
    public void execute(ICommandContext context) {
        Player player = (Player) context.getSender();
        PlayerManager playerManager = getPlugin().getPlayerManager();
        EvaraPlayer evaraPlayer = playerManager.getPlayer(player.getUniqueId());
        new EnchantUI(player, evaraPlayer).open();
    }


}
