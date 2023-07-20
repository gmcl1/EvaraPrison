package net.evara.prison.player.enchants.impl;

import net.evara.prison.mines.PrivateMine;
import net.evara.prison.player.EvaraPlayer;
import net.evara.prison.player.enchants.AppliedEnchant;
import net.evara.prison.player.enchants.EnchantType;
import net.evara.prison.player.enchants.base.Enchant;
import org.bukkit.Material;
import org.bukkit.event.block.BlockBreakEvent;

import java.text.DecimalFormat;
import java.util.List;
import java.util.Optional;

public class Tokenator extends Enchant {

    /**
     * set {_tokenatoramount} to (({_level} * 1.0005) * 7.75 + 15000)
     * set {_addamount} to ({_tokenatoramount} * (({_prestige} + 1) * 0.4))
     * add {_addamount} to {_tokenatoramount}
     * set {_tokenatormax} to ({_tokenatoramount} * 1.2)
     */

    /*
        Token Amount = level * 1.0005 * 7.75 + 15000
        Prestige Token Amount = Token Amount * (prestige + 1) * 0.4
        Base Token Amount = Token Amount + Prestige Token Amount
        Max Token Amount = Token Amount * 1.2
     */
    public Tokenator() {
        super(
                "tokenator",
                "&5&lTokenator",
                List.of(
                        "&7Chance to find Tokens while mining."
                ),
                1,
                50,
                10,
                100,
                5,
                100,
                EnchantType.NORMAL,
                15,
                Material.PURPLE_DYE
        );
    }

    private double getMinTokenAmount(long level, long prestige) {
        double tokenAmount = (level * 1.0005 * 7.75 + 15000);
        double prestigeTokenAmount = tokenAmount * (prestige + 1) * 0.4;
        return tokenAmount + prestigeTokenAmount;
    }

    private double getMaxTokenAmount(long level, long prestige) {
        return getMinTokenAmount(level, prestige) * 1.2;
    }

    @Override
    public long onActivate(BlockBreakEvent e, PrivateMine mine, EvaraPlayer player, AppliedEnchant enchant) {
        return 0L;
    }

    @Override
    public Optional<String> additionalInformation(AppliedEnchant enchant) {

        long level = enchant.getLevel();
        long prestige = enchant.getPrestige();
        double min = getMinTokenAmount(level, prestige);
        double max = getMaxTokenAmount(level, prestige);
        DecimalFormat df = new DecimalFormat("#,###.##");

        return Optional.of(
                "&fAmount: &d" + df.format(min) + " - " + df.format(max) + " Tokens"
        );
    }


}
