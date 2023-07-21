package net.evara.prison.player.enchants;

import lombok.Getter;
import lombok.Setter;
import net.evara.prison.mines.PrivateMine;
import net.evara.prison.player.EvaraPlayer;
import net.evara.prison.player.currency.Currency;
import net.evara.prison.player.currency.CurrencyType;
import net.evara.prison.player.enchants.base.Enchant;
import net.evara.prison.utils.Number;
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


    public void upgrade(EvaraPlayer player, int amount){
        CurrencyType type = CurrencyType.TOKENS;
        Currency currency = player.getCurrencyHolder().getCurrency(type);
        double currencyAmount = currency.getAmount();

        if(level >= enchant.getMaxLevel()){
            player.msg("&cYou cannot upgrade this enchantment any further!");
            player.msg("&cThis enchantment can only be upgraded to level " + enchant.getMaxLevel() + "!");
            return;
        }

        if(level + amount >= enchant.getMaxLevel()){
            amount = (int) (enchant.getMaxLevel() - level);
        }

        double cost = enchant.getCost(level, amount);

        if(currencyAmount < cost) {
            player.msg("&cYou do not have enough tokens to upgrade this enchantment!");
            player.msg("&cYou need " + type.getSymbol() +  Number.format(cost) +  " " + type.getName() +  " to upgrade this enchantment!");
            return;
        }

        currency.remove(cost);
        addLevel(amount);
        player.msg("&5&lENCHANTS &7• Added &d" + amount + " &7levels to " + enchant.getDisplayName() + "&7 for &d" + type.getSymbol() + Number.format(cost) + "&7.");
    }

    public void prestige(EvaraPlayer player, int level, int prestige){
        CurrencyType type = CurrencyType.TOKENS;
        Currency currency = player.getCurrencyHolder().getCurrency(type);
        double currencyAmount = currency.getAmount();
        double cost = enchant.getPrestigeCost(level, prestige);
        if(currencyAmount < cost) {
            player.msg("&cYou do not have enough tokens to prestige this enchantment!");
            player.msg("&cYou need " + type.getSymbol() +  Number.format(cost) +  " " + type.getName() +  " to prestige this enchantment!");
            return;
        }
        currency.remove(cost);
        addPrestige(1);
        setLevel(0);

        player.msg("&aYou have prestiged " + enchant.getDisplayName() + " to prestige " + prestige + "!");
        player.msg("&aThis cost you " + type.getSymbol() + Number.format(cost) + " " + type.getName() + "!");
        player.msg("&aYou now have " + type.getSymbol() + Number.format(currency.getAmount()) + " " + type.getName() + "!");
        player.msg("");
        player.msg("&aYour " + enchant.getDisplayName() + " is now level 0!");

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
