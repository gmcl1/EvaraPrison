package net.evara.prison.player;

import lombok.Getter;
import net.evara.prison.PrisonCore;
import net.evara.prison.player.enchants.EnchantRegistry;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Getter
public class PlayerManager implements Listener {

    private final PrisonCore plugin;
    private final EnchantRegistry enchantRegistry;
    private final Map<UUID, EvaraPlayer> loadedPlayers = new ConcurrentHashMap<>();

    public PlayerManager(PrisonCore plugin) {
        this.plugin = plugin;
        this.enchantRegistry = new EnchantRegistry(plugin);
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Player player = e.getPlayer();
        load(player);
    }

    public void load(Player player) {
        if (isLoaded(player)) return;
        this.loadedPlayers.put(
                player.getUniqueId(),
                new EvaraPlayer(player.getUniqueId(), enchantRegistry)
        );
    }

    public boolean isLoaded(Player player) {
        return this.loadedPlayers.containsKey(player.getUniqueId());
    }

    public EvaraPlayer getPlayer(UUID uuid) {
        return this.loadedPlayers.get(uuid);
    }

}
