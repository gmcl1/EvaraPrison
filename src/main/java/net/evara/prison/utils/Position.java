package net.evara.prison.utils;

import com.google.gson.JsonObject;
import lombok.Data;
import org.bukkit.Location;

@Data
public class Position {

    private int x;
    private int y;
    private int z;
    private String world;

    public Position(int x, int y, int z, String world) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.world = world;
    }

    public Position() {

    }

    public Position of(Position pos) {
        return new Position(pos.getX(), pos.getY(), pos.getZ(), pos.getWorld());
    }

    public Position centerOf(Position pos1, Position pos2) {
        return new Position(
                (pos1.getX() + pos2.getX()) / 2,
                pos2.getY(),
                (pos1.getZ() + pos2.getZ()) / 2,
                pos1.getWorld()
        );
    }

    public Position add(int x, int y, int z) {
        return new Position(this.x + x, this.y + y, this.z + z, this.world);
    }

    public Position subtract(int x, int y, int z) {
        return new Position(this.x - x, this.y - y, this.z - z, this.world);
    }

    public Position of(Location loc) {
        return new Position(loc.getBlockX(), loc.getBlockY(), loc.getBlockZ(), loc.getWorld().getName());
    }

    public Location toLocation() {
        return new Location(org.bukkit.Bukkit.getWorld(world), x, y, z);
    }


    public JsonObject toJson() {
        JsonObject json = new JsonObject();
        json.addProperty("x", x);
        json.addProperty("y", y);
        json.addProperty("z", z);
        json.addProperty("world", world);
        return json;
    }

}
