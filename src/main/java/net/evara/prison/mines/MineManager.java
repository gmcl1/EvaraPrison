package net.evara.prison.mines;

import lombok.Getter;
import lombok.Setter;
import net.evara.prison.PrisonCore;
import net.evara.prison.player.EvaraPlayer;
import net.evara.prison.player.PlayerManager;
import net.evara.prison.utils.EmptyWorldGenerator;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Getter
@Setter
public class MineManager implements Listener {

    private final PrisonCore plugin;
    private final PlayerManager playerManager;
    private final Map<UUID, PrivateMine> loadedPrivateMines = new ConcurrentHashMap<>();
    private final World mineWorld;
    private int lastMineX = 0;

    public MineManager(PrisonCore plugin) {
        this.plugin = plugin;
        this.playerManager = plugin.getPlayerManager();
        this.mineWorld = plugin.getServer().getWorld("mines") == null ? EmptyWorldGenerator.generateEmptyWorld("mines") : plugin.getServer().getWorld("mines");
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Player player = e.getPlayer();
        PrivateMine mine = getMine(player);
        mine.enter(player);
    }


    @EventHandler
    public void onBreak(BlockBreakEvent e) {
        Player player = e.getPlayer();
        PrivateMine mine = getMine(player);
        PrivateMineMember member = mine.getMembers().getMembers().get(player.getUniqueId());
        EvaraPlayer evaraPlayer = playerManager.getPlayer(player.getUniqueId());

        if (member == null) {
            e.setCancelled(true);
            return;
        }

        evaraPlayer.getEnchantHolder().runEnchants(e, mine).whenComplete(
                (blocks, throwable) -> {
                    if(throwable != null){
                        throwable.printStackTrace();
                        return;
                    }

                    member.addBlocksMined(blocks);
                }
        );

    }

    private void loadNewMine(UUID uuid) {
        setLastMineX(getLastMineX() + 500);
        this.loadedPrivateMines.put(uuid, new PrivateMine(mineWorld, uuid, this.lastMineX));
    }

    private void loadNewMine(Player player) {
        setLastMineX(getLastMineX() + 500);
        this.loadedPrivateMines.put(player.getUniqueId(), new PrivateMine(mineWorld, player.getUniqueId(), this.lastMineX));
    }

    public PrivateMine getMine(Player player) {
        if (!hasOrIsMemberOfMine(player))
            loadNewMine(player);
        return this.loadedPrivateMines.get(player.getUniqueId());
    }

    private boolean hasOrIsMemberOfMine(Player player) {
        return loadedPrivateMines
                .values()
                .stream()
                .anyMatch(mine -> mine.getMembers().isMember(player.getUniqueId()));
    }

}
