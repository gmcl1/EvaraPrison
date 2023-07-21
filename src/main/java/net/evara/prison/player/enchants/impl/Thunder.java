package net.evara.prison.player.enchants.impl;

import com.fastasyncworldedit.core.FaweAPI;
import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.WorldEdit;
import com.sk89q.worldedit.regions.Region;
import com.sk89q.worldedit.world.block.BlockTypes;
import me.lucko.helper.Schedulers;
import net.evara.prison.mines.PrivateMine;
import net.evara.prison.player.EvaraPlayer;
import net.evara.prison.player.enchants.AppliedEnchant;
import net.evara.prison.player.enchants.EnchantType;
import net.evara.prison.player.enchants.base.Enchant;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.LightningStrike;
import org.bukkit.event.block.BlockBreakEvent;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

public class Thunder extends Enchant {

    public Thunder() {
        super(
                "thunder",
                "&5&lThunder",
                List.of(
                        "&7Mine a 5x5 hole to the bottom of the mine."
                ),
                1,
                50,
                10,
                100,
                5,
                100,
                EnchantType.NORMAL,
                21,
                Material.END_ROD
        );
    }

    @Override
    public long onActivate(BlockBreakEvent e, PrivateMine mine, EvaraPlayer player, AppliedEnchant enchant) {
        if (mine == null) {
            return 0;
        }

        Location loc = e.getBlock().getLocation();
        Region hole = mine.getArea().getCuboidHole(loc, 2);
        AtomicLong blocks = new AtomicLong(0);

        try (
                EditSession editSession = WorldEdit.getInstance().newEditSessionBuilder()
                        .world(FaweAPI.getWorld(loc.getWorld().getName()))
                        .fastMode(true)
                        .maxBlocks(-1)
                        .build()
        ) {

            blocks.getAndSet(editSession.setBlocks(hole, BlockTypes.AIR));
            editSession.flushQueue();

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        Schedulers.sync()
                .run(() -> {

                    LightningStrike s = loc.getWorld().strikeLightningEffect(loc);

                    s.setFireTicks(0);

                });


        return blocks.get();
    }

    @Override
    public Optional<String> additionalInformation(AppliedEnchant enchant) {
        return Optional.empty();
    }

}
