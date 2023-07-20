package net.evara.prison.player.enchants;

import lombok.Getter;
import net.evara.prison.PrisonCore;
import net.evara.prison.player.enchants.base.Enchant;
import org.reflections.Reflections;

import java.util.HashMap;
import java.util.Map;

@Getter
public class EnchantRegistry {


    private final PrisonCore plugin;
    private final Map<String, Enchant> enchantMap = new HashMap<>();

    public EnchantRegistry(PrisonCore plugin) {
        this.plugin = plugin;
        loadAllEnchants();
    }

    public Enchant getEnchant(String identifier){
        return enchantMap.get(identifier);
    }

    private void loadAllEnchants() {
        Reflections reflections = new Reflections("net.evara.prison.player.enchants.impl");
        System.out.println("Loading Enchants...");
        reflections.getSubTypesOf(Enchant.class).forEach(enchantClass -> {
            try {
                Enchant enchant = enchantClass.newInstance();
                if(!enchant.isEnabled()) return;
                enchant.setPlugin(plugin);
                enchantMap.put(enchant.getIdentifier(), enchant);
                System.out.println("Loaded Enchant: " + enchant.getIdentifier());
            } catch (InstantiationException | IllegalAccessException e) {
                System.out.println("Failed to load Enchant: " + enchantClass.getSimpleName());
            }
        });
        System.out.println("Loaded " + enchantMap.size() + " enchants!");
    }


}
