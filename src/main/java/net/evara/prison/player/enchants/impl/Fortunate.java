package net.evara.prison.player.enchants.impl;

import net.evara.prison.mines.PrivateMine;
import net.evara.prison.player.EvaraPlayer;
import net.evara.prison.player.enchants.AppliedEnchant;
import net.evara.prison.player.enchants.EnchantType;
import net.evara.prison.player.enchants.base.Enchant;
import org.bukkit.Material;
import org.bukkit.event.block.BlockBreakEvent;

import java.util.List;
import java.util.Optional;

public class Fortunate extends Enchant {

    public Fortunate() {
        super(
                "fortunate",
                "&5&lFortunate",
                List.of(
                        "&7Chance to add Fortune to your",
                        "&7pickaxe."
                ),
                1,
                50,
                10,
                100,
                5,
                100,
                EnchantType.NORMAL,
                14,
                Material.BOOK
        );
    }

    @Override
    public long onActivate(BlockBreakEvent e, PrivateMine mine, EvaraPlayer player, AppliedEnchant enchant) {
        return 0L;
    }

    @Override
    public Optional<String> additionalInformation(AppliedEnchant enchant) {
        return Optional.of(
                "&fAmount: &d+1 Level(s)"
        );
    }


}
