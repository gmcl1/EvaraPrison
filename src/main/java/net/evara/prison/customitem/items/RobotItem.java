package net.evara.prison.customitem.items;

import lombok.Getter;
import net.evara.prison.PrisonCore;
import net.evara.prison.customitem.CustomItem;
import net.evara.prison.mines.robot.RobotType;
import net.evara.prison.utils.ItemBuilder;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
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

    }

    @Override
    public ItemStack make() {

        List<String> lore = new ArrayList<>();
        lore.add("");
        lore.add("&7Robots are used to generate &dBeacons &7for");
        lore.add("&7your &dMine&7. &7These can then be used to");
        lore.add("&7boost your &dMine Upgrades&7.");
        lore.add("");
        lore.add("&5 Robot Information");
        lore.add("&d┃ &fTier: " + tier.getDisplayName());
        lore.add("&d┃ &fSpeed: &d10 seconds");
        lore.add("&d┃ &fBeacons: &d+" + tier.getProductionAmount());
        lore.add("");
        lore.add("&7Right Click while holding to attach to your &dMine&7.");

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
