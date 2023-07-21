package net.evara.prison.customitem.items;

import me.lucko.helper.utils.Players;
import net.evara.prison.PrisonCore;
import net.evara.prison.customitem.CustomItem;
import net.evara.prison.mines.robot.RobotContainerTier;
import net.evara.prison.mines.robot.RobotType;
import net.evara.prison.utils.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.List;

public class RobotContainerItem extends CustomItem {

    private RobotContainerTier containerTier;
    private int amount;

    public RobotContainerItem() {
        super("robot_container_item");
    }

    public RobotContainerItem(RobotContainerTier containerTier, int  amount) {
        super("robot_container_item");
        setPlugin(PrisonCore.getInstance());
        this.containerTier = containerTier;
        this.amount = amount;
    }

    @Override
    public void onUse(PlayerInteractEvent e) {

        Player player = e.getPlayer();
        ItemStack itemInHand = player.getInventory().getItemInMainHand();
        if(itemInHand.getType() == Material.AIR) return;

        RobotContainerTier tier = RobotContainerTier.valueOf(getPersistentData(itemInHand, "robot_container_tier", PersistentDataType.STRING));
        RobotType type = tier.getRandom();

        itemInHand.setAmount(itemInHand.getAmount() - 1);

        ItemStack robotItem = new RobotItem(type).build();
        player.getInventory().addItem(robotItem);

        Players.msg(player, "&5&lROBOT &7• You have unlocked a &bRobot Container &7and received a " + type.getDisplayName() + "&7.");
    }

    @Override
    public ItemStack make() {

        List<String> lore = new ArrayList<>();
        lore.add("&7Right Click while holding to reveal the robot inside!");
        lore.add("");
        lore.add("&5 Robot Types");
        this.containerTier.getTiers().forEach(tier -> lore.add("&d┃ " + tier.getDisplayName()));

        return new ItemBuilder().of(Material.HOPPER)
                .name(this.containerTier.getDisplayName())
                .amount(amount)
                .lore(
                       lore
                )
                .glow(true)
                .hideAttributes(true)
                .addPersistentData("robot_container_tier", PersistentDataType.STRING, this.containerTier.name())
                .build();
    }


}
