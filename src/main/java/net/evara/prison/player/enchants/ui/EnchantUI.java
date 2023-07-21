package net.evara.prison.player.enchants.ui;

import me.lucko.helper.item.ItemStackBuilder;
import me.lucko.helper.menu.Gui;
import me.lucko.helper.menu.scheme.MenuPopulator;
import me.lucko.helper.menu.scheme.MenuScheme;
import net.evara.prison.player.EvaraPlayer;
import net.evara.prison.player.enchants.base.Enchant;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class EnchantUI extends Gui {

    private EvaraPlayer evaraPlayer;

    public EnchantUI(Player player, EvaraPlayer evaraPlayer) {
        super(player, 5, "&8Enchants (" + player.getName() + ")");
        this.evaraPlayer = evaraPlayer;
    }

    private static final MenuScheme enchantScheme = new MenuScheme()
            .mask("000000000")
            .mask("000111110")
            .mask("000111110")
            .mask("000111110")
            .mask("000000000");

    @Override
    public void redraw() {
        MenuPopulator enchantPopulator = enchantScheme.newPopulator(this);

        enchantPopulator.getSlots()
                .forEach(slot -> enchantPopulator.accept(ItemStackBuilder.of(Material.BARRIER).name("&cEmpty Slot.").hideAttributes()
                        .buildItem().build()));

        evaraPlayer.getEnchantHolder().getAll()
                .forEach(enchant -> {


                    Enchant base = enchant.getEnchant();
                    List<String> lore = new ArrayList<>(base.getDescription());
                    lore.add("");
                    lore.add("&d Statistics");
                    lore.add("&fLevel: &d" + enchant.getLevel() + "/" + base.getMaxLevel() + " &7(" + enchant.getPrestige() + "/" + base.getMaxPrestige() + ")");
                    lore.add("&fChance: &d" + Math.floor(base.getCurrentChanceOfActivating(enchant.getLevel())) + "%");
                    base.additionalInformation(enchant).ifPresent(lore::add);
                    lore.add("");
                    lore.add("&d Click Here To Upgrade!");

                    boolean maxLevel = enchant.getLevel() >= base.getMaxLevel();
                    boolean maxPrestige = enchant.getPrestige() >= base.getMaxPrestige();

                    String name = maxLevel ? "&c" + base.getDisplayName() + " &7(Max Level)" : "&d" + base.getDisplayName();
                    name = maxPrestige ? name + " &7(Max Prestige)" : name;

                    setItem(
                            base.getSlot(),
                            ItemStackBuilder.of(base.getIcon())
                                    .name(name)
                                    .lore(lore)
                                    .build(() -> {
                                        new EnchantUpgradeUI(getPlayer(), enchant, evaraPlayer).open();
                                    })
                    );

                });


    }


}
