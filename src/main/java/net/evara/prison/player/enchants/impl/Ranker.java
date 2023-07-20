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

public class Ranker extends Enchant {

    public Ranker() {
        super(
                "ranker",
                "&5&lRanker",
                List.of(
                    "&7Mine a certain amount of blocks to",
                        "&7earn rewards."
                ),
                1,
                50,
                10,
                100,
                5,
                100,
                EnchantType.NORMAL,
                16,
                Material.REDSTONE
        );
    }

    @Override
    public long onActivate(BlockBreakEvent e, PrivateMine mine, EvaraPlayer player, AppliedEnchant enchant) {
        return 0L;
    }

    @Override
    public Optional<String> additionalInformation(AppliedEnchant enchant) {
        return Optional.empty();
    }


}
