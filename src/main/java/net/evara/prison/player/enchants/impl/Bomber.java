package net.evara.prison.player.enchants.impl;

import com.fastasyncworldedit.core.FaweAPI;
import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.WorldEdit;
import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldedit.regions.Region;
import com.sk89q.worldedit.regions.factory.SphereRegionFactory;
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

public class Bomber extends Enchant {

    public Bomber() {
        super(
                "bomber",
                "&5&lBomber",
                List.of(
                       "&7Destroys blocks in a Sphere pattern."
                ),
                1,
                50,
                10,
                100,
                5,
                100,
                EnchantType.NORMAL,
                13,
                Material.TNT
        );
    }

    @Override
    public long onActivate(BlockBreakEvent e, PrivateMine mine, EvaraPlayer player, AppliedEnchant enchant) {

        if (mine == null) {
            return 0;
        }

        Location loc = e.getBlock().getLocation();
        Region sphere = new SphereRegionFactory().createCenteredAt(BlockVector3.at(loc.getX(), loc.getY(), loc.getZ()), 4);
        AtomicLong blocks = new AtomicLong(0);

        try (
                EditSession editSession = WorldEdit.getInstance().newEditSessionBuilder()
                        .world(FaweAPI.getWorld(loc.getWorld().getName()))
                        .fastMode(true)
                        .maxBlocks(-1)
                        .build()
        ) {

            blocks.getAndSet(editSession.setBlocks(sphere, BlockTypes.AIR));
            editSession.flushQueue();

        } catch (Exception ex) {
            ex.printStackTrace();
        }



        return blocks.get();
    }

    @Override
    public Optional<String> additionalInformation(AppliedEnchant enchant) {
        return Optional.of(
                "&fRadius: &d4 &7(+" + enchant.getPrestige() + 1 + "&7)"
        );
    }

}
