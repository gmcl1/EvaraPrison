package net.evara.prison.player;

import lombok.Getter;
import me.lucko.helper.utils.Players;
import net.evara.prison.player.currency.CurrencyHolder;
import net.evara.prison.player.enchants.EnchantHolder;
import net.evara.prison.player.enchants.EnchantRegistry;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.UUID;

@Getter
public class EvaraPlayer {

    private final UUID uuid;
    private final EnchantHolder enchantHolder;
    private final CurrencyHolder currencyHolder;

    public EvaraPlayer(UUID uuid, EnchantRegistry registry) {
        this.uuid = uuid;
        this.enchantHolder = new EnchantHolder(this, registry);
        this.currencyHolder = new CurrencyHolder(this);
    }

    public Player getPlayer() {
        return Bukkit.getPlayer(this.uuid);
    }

    public void msg(String msg) {
        Players.msg(getPlayer(), msg);
    }

}
