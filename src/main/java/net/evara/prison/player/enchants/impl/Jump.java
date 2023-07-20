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

public class Jump extends Enchant {

    public Jump() {
        super(
                "jump_boost",
                "&5&lJump Boost",
                List.of(
                       "&7Increases the height that you can jump."
                ),
                1,
                50,
                10,
                100,
                5,
                100,
                EnchantType.NORMAL,
                23,
                Material.RABBIT_HIDE
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
