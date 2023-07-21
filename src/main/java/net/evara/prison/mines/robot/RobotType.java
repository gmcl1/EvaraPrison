package net.evara.prison.mines.robot;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import me.lucko.helper.random.Weighted;
import net.evara.prison.utils.Text;
import org.bukkit.Material;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RequiredArgsConstructor
@Getter
public enum RobotType implements Weighted {


    STONE("&7Stone Robot", RobotContainerTier.TIER_1, 1, 50, Material.STONE, 1),
    COAL("&8Coal Robot", RobotContainerTier.TIER_1, 2, 30, Material.COAL, 2),
    IRON("&fIron Robot", RobotContainerTier.TIER_2, 3, 50, Material.IRON_INGOT, 3),
    LAPIS("&9Lapis Robot", RobotContainerTier.TIER_2, 4, 20, Material.LAPIS_LAZULI, 4),
    REDSTONE("&cRedstone Robot", RobotContainerTier.TIER_3, 5, 50, Material.REDSTONE, 5),
    GOLD("&6Gold Robot", RobotContainerTier.TIER_3, 6, 20, Material.GOLD_INGOT, 6),
    DIAMOND("&bDiamond Robot", RobotContainerTier.TIER_4, 7, 50, Material.DIAMOND, 7),
    EMERALD("&aEmerald Robot", RobotContainerTier.TIER_4, 8, 20, Material.EMERALD, 8),
    BEDROCK("&8&lBedrock Robot", RobotContainerTier.TIER_4, 9, 5, Material.BEDROCK, 9);


    private final String displayName;
    private final RobotContainerTier retrievedFrom;
    private final int productionAmount;
    private final double chance;
    private final Material robotMaterial;
    private final int tier;

    public RobotType getNext(){
        return Arrays.stream(values())
                .filter(robotType -> robotType.getTier() == this.getTier() + 1)
                .findFirst()
                .orElse(this);
    }

    public List<String> getBaseLore(){
        List<String> lore = new ArrayList<>();
        lore.add("");
        lore.add("&7Robots are used to generate &dBeacons &7for");
        lore.add("&7your &dMine&7. &7These can then be used to");
        lore.add("&7boost your &dMine Upgrades&7.");
        lore.add("");
        lore.add("&5 Robot Information");
        lore.add("&d┃ &fTier: " + this.displayName);
        lore.add("&d┃ &fSpeed: &d10 seconds");
        lore.add("&d┃ &fBeacons: &d+" + this.productionAmount);
        lore.add("");
        return Text.color(lore);
    }

    public boolean isTopTier(){
        return this.getTier() == BEDROCK.tier;
    }

    @Override
    public double getWeight() {
        return this.chance;
    }

}
