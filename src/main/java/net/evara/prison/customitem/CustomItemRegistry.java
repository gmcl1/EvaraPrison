package net.evara.prison.customitem;

import lombok.Getter;
import net.evara.prison.PrisonCore;
import org.reflections.Reflections;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Getter
public class CustomItemRegistry {

    private final PrisonCore plugin;
    private final Map<String, CustomItem> customItemMap = new ConcurrentHashMap<>();

    public CustomItemRegistry(PrisonCore plugin) {
        this.plugin = plugin;
        this.registerAllCustomItems();
    }

    public void registerAllCustomItems() {
        Reflections reflections = new Reflections("me.gary.prisoncore.customitem.items");
        for (Class<? extends CustomItem> clazz : reflections.getSubTypesOf(CustomItem.class)) {
            try {
                CustomItem customItem = clazz.newInstance();
                customItem.setPlugin(plugin);
                customItemMap.put(customItem.getIdentifier(), customItem);
                System.out.println("Registered custom item: " + customItem.getIdentifier());
            } catch (InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }


}
