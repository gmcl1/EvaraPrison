package net.evara.prison.player.enchants.ui;

import me.lucko.helper.item.ItemStackBuilder;
import me.lucko.helper.menu.Gui;
import me.lucko.helper.menu.Item;
import me.lucko.helper.menu.scheme.MenuPopulator;
import me.lucko.helper.menu.scheme.MenuScheme;
import net.evara.prison.player.EvaraPlayer;
import net.evara.prison.player.currency.Currency;
import net.evara.prison.player.currency.CurrencyType;
import net.evara.prison.player.enchants.AppliedEnchant;
import net.evara.prison.player.enchants.base.Enchant;
import net.evara.prison.utils.Number;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.List;

public class EnchantUpgradeUI extends Gui {

    private EvaraPlayer evaraPlayer;
    private AppliedEnchant enchant;

    public EnchantUpgradeUI(Player player, AppliedEnchant enchant, EvaraPlayer evaraPlayer) {
        super(player, 3, "&8Upgrade " + enchant.getEnchant().getDisplayName() + " &8(" + player.getName() + ")");
        this.evaraPlayer = evaraPlayer;
        this.enchant = enchant;
    }

    private static MenuScheme upgradeSlots = new MenuScheme()
            .mask("000000000")
            .mask("011111110")
            .mask("000000000");
    private static MenuScheme outline = new MenuScheme()
            .mask("111111111")
            .mask("100000001")
            .mask("011111111");

    private static int[] upgradeAmounts = new int[]{
            1, 10, 25, 100, 250, 500, -1
    };

    @Override
    public void redraw() {


        MenuPopulator outlinePop = outline.newPopulator(this);
        outlinePop.getSlots().forEach(slot -> outlinePop.accept(ItemStackBuilder.of(Material.BLACK_STAINED_GLASS_PANE).name(" ").hideAttributes().buildItem().build()));
        setItem(18, ItemStackBuilder.of(Material.ARROW).name("&cBack").hideAttributes().build(() -> new EnchantUI(getPlayer(), evaraPlayer).open()));

        MenuPopulator upgradePop = upgradeSlots.newPopulator(this);
        CurrencyType type = CurrencyType.TOKENS;
        Currency tokenCurrency = evaraPlayer.getCurrencyHolder().getCurrency(type);
        Enchant base = enchant.getEnchant();

        long enchantLevel = enchant.getLevel();
        long maxLevel = base.getMaxLevel();
        boolean isMaxLevel = enchant.getLevel() >= enchant.getEnchant().getMaxLevel();
        boolean isMaxPrestige = enchant.getPrestige() >= base.getMaxPrestige();

        int itemIndex = 1;

        for (int upgradeAmount : upgradeAmounts) {

            boolean isMaxSlot = upgradeAmount == -1;
            int itemAmount = isMaxLevel ? 1 : isMaxSlot ? 1 : itemIndex;

            if (isMaxSlot) upgradeAmount = base.getLevelsPlayerCanAfford(enchant.getLevel(), tokenCurrency.getAmount());
            if (enchantLevel + upgradeAmount >= maxLevel) upgradeAmount = (int) (maxLevel - enchantLevel);

            double cost = base.getCost(enchant.getLevel(), upgradeAmount);
            boolean canAfford = tokenCurrency.has(cost);
            Material material = isMaxLevel ? Material.BARRIER : isMaxSlot ? Material.MAGMA_CREAM : Material.SWEET_BERRIES;
            String name =
                    !canAfford
                            ? "&cCannot Afford"
                            : isMaxLevel || upgradeAmount == 0
                            ? "&cEnchantment Maxed"
                            : isMaxSlot
                            ? "&dMax Upgrade &8(+" + upgradeAmount + ")"
                            : "&dUpgrade &8(+" + upgradeAmount + ")";


            List<String> lore;

            if(canAfford) {
                lore = List.of(
                        "&d┃ &fLevel: &d" + enchantLevel + "/" + maxLevel,
                        "&d┃ &fCost: &5" + type.getSymbol() + " " + Number.format(cost) + " " + type.getName()
                );
            } else {
                lore = List.of(
                        "&d┃ &fLevel: &d" + enchantLevel + "/" + maxLevel,
                        "&d┃ &fCost: &5" + type.getSymbol() + " " + Number.format(cost) + " " + type.getName(),
                        "",
                        "&cYou cannot afford this upgrade."
                );
            }

            if(isMaxLevel) {
                lore = List.of(
                        "&cYou've reached the maximum level for this enchantment.",
                        (isMaxPrestige) ? "&cYou've reached the maximum prestige and level for this enchantment." : "&cYou can now prestige this enchantment for a higher activation chance."
                );
            }


            int finalUpgradeAmount = upgradeAmount;
            Item upgradeSlot = ItemStackBuilder.of(material)
                    .amount(itemAmount)
                    .name(name)
                    .lore(lore)
                    .build(() -> {
                        if (isMaxLevel) return;
                        enchant.upgrade(evaraPlayer, finalUpgradeAmount);
                        redraw();
                    });

            upgradePop.accept(upgradeSlot);

            itemIndex++;
        }


    }


}
