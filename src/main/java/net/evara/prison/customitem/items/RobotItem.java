package net.evara.prison.customitem.items;

import lombok.Getter;
import me.lucko.helper.utils.Players;
import net.evara.prison.PrisonCore;
import net.evara.prison.customitem.CustomItem;
import net.evara.prison.mines.PrivateMine;
import net.evara.prison.mines.PrivateMineMember;
import net.evara.prison.mines.robot.MineRobot;
import net.evara.prison.mines.robot.RobotCombineUI;
import net.evara.prison.mines.robot.RobotType;
import net.evara.prison.utils.ItemBuilder;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;

import java.util.List;

@Getter
public class RobotItem extends CustomItem {

    private RobotType tier;

    public RobotItem() {
        super("robot_item");
    }

    public RobotItem(RobotType tier) {
        super("robot_item");
        setPlugin(PrisonCore.getInstance());
        this.tier = tier;
    }

    @Override
    public void onUse(PlayerInteractEvent e) {
        Player player = e.getPlayer();
        ItemStack itemInHand = player.getInventory().getItemInMainHand();
        Action action = e.getAction();
        PrivateMine mine = getPlugin().getManager().getMine(player);

        if (itemInHand.getType().isAir()) return;

        if ((action == Action.RIGHT_CLICK_AIR || action == Action.RIGHT_CLICK_BLOCK) && player.isSneaking()) {
            new RobotCombineUI(player).open();
            e.setCancelled(true);
            return;
        }

        RobotType type = RobotType.valueOf(getPersistentData(itemInHand, "robot_tier", PersistentDataType.STRING));
        PrivateMineMember member = mine.getMembers().getMembers().get(player.getUniqueId());
        member.addRobot(new MineRobot(mine, type, player.getUniqueId()));
        Players.msg(player, "&5&lROBOT &7• &7You have added a &d" + type.getDisplayName() + " &7robot to your &dMine&7.");

        itemInHand.setAmount(itemInHand.getAmount() - 1);
        e.setCancelled(true);
    }

    @Override
    public ItemStack make() {

        List<String> lore = tier.getBaseLore();
        lore.add("&7Right Click while holding to attach to your &dMine&7.");
        lore.add("&7Shift + Right Click while holding to &dCombine &7robots.");

        return new ItemBuilder()
                .of(tier.getRobotMaterial())
                .name(this.tier.getDisplayName())
                .amount(1)
                .lore(
                        lore
                )
                .glow(true)
                .hideAttributes(true)
                .addPersistentData("robot_tier", PersistentDataType.STRING, this.tier.name())
                .build();
    }


}
