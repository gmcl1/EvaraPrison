package net.evara.prison.mines.ui;

import me.lucko.helper.item.ItemStackBuilder;
import me.lucko.helper.menu.Gui;
import net.evara.prison.mines.PrivateMine;
import net.evara.prison.mines.PrivateMineMember;
import net.evara.prison.mines.robot.AttachedRobotUI;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;

public class PrivateMineMemberUI extends Gui {

    private PrivateMine privateMine;

    public PrivateMineMemberUI(Player player, PrivateMine privateMine) {
        super(player, 3, "&dPrivate Mine &8(" + privateMine.getOwnerName() + ")");
        this.privateMine = privateMine;
    }

    @Override
    public void redraw() {
        int i = 0;
        for (PrivateMineMember member : privateMine.getMembers().getAllMembers()) {
            setItem(i,
                    ItemStackBuilder.of(Material.PLAYER_HEAD)
                            .name(member.getPlayer().getName())
                            .lore(
                                    "&7Click to view &d" + member.getPlayer().getName() + "'s &7Robots"
                            )
                            .enchant(Enchantment.DIG_SPEED)
                            .hideAttributes()
                            .build(() -> {
                                new AttachedRobotUI(getPlayer(), member).open();
                            })
            );
            i++;
        }
    }


}
