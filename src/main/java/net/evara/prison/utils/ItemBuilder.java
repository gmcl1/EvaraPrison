package net.evara.prison.utils;

import lombok.Getter;
import net.evara.prison.PrisonCore;
import org.apache.commons.lang3.tuple.Pair;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.*;

@Getter
public class ItemBuilder {


    private ItemStack itemStack = null;
    private String name;
    private Material material;
    private List<String> lore;
    private int amount;
    private boolean glow;
    private boolean hideAttributes;
    private int customModelData;
    private Map<String, Pair<PersistentDataType<?, ?>, ?>> persistentData = new HashMap<>();

    public ItemBuilder of(ItemStack itemStack) {
        this.itemStack = itemStack;
        return this;
    }

    public ItemBuilder of(Material material) {
        this.material = material;
        return this;
    }

    public ItemBuilder of(Material material, int amount) {
        this.material = material;
        this.amount = amount;
        return this;
    }

    public ItemBuilder name(String name) {
        this.name = name;
        return this;
    }

    public ItemBuilder lore(List<String> lore) {
        this.lore = lore;
        return this;
    }

    public ItemBuilder lore(String... lore) {
        this.lore = Arrays.asList(lore);
        return this;
    }

    public ItemBuilder amount(int amount) {
        this.amount = amount;
        return this;
    }

    public ItemBuilder glow(boolean glow) {
        this.glow = glow;
        return this;
    }

    public ItemBuilder hideAttributes(boolean hideAttributes) {
        this.hideAttributes = hideAttributes;
        return this;
    }

    public ItemBuilder customModelData(int customModelData) {
        this.customModelData = customModelData;
        return this;
    }

    public <T, Z> ItemBuilder addPersistentData(String key, PersistentDataType<T, Z> type, Z value) {
        if (value != null) {
            this.persistentData.put(key, Pair.of(type, value));
        }
        return this;
    }

    @SuppressWarnings("deprecation")
    public <T, Z> ItemStack build() {
        ItemStack itemStack = this.itemStack != null ? this.itemStack : new ItemStack(material);
        itemStack.setAmount(amount);
        ItemMeta meta = itemStack.getItemMeta();

        PersistentDataContainer container = meta.getPersistentDataContainer();
        meta.setDisplayName(Text.colorize(name));
        meta.setLore(colorize(lore));

        if (glow) {
            meta.addEnchant(Enchantment.CHANNELING, 1, true);
        }

        if (hideAttributes) {
            meta.addItemFlags(ItemFlag.values());
        }

        if (customModelData != 1) {
            meta.setCustomModelData(customModelData);
        }


        this.persistentData.forEach((key, data) -> {

            PersistentDataType<T, Z> type = (PersistentDataType<T, Z>) data.getLeft();
            Z value = (Z) data.getRight();

            container.set(new NamespacedKey(PrisonCore.getInstance(), key), type, value);
            System.out.println(type.toString());
            System.out.println("Added " + key + " to " + itemStack.getType().name() + " with value " + value.toString());
        });

        itemStack.setItemMeta(meta);
        return itemStack;
    }

    private List<String> colorize(List<String> input) {
        List<String> output = new ArrayList<>();
        for (String s : input) {
            output.add(Text.colorize(s));
        }
        return output;
    }


}
