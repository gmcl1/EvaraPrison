package net.evara.prison.player.currency.command;

import net.evara.prison.PrisonCore;
import net.evara.prison.command.PrisonCommand;
import net.evara.prison.command.context.ICommandContext;
import net.evara.prison.command.parameter.CommandParameter;
import net.evara.prison.command.provider.DoubleProvider;
import net.evara.prison.customitem.items.CurrencyNote;
import net.evara.prison.player.EvaraPlayer;
import net.evara.prison.player.PlayerManager;
import net.evara.prison.player.currency.CurrencyType;
import net.evara.prison.player.currency.provider.CurrencyProvider;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Optional;

public class WithdrawCommand extends PrisonCommand {

    public WithdrawCommand(PrisonCore plugin) {
        super(plugin, "withdraw");
        addParameters(
                CommandParameter.builder()
                        .name("currency")
                        .index(0)
                        .provider(
                                new CurrencyProvider()
                        )
                        .build(),
                CommandParameter.builder()
                        .name("amount")
                        .index(1)
                        .provider(
                                new DoubleProvider()
                        )
                        .build()
        );
    }

    @Override
    public void execute(ICommandContext context) {
        PlayerManager playerManager = getPlugin().getPlayerManager();
        EvaraPlayer player = playerManager.getPlayer(((Player) context.getSender()).getUniqueId());
        CurrencyType currencyType = parse(context, 0);
        double amount = parse(context, 1);

        Optional<CurrencyNote> optional = player.getCurrencyHolder().withdraw(currencyType, amount);
        if (optional.isEmpty()) {
            player.msg("&cYou do not have enough of this currency to withdraw that amount.");
            return;
        }

        ItemStack currencyItem = optional.get().build();
        player.getPlayer().getInventory().addItem(currencyItem);
    }


}
