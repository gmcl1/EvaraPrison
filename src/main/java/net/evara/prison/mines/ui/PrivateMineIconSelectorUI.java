package net.evara.prison.mines.ui;

import me.lucko.helper.item.ItemStackBuilder;
import me.lucko.helper.menu.Gui;
import me.lucko.helper.menu.scheme.MenuPopulator;
import me.lucko.helper.menu.scheme.MenuScheme;
import me.lucko.helper.utils.Players;
import net.evara.prison.mines.MineManager;
import net.evara.prison.mines.PrivateMine;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;

public class PrivateMineIconSelectorUI extends Gui {

    private final MineManager manager;
    private PrivateMine mine;

    public PrivateMineIconSelectorUI(Player player, PrivateMine mine, MineManager manager) {
        super(player, 3, "&dSelect An Icon");
        this.manager = manager;
        this.mine = mine;
    }

    private static final Material[] icons = new Material[]{
            Material.IRON_BARS, Material.REDSTONE_TORCH, Material.BELL,
            Material.NETHERITE_PICKAXE, Material.NETHERITE_SWORD, Material.NETHERITE_AXE,
            Material.BEACON
    };

    private static final MenuScheme materialSlots = new MenuScheme()
            .mask("000000000")
            .mask("011111110")
            .mask("000000000");

    @Override
    public void redraw() {
        MenuPopulator populator = materialSlots.newPopulator(this);
        for (Material material : icons) {
            populator.accept(
                    ItemStackBuilder.of(material)
                            .name("&5&lChoose Icon")
                            .lore(
                                   "&dClick here to select this Icon."
                            )
                            .enchant(Enchantment.DIG_SPEED)
                            .hideAttributes()
                            .build(() -> {
                                mine.getInfo().setMineListMaterial(material);
                                Players.msg(getPlayer(), "&dYou have set your mine icon to &5&l" + formatMaterialName(material) + "&d.");
                            })
            );
        }
    }

    private String formatMaterialName(Material material) {
        return material.name().toLowerCase().replace("_", " ");
    }


}
