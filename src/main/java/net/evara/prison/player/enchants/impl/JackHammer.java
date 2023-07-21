package net.evara.prison.player.enchants.impl;

import com.fastasyncworldedit.core.FaweAPI;
import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.WorldEdit;
import com.sk89q.worldedit.regions.Region;
import com.sk89q.worldedit.world.block.BlockTypes;
import net.evara.prison.mines.PrivateMine;
import net.evara.prison.player.EvaraPlayer;
import net.evara.prison.player.enchants.AppliedEnchant;
import net.evara.prison.player.enchants.EnchantType;
import net.evara.prison.player.enchants.base.Enchant;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.event.block.BlockBreakEvent;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

public class JackHammer extends Enchant {
    public JackHammer() {
        super(
                "jackhammer",
                "&5&lJack Hammer",
                List.of(
                        "&7Destroys a layer of blocks."
                ),
                1,
                50,
                10,
                50000,
                5,
                0.12,
                EnchantType.NORMAL,
                12,
                Material.IRON_PICKAXE
        );
    }

    @Override
    public long onActivate(BlockBreakEvent e, PrivateMine mine, EvaraPlayer player, AppliedEnchant enchant) {
        if (mine == null) {
            return 0;
        }

        Location loc = e.getBlock().getLocation();
        Region layer = mine.getArea().getLayer(loc.getBlockY());
        AtomicLong blocks = new AtomicLong(0);

        try (
                EditSession editSession = WorldEdit.getInstance().newEditSessionBuilder()
                        .world(FaweAPI.getWorld(loc.getWorld().getName()))
                        .fastMode(true)
                        .maxBlocks(-1)
                        .build()
        ) {

            blocks.getAndSet(editSession.setBlocks(layer, BlockTypes.AIR));
            editSession.flushQueue();

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return blocks.get();
    }

    @Override
    public Optional<String> additionalInformation(AppliedEnchant enchant) {
        return Optional.empty();
    }

}
