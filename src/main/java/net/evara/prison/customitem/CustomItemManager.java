package net.evara.prison.customitem;

import lombok.Getter;
import net.evara.prison.PrisonCore;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.Optional;

@Getter
public class CustomItemManager implements Listener {

    private final PrisonCore plugin;
    private final CustomItemRegistry registry;

    public CustomItemManager(PrisonCore plugin) {
        this.plugin = plugin;
        this.registry = new CustomItemRegistry(plugin);
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onUse(PlayerInteractEvent e) {
        Player player = e.getPlayer();
        ItemStack itemInHand = player.getInventory().getItemInMainHand();

        if (e.getHand() != EquipmentSlot.HAND) {
            System.out.println("Hand is not main hand");
            return;
        }

        if (!isCustomItem(itemInHand))  {
            System.out.println("Item is not custom");
            return;
        }

        Optional<CustomItem> optional = getCustomItem(itemInHand);

        if (optional.isEmpty()) {
            System.out.println("Custom item is empty");
            return;
        }

        CustomItem customItem = optional.get();
        customItem.onUse(e);
    }

    public CustomItem getCustomItem(String identifier) {
        return registry.getCustomItemMap().get(identifier);
    }

    public Optional<CustomItem> getCustomItem(ItemStack item) {
        if (!isCustomItem(item)) return Optional.empty();
        ItemMeta meta = item.getItemMeta();
        PersistentDataContainer container = meta.getPersistentDataContainer();
        NamespacedKey key = new NamespacedKey(plugin, "custom_item");
        String identifier = container.get(key, PersistentDataType.STRING);
        return Optional.ofNullable(registry.getCustomItemMap().get(identifier));
    }

    public boolean isCustomItem(ItemStack item) {
        if (item == null) return false;
        if (item.getType() == Material.AIR) return false;
        ItemMeta meta = item.getItemMeta();
        PersistentDataContainer container = meta.getPersistentDataContainer();
        NamespacedKey key = new NamespacedKey(plugin, "custom_item");
        return container.has(key, PersistentDataType.STRING);
    }

}
