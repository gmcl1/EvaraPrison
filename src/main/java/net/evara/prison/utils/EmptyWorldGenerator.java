package net.evara.prison.utils;

import org.bukkit.Bukkit;
import org.bukkit.GameRule;
import org.bukkit.World;
import org.bukkit.WorldCreator;

public class EmptyWorldGenerator {

    public static World generateEmptyWorld(String worldName) {

        WorldCreator wc = new WorldCreator(worldName);
        wc.generator(new EmptyWorld());
        World world = wc.createWorld();
        assert world != null;
        world.setGameRule(GameRule.DO_MOB_SPAWNING, false);
        world.setGameRule(GameRule.DO_DAYLIGHT_CYCLE, false);
        world.setGameRule(GameRule.DO_WEATHER_CYCLE, false);
        world.setGameRule(GameRule.DO_FIRE_TICK, false);
        world.setGameRule(GameRule.DO_TILE_DROPS, false);
        world.setGameRule(GameRule.DO_ENTITY_DROPS, false);
        world.setGameRule(GameRule.DO_MOB_LOOT, false);

        return Bukkit.getWorld(worldName);
    }

}
