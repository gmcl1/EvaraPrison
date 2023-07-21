package net.evara.prison.customitem.items;

import net.evara.prison.customitem.CustomItem;
import org.bukkit.Material;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Objects;

public class DefaultItem extends CustomItem {

    public DefaultItem() {
        super("default_item");
    }

    @Override
    public void onUse(PlayerInteractEvent e) {
        Objects.requireNonNull(e.getClickedBlock()).setType(Material.DIAMOND_ORE);
        e.setCancelled(true);
    }

    @Override
    public ItemStack make() {
        return new ItemStack(Material.DIAMOND);
    }
}
