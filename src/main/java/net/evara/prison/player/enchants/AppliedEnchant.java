package net.evara.prison.player.enchants;

import lombok.Getter;
import lombok.Setter;
import net.evara.prison.mines.PrivateMine;
import net.evara.prison.player.EvaraPlayer;
import net.evara.prison.player.enchants.base.Enchant;
import org.bukkit.event.block.BlockBreakEvent;

@Getter
@Setter
public class AppliedEnchant {

    private final Enchant enchant;
    private long level;
    private long prestige;
    private boolean alwaysActivate = true; // Admin Feature to always activate the enchant
    private boolean toggled = true;

    public AppliedEnchant(Enchant enchant){
        this.enchant = enchant;
        this.level = enchant.getStartLevel();
        this.prestige = 0L;
    }

    public long activate(BlockBreakEvent e, PrivateMine mine, EvaraPlayer player){
        return enchant.onActivate(e, mine, player, this);
    }

    public void addLevel(long amount){
        this.level += amount;
    }

    public void addPrestige(long amount){
        this.prestige += amount;
    }

    public boolean shouldActivate(){
        return enchant.shouldActivate(level);
    }

    public double getUpgradeCost(){
        return enchant.getCost(level);
    }



}
