package net.evara.prison.player.enchants.base;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import net.evara.prison.PrisonCore;
import net.evara.prison.mines.PrivateMine;
import net.evara.prison.player.EvaraPlayer;
import net.evara.prison.player.enchants.AppliedEnchant;
import net.evara.prison.player.enchants.EnchantType;
import org.bukkit.Material;
import org.bukkit.event.block.BlockBreakEvent;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Setter
@Getter
public abstract class Enchant {

    private PrisonCore plugin;
    private final String identifier;
    private final String displayName;
    private final List<String> description;
    private final float baseChance;
    private final float maxChance;
    private final long startLevel;
    private final long maxLevel;
    private final long maxPrestige;
    private final double cost;
    private final EnchantType type;
    private final int slot;
    private final Material icon;
    private boolean enabled = true;

    /**
     * Called when {@link Enchant} is activated
     *
     * @return the amount of blocks mined
     */
    public abstract long onActivate(BlockBreakEvent e, PrivateMine mine, EvaraPlayer player, AppliedEnchant enchant);

    public abstract Optional<String> additionalInformation(AppliedEnchant enchant);

    public boolean shouldActivate(AppliedEnchant enchant) {
        if (enchant.isAlwaysActivate()) return true;
        return (Math.random() <= getCurrentChanceOfActivating(enchant.getLevel()));
    }

    public boolean shouldActivate(long level) {
        return (Math.random() <= getCurrentChanceOfActivating(level));
    }

    public int getLevelsPlayerCanAfford(long currentLevel, double tokens) {

        int totalLevels = 0;
        double cost = getCost(currentLevel);
        while (tokens >= cost && currentLevel + totalLevels < maxLevel) {
            tokens -= cost;
            cost = getCost(currentLevel + (totalLevels + 1));
            totalLevels++;
        }

        return totalLevels;
    }

    public double getCost(long currentLevel, long levels) {
        double totalCost = 0;
        for (int i = 0; i < levels; i++) {
            totalCost += getCost(currentLevel + i);
        }
        return totalCost;
    }

    public double getCost(long level) {
        return (cost * ((double) (level + 1) / 15) + 25);
    }

    public double getPrestigeCost(long level, long prestige) {
        if (prestige == this.maxPrestige) return -1;
        double baseCost = getCost(level);
        return baseCost + (baseCost * ((prestige + 1) * 0.30));
    }

    public double getCurrentChanceOfActivating(long level) {
        return (baseChance + (level * (maxChance - baseChance) / maxLevel));
    }


}
