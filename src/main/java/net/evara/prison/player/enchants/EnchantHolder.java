package net.evara.prison.player.enchants;

import lombok.Getter;
import lombok.Setter;
import net.evara.prison.mines.PrivateMine;
import net.evara.prison.player.EvaraPlayer;
import net.evara.prison.player.enchants.base.Enchant;
import org.bukkit.event.block.BlockBreakEvent;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicLong;

@Getter
@Setter
public class EnchantHolder {

    private final EvaraPlayer player;
    private final Map<String, AppliedEnchant> enchantments = new HashMap<>();

    public EnchantHolder(EvaraPlayer player, EnchantRegistry registry) {
        this.player = player;
        registry.getEnchantMap()
                .values()
                .forEach(this::load);
    }

    public CompletableFuture<Long> runEnchants(BlockBreakEvent e, PrivateMine mine) {
        AtomicLong blocksMined = new AtomicLong(0L);

        CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
            blocksMined.set(enchantments.values()
                    .stream()
                    .filter(AppliedEnchant::shouldActivate)
                    .filter(AppliedEnchant::isToggled)
                    .mapToLong(enchant -> enchant.activate(e, mine, player))
                    .sum());
        });

        return future.thenApply((Void) -> blocksMined.get());
    }

    public List<AppliedEnchant> getAll() {
        return enchantments.values().stream().toList();
    }

    public AppliedEnchant getEnchant(String identifier) {
        return enchantments.get(identifier);
    }

    private void load(Enchant enchant) {
        this.enchantments.put(
                enchant.getIdentifier(),
                new AppliedEnchant(enchant)
        );
    }

}
