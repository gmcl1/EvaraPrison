package net.evara.prison.customitem;

import lombok.Getter;
import lombok.Setter;
import me.lucko.helper.item.ItemStackBuilder;
import net.evara.prison.PrisonCore;
import org.bukkit.NamespacedKey;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

@Getter
@Setter
public abstract class CustomItem {

    private PrisonCore plugin;
    private String identifier;

    public CustomItem(String identifier) {
        this.identifier = identifier;
    }

    public abstract void onUse(PlayerInteractEvent e);
    public abstract ItemStack make();
    public ItemStack build() {
        return ItemStackBuilder.of(make())
                .transformMeta(meta -> {
                    PersistentDataContainer container = meta.getPersistentDataContainer();
                    container.set(new NamespacedKey(plugin, "custom_item"), PersistentDataType.STRING, identifier);
                })
                .build();

    }

    public <T, Z> Z getPersistentData(ItemStack itemStack, String key, PersistentDataType<T, Z> type){
        return itemStack.getItemMeta().getPersistentDataContainer().get(new NamespacedKey(plugin, key), type);
    }


}
