package net.evara.prison.mines.robot;

import me.lucko.helper.item.ItemStackBuilder;
import me.lucko.helper.menu.Gui;
import me.lucko.helper.menu.Item;
import me.lucko.helper.menu.scheme.MenuPopulator;
import me.lucko.helper.menu.scheme.MenuScheme;
import me.lucko.helper.utils.Players;
import net.evara.prison.customitem.items.RobotItem;
import net.evara.prison.mines.PrivateMineMember;
import net.evara.prison.utils.Text;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Date;
import java.util.List;

public class AttachedRobotUI extends Gui {

    private final PrivateMineMember privateMineMember;

    private static final MenuScheme robotScheme = new MenuScheme()
            .mask("000000000")
            .mask("011111110")
            .mask("011111110")
            .mask("011111110")
            .mask("011111110")
            .mask("000000000");

    public AttachedRobotUI(Player player, PrivateMineMember privateMineMember) {
        super(player, 6, "&dAttached Robots &8(" + privateMineMember.getPlayer().getName() + ")");
        this.privateMineMember = privateMineMember;
    }

    @Override
    public void redraw() {
        fillWith(ItemStackBuilder.of(Material.PURPLE_STAINED_GLASS_PANE).name(" ").buildItem().build());
        MenuPopulator robotPopulator = robotScheme.newPopulator(this);
        privateMineMember.getRobots().values()
                .forEach(robot -> {

                    RobotItem robotItem = new RobotItem(robot.getType());
                    ItemStack itemStack = robotItem.build();
                    ItemMeta itemMeta = itemStack.getItemMeta();
                    List<String> lore = robot.getType().getBaseLore();
                    lore.add(Text.colorize("&5 Statistics"));
                    lore.add(Text.colorize("&d| &fBeacons Generated: &d" + robot.getBeaconsGenerated()));
                    lore.add(Text.colorize("&d| &fDate Created: &d" + new Date(robot.getAttachedAt())));
                    itemMeta.setLore(lore);
                    itemStack.setItemMeta(itemMeta);

                    Item item = ItemStackBuilder.of(itemStack)
                            .build(() -> {
                                Player player = getPlayer();
                                if (!player.getUniqueId().equals(robot.getAttachedBy())) {
                                    Players.msg(player, "&5&lROBOT &7• &7You cannot remove &dRobots&7 that are not yours!");
                                    return;
                                }
                                privateMineMember.removeRobot(robot);
                                Players.msg(player, "&5&lROBOT &7• &7You have removed a &dRobot&7 from your mine!");
                                redraw();
                            });

                    robotPopulator.accept(item);

                });

        robotPopulator.getSlots().forEach(slot -> {
            if (this.getSlot(slot).getItem().getType() == Material.PURPLE_STAINED_GLASS_PANE) {
                this.setItem(slot, ItemStackBuilder.of(Material.GRAY_STAINED_GLASS_PANE).name(" ").buildItem().build());
            }
        });

    }


}
