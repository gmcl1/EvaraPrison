package net.evara.prison.customitem;

import lombok.Getter;
import lombok.Setter;
import net.evara.prison.PrisonCore;
import net.evara.prison.utils.ItemBuilder;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;

@Getter
@Setter
public abstract class CustomItem {

    private PrisonCore plugin;
    private String identifier;
    public abstract void onUse(PlayerInteractEvent e);
    public abstract ItemStack make();
    public ItemStack build() {
        ItemStack item = make();
        return new ItemBuilder()
                .of(item)
                .addPersistentData("custom_item", PersistentDataType.STRING, identifier)
                .build();
    }


}
