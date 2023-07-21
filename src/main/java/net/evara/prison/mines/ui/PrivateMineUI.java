package net.evara.prison.mines.ui;

import me.lucko.helper.item.ItemStackBuilder;
import me.lucko.helper.menu.Gui;
import net.evara.prison.mines.MineManager;
import net.evara.prison.mines.PrivateMine;
import net.evara.prison.mines.PrivateMineMember;
import net.evara.prison.player.EvaraPlayer;
import net.evara.prison.player.PlayerManager;
import net.evara.prison.utils.SkullCreator;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;

public class PrivateMineUI extends Gui {

    private final MineManager manager;
    private final PlayerManager playerManager;
    private PrivateMine mine;
    private EvaraPlayer evaraPlayer;

    public PrivateMineUI(Player player, PlayerManager playerManager, PrivateMine mine, MineManager manager) {
        super(player, 3, "&8Mine Panel (" + player.getName() + ")");
        this.manager = manager;
        this.mine = mine;
        this.playerManager = playerManager;
        this.evaraPlayer = playerManager.getPlayer(player.getUniqueId());
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

        PrivateMineMember member = mine.getMembers().getMembers().get(getPlayer().getUniqueId());

        setItem(
                10,
                ItemStackBuilder.of(SkullCreator.itemFromUuid(getPlayer().getUniqueId()))
                        .name("&5&l" + getPlayer().getName())
                        .lore(
                                "",
                                "&5Player Information",
                                "&5┃ &fBlocks Mined: &d" + member.getBlocksMined(),
                                "&5┃ &fPlay Time: &7",
                                "",
                                "&5Mine Information",
                                "&5┃ &fMine Owner: &d" + mine.getOwnerName(),
                                "&5┃ &fMine Members: &7" + mine.getMembers().getAllMembers().size() + "/" + mine.getMembers().getMaxMembers(),
                                "&5┃ &fMine Level: &dL" + mine.getInfo().getLevel(),
                                "&5┃ &fMine Prestige: &d&lP"
                        )
                        .enchant(Enchantment.DIG_SPEED)
                        .hideAttributes()
                        .build(() -> {

                        })
        );

        setItem(
                12,
                ItemStackBuilder.of(SkullCreator.itemFromBase64(
                                "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZGFmOTcxMWQyMzFlM2FiZmI2ZmI3NDQ3MTFmMGQ4NmU2MDQxZWUwNzdkYWUxNmRkYzcwM2E3YjRlYTE2NWQ1OCJ9fX0="
                        ))
                        .name("&5&lTreasury")
                        .lore(
                                "&5┃ &fBeacons: &b☆",
                                "",
                                "&7&o * /mineshop to use these *"
                        )
                        .enchant(Enchantment.DIG_SPEED)
                        .hideAttributes()
                        .build(() -> {

                        })
        );

        setItem(
                13,
                ItemStackBuilder.of(Material.DARK_OAK_DOOR)
                        .name("&5&lTeleport to Mine")
                        .lore(
                                "&5┃ &fClick on this &dItem &fto teleport to",
                                " &fyour mine."
                        )
                        .enchant(Enchantment.DIG_SPEED)
                        .hideAttributes()
                        .build(() -> {
                            getPlayer().teleport(mine.getArea().getSpawnPos().toLocation());
                            evaraPlayer.msg("&5&lMINE &7• &7You have been &dteleported &7to your mine.");
                        })
        );

        setItem(
                14,
                ItemStackBuilder.of(Material.SWEET_BERRIES)
                        .name("&5&lReset Mine")
                        .lore(
                                "&5┃ &fClick on this &dItem &fto reset your",
                                " &fmine.",
                                "",
                                "&7&o* 2m cooldown *"
                        )
                        .enchant(Enchantment.DIG_SPEED)
                        .hideAttributes()
                        .build(() -> {
                            mine.getArea().reset();
                            evaraPlayer.msg("&5&lMINE &7• &7Your mine has been &dreset&7.");
                        })
        );

        setItem(
                15,
                ItemStackBuilder.of(SkullCreator.itemFromBase64(
                                        "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvY2I5NDJiOWYzODY4MWJmOTZjODRkMTgxYmFkOWVmMGFjYzA0NzYzNWQyNTRlMzQ4OWZjODExODg2NzgxY2E1ZCJ9fX0="
                                )
                        )
                        .name("&5&lMine Members")
                        .lore(
                                "&5┃ &fClick on this &dItem &fto view",
                                " &fyour mine members and their contributions."
                        )
                        .enchant(Enchantment.DIG_SPEED)
                        .hideAttributes()
                        .build(() -> {
                            new PrivateMineMemberUI(getPlayer(), mine).open();
                        })
        );

        setItem(
                16,
                ItemStackBuilder.of(Material.MAGMA_CREAM)
                        .name("&5&lMine Library")
                        .lore(
                                "&5┃ &fClick on this &dItem &fto view",
                                " &fthe Mines that are public."
                        )
                        .enchant(Enchantment.DIG_SPEED)
                        .hideAttributes()
                        .build(() -> {
                            new PrivateMineListUI(getPlayer(), playerManager, manager).open();
                        })
        );


    }

}
