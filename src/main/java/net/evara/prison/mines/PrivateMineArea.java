package net.evara.prison.mines;

import com.fastasyncworldedit.core.FaweAPI;
import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.WorldEdit;
import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldedit.regions.CuboidRegion;
import com.sk89q.worldedit.regions.Region;
import com.sk89q.worldedit.world.block.BlockTypes;
import lombok.Getter;
import lombok.Setter;
import me.lucko.helper.Schedulers;
import net.evara.prison.utils.Position;
import org.apache.commons.lang3.tuple.Pair;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

@Getter
@Setter
public class PrivateMineArea {

    private final PrivateMine mine;
    private Position pos1;
    private Position pos2;
    private Position borderPos1;
    private Position borderPos2;
    private Position spawnPos;

    public PrivateMineArea(PrivateMine mine, World mineWorld, int lastMinePosition) {
        this.mine = mine;

        this.pos1 = new Position(
                lastMinePosition,
                50,
                lastMinePosition,
                mineWorld.getName()
        );

        this.pos2 = new Position(
                lastMinePosition + 11,
                100,
                lastMinePosition + 11,
                mineWorld.getName()
        );

        this.borderPos1 = new Position().of(pos1).subtract(35, 1, 35);
        this.borderPos2 = new Position().of(pos2).add(35, 0, 35);
        this.spawnPos = new Position().centerOf(pos1, pos2).add(0, 1, 0);

        this.reset();
    }

    public void reset() {
        Schedulers.async()
                .run(() -> {
                    try (
                            EditSession parentSession = WorldEdit.getInstance().newEditSessionBuilder()
                                    .world(FaweAPI.getWorld(pos1.getWorld()))
                                    .fastMode(true)
                                    .maxBlocks(-1)
                                    .build();

                            EditSession childSession = WorldEdit.getInstance().newEditSessionBuilder()
                                    .world(FaweAPI.getWorld(pos1.getWorld()))
                                    .fastMode(true)
                                    .maxBlocks(-1)
                                    .build();
                    ) {

                        Region borderRegion = getBorderRegion();
                        Pair<Region, Region> barrierRegion = getBorderBarrierRegion();
                        Region mineableRegion = getMineableRegion();

                        parentSession.setBlocks(borderRegion, BlockTypes.BEDROCK);
                        parentSession.setBlocks(barrierRegion.getLeft(), BlockTypes.BARRIER);
                        parentSession.setBlocks(barrierRegion.getRight(), BlockTypes.BARRIER);
                        childSession.setBlocks(mineableRegion, BlockTypes.EMERALD_BLOCK);

                        parentSession.flushQueue();
                        childSession.flushQueue();


                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }).thenAcceptSync((v) -> this.teleportAllToSpawn());
    }

    public Region getLayer(int y){
        return new CuboidRegion(
                BlockVector3.at(pos1.getX(), y, pos1.getZ()),
                BlockVector3.at(pos2.getX(), y, pos2.getZ())
        );
    }

    public Region getCuboidHole(Location loc, int size){
        return new CuboidRegion(
                BlockVector3.at(loc.getX() - size, pos1.getY(), loc.getZ() - size),
                BlockVector3.at(loc.getX() + size, loc.getY() + size, loc.getZ() + size)
        );
    }

    public void enterMine(Player player) {
        player.teleport(spawnPos.toLocation());
    }

    public void teleportAllToSpawn() {
        this.mine.getMembers()
                .getAllMembers()
                .stream()
                .filter(PrivateMineMember::isOnline)
                .filter(privateMineMember -> {
                    Player player = privateMineMember.getPlayer();
                    Location loc = player.getLocation();
                    return getMineableRegion().contains(BlockVector3.at(loc.getX(), loc.getY(), loc.getZ()));
                })
                .forEach(player -> player.getPlayer().teleport(spawnPos.toLocation()));
    }

    public Region getMineableRegion() {
        return new CuboidRegion(
                BlockVector3.at(pos1.getX(), pos1.getY(), pos1.getZ()),
                BlockVector3.at(pos2.getX(), pos2.getY(), pos2.getZ())
        );
    }

    public Region getBorderRegion() {
        return new CuboidRegion(
                BlockVector3.at(borderPos1.getX(), borderPos1.getY(), borderPos1.getZ()),
                BlockVector3.at(borderPos2.getX(), borderPos2.getY(), borderPos2.getZ())
        );
    }

    public Pair<Region, Region> getBorderBarrierRegion() {

        Region barrier = new CuboidRegion(
                BlockVector3.at(borderPos1.getX(), borderPos2.getY() + 1, borderPos1.getZ()),
                BlockVector3.at(borderPos2.getX(), borderPos2.getY() + 50, borderPos2.getZ())
        ).getWalls();

        Region roof = new CuboidRegion(
                BlockVector3.at(borderPos1.getX(), borderPos2.getY() + 50, borderPos1.getZ()),
                BlockVector3.at(borderPos2.getX(), borderPos2.getY() + 50, borderPos2.getZ())
        );

        return Pair.of(barrier, roof);
    }

    public void expand() {
        if (exceedBorderArea()) {
            return;
        }
        this.pos1 = new Position().of(pos1).subtract(1, 0, 1);
        this.pos2 = new Position().of(pos2).add(1, 0, 1);
        this.spawnPos = new Position().centerOf(pos1, pos2).add(0, 1, 0);
        this.reset();

        this.mine.getMembers().messageAll(
                "Your mine has been expanded"
        );
    }

    private boolean exceedBorderArea() {
        return pos1.getX() < borderPos1.getX() - 2 ||
                pos1.getZ() < borderPos1.getZ() - 2 ||
                pos2.getX() > borderPos2.getX() - 2 ||
                pos2.getZ() > borderPos2.getZ() - 2;
    }

}
