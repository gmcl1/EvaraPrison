package net.evara.prison.mines.ui;

import me.lucko.helper.item.ItemStackBuilder;
import me.lucko.helper.menu.Gui;
import net.evara.prison.mines.MineManager;
import net.evara.prison.mines.PrivateMine;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;

import java.util.List;

public class PrivateMineUI extends Gui {

    private final MineManager manager;
    private PrivateMine mine;

    public PrivateMineUI(Player player, PrivateMine mine, MineManager manager) {
        super(player, 6, "&5&lPrivate Mine &d(&5" + mine.getOwnerName() + "&d)");
        this.manager = manager;
        this.mine = mine;
    }

    int[] minecraftInventorySlotsVisualised = new int[]{
            0, 1, 2, 3, 4, 5, 6, 7, 8,
            9, 10, 11, 12, 13, 14, 15, 16, 17,
            18, 19, 20, 21, 22, 23, 24, 25, 26,
            27, 28, 29, 30, 31, 32, 33, 34, 35,
            36, 37, 38, 39, 40, 41, 42, 43, 44,
            45, 46, 47, 48, 49, 50, 51, 52, 53
    };


    @Override
    public void redraw() {

        boolean accessToMineIcon = getPlayer().hasPermission("mines.icon.access");
        ItemStackBuilder iconChanger = accessToMineIcon
                ? ItemStackBuilder.of(Material.IRON_BARS).name("&5&lMine Icon &7(&d&lUNLOCKED&7)")
                : ItemStackBuilder.of(Material.IRON_BARS).name("&5&lMine Icon &7(&c&lLOCKED&7)");


        setItem(
                22,
                ItemStackBuilder.of(Material.DIAMOND_PICKAXE)
                        .name("&5&lMine Information")
                        .lore(
                                List.of(
                                        "&dTo Be Implemented."
                                )
                        )
                        .enchant(Enchantment.DIG_SPEED)
                        .hideAttributes()
                        .build(() -> {

                        })
        );

        setItem(
                29,
                iconChanger
                        .lore(
                                "&dClick here to change your Mine Icon,",
                                "&dthis is what will be displayed in the public mines",
                                "&dmenu."
                        )
                        .enchant(Enchantment.DIG_SPEED)
                        .hideAttributes()
                        .build(() -> {
                            new PrivateMineIconSelectorUI(getPlayer(), mine, manager).open();
                        })
        );

        setItem(
                33,
                ItemStackBuilder.of(Material.IRON_DOOR)
                        .name("&5&lTeleport")
                        .lore(
                                "&dClick here to teleport to your mine."
                        )
                        .enchant(Enchantment.DIG_SPEED)
                        .hideAttributes()
                        .build(() -> {
                            getPlayer().teleport(mine.getArea().getSpawnPos().toLocation());
                        })
        );

        setItem(
                40,
                ItemStackBuilder.of(Material.REDSTONE)
                        .name("&5&lReset Mine")
                        .lore(
                                "&dClick here to reset your mine."
                        )
                        .enchant(Enchantment.DIG_SPEED)
                        .hideAttributes()
                        .build(() -> {
                            mine.getArea().reset();
                        })
        );



    }

}
