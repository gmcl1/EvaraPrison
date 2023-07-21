package net.evara.prison.customitem.items;

import lombok.Getter;
import net.evara.prison.PrisonCore;
import net.evara.prison.customitem.CustomItem;
import net.evara.prison.player.EvaraPlayer;
import net.evara.prison.player.PlayerManager;
import net.evara.prison.player.currency.CurrencyType;
import net.evara.prison.utils.ItemBuilder;
import net.evara.prison.utils.Number;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;

import java.util.List;

@Getter
public class CurrencyNote extends CustomItem {

    private Player player;
    private CurrencyType type;
    public int amount;
    private double currencyAmount;

    public CurrencyNote() {
        super("currency_note");
    }

    public CurrencyNote(Player player, CurrencyType type, int amount, double currencyAmount) {
        super("currency_note");
        setPlugin(PrisonCore.getInstance());
        this.type = type;
        this.amount = amount;
        this.currencyAmount = currencyAmount;
        this.player = player;
    }

    @Override
    public void onUse(PlayerInteractEvent e) {
        Player player = e.getPlayer();
        ItemStack itemInHand = player.getInventory().getItemInMainHand();
        if (itemInHand.getType() == Material.AIR) return;

        PlayerManager playerManager = getPlugin().getPlayerManager();
        EvaraPlayer evaraPlayer = playerManager.getPlayer(player.getUniqueId());

        double currencyAmount = getPersistentData(itemInHand, "currency_amount", PersistentDataType.DOUBLE);
        CurrencyType currencyType = CurrencyType.valueOf(getPersistentData(itemInHand, "currency_type", PersistentDataType.STRING));
        evaraPlayer.getCurrencyHolder().add(currencyType, currencyAmount);

        itemInHand.setAmount(itemInHand.getAmount() - 1);
    }

    @Override
    public ItemStack make() {
        return new ItemBuilder().of(Material.PURPLE_DYE)
                .name("&d" + type.getName() + " Note")
                .amount(amount)
                .lore(
                        List.of(
                                "&dThis is a Currency Note that can be redeemed",
                                "&dto receive the specific currency.",
                                "",
                                "&d┃ &fWorth: &d" + Number.format(currencyAmount),
                                "&d┃ &fWithdrew By: &d" + player.getName()
                        )
                )
                .addPersistentData("currency_type", PersistentDataType.STRING, type.name())
                .addPersistentData("currency_amount", PersistentDataType.DOUBLE, currencyAmount)
                .hideAttributes(true)
                .glow(true)
                .build();
    }
}
