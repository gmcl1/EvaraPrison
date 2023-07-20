package net.evara.prison.utils;

import lombok.Getter;
import net.evara.prison.PrisonCore;
import org.apache.commons.lang3.tuple.Pair;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.Plugin;

import java.util.*;

@Getter
public class ItemBuilder {

    private ItemStack item = null;
    private Material material = Material.STONE;
    private String name = "&bDefault";
    private List<String> lore = new ArrayList<>();
    private int amount = 1;
    private int data = 0;
    private final Map<String, Pair<PersistentDataType<?, ?>, ?>> persistentData = new HashMap<>();

    public ItemBuilder of(ItemStack item) {
        this.item = item;
        return this;
    }

    public ItemBuilder setName(String name) {
        this.name = name;
        return this;
    }

    public ItemBuilder setLore(List<String> lore) {
        this.lore = lore;
        return this;
    }

    public ItemBuilder setLore(String... lore) {
        this.lore = new ArrayList<>(Arrays.asList(lore));
        return this;
    }

    public ItemBuilder setAmount(int amount) {
        this.amount = amount;
        return this;
    }

    public ItemBuilder setData(int data) {
        this.data = data;
        return this;
    }

    public ItemBuilder setMaterial(Material material) {
        this.material = material;
        return this;
    }

    public ItemBuilder addPersistentData(String key, PersistentDataType<?, ?> type, Object value) {
        persistentData.put(key, Pair.of(type, value));
        return this;
    }

    public ItemBuilder removePersistentData(String key) {
        persistentData.remove(key);
        return this;
    }

    public ItemBuilder clearPersistentData() {
        persistentData.clear();
        return this;
    }

    public <T, Z> ItemStack build() {
        Plugin plugin = PrisonCore.getProvidingPlugin(PrisonCore.class);

        ItemStack item = this.item == null ? new ItemStack(material) : this.item;
        item.setAmount(amount);
        ItemMeta meta = item.getItemMeta();
        PersistentDataContainer container = meta.getPersistentDataContainer();
        meta.setCustomModelData(data);
        meta.setDisplayName(name);
        meta.setLore(lore);
        persistentData.forEach(
                (key, value) -> {
                    NamespacedKey namespacedKey = new NamespacedKey(plugin, key);

                    PersistentDataType<T, Z> type = (PersistentDataType<T, Z>) value.getLeft();
                    Z data = (Z) value.getRight();

                    container.set(namespacedKey, type, data);
                }
        );

        item.setItemMeta(meta);
        return item;
    }


}
