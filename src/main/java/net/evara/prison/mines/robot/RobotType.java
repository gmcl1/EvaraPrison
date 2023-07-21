package net.evara.prison.mines.robot;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import me.lucko.helper.random.Weighted;
import org.bukkit.Material;

@RequiredArgsConstructor
@Getter
public enum RobotType implements Weighted {


    STONE("&7Stone Robot", RobotContainerTier.TIER_1, 1, 50, Material.STONE),
    COAL("&8Coal Robot", RobotContainerTier.TIER_1, 2, 30, Material.COAL),
    IRON("&fIron Robot", RobotContainerTier.TIER_2, 3, 50, Material.IRON_INGOT),
    LAPIS("&9Lapis Robot", RobotContainerTier.TIER_2, 4, 20, Material.LAPIS_LAZULI),
    REDSTONE("&cRedstone Robot", RobotContainerTier.TIER_3, 5, 50, Material.REDSTONE),
    GOLD("&6Gold Robot", RobotContainerTier.TIER_3, 6, 20, Material.GOLD_INGOT),
    DIAMOND("&bDiamond Robot", RobotContainerTier.TIER_4, 7, 50, Material.DIAMOND),
    EMERALD("&aEmerald Robot", RobotContainerTier.TIER_4, 8, 20, Material.EMERALD),
    BEDROCK("&8&lBedrock Robot", RobotContainerTier.TIER_4, 9, 5, Material.BEDROCK);


    private final String displayName;
    private final RobotContainerTier retrievedFrom;
    private final int productionAmount;
    private final double chance;
    private final Material robotMaterial;

    @Override
    public double getWeight() {
        return this.chance;
    }

}
