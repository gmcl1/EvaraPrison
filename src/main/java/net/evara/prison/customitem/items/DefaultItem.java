package net.evara.prison.customitem.items;

import net.evara.prison.utils.ItemBuilder;
import net.evara.prison.customitem.CustomItem;
import org.bukkit.Material;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Objects;

public class DefaultItem extends CustomItem {

    public DefaultItem() {
        setIdentifier("default_item");
    }

    @Override
    public void onUse(PlayerInteractEvent e) {
        Objects.requireNonNull(e.getClickedBlock()).setType(Material.DIAMOND_ORE);
        e.setCancelled(true);
    }

    @Override
    public ItemStack make() {
        return new ItemBuilder()
                .setLore(
                        "&7This is a default item.",
                        "&7You can use this as a template for your own custom items."
                )
                .build();
    }
}
