package net.evara.prison.player.currency;

import lombok.Getter;
import me.lucko.helper.utils.Players;
import net.evara.prison.customitem.items.CurrencyNote;
import net.evara.prison.player.EvaraPlayer;
import net.evara.prison.utils.Number;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Getter
public class CurrencyHolder {

    private final EvaraPlayer player;
    private final Map<CurrencyType, Currency> currencies = new ConcurrentHashMap<>();

    public CurrencyHolder(EvaraPlayer player) {
        this.player = player;
        for (CurrencyType type : CurrencyType.values()) {
            this.currencies.put(type, new Currency(type));
        }
    }

    public Currency getCurrency(CurrencyType type) {
        return this.currencies.get(type);
    }

    public Optional<CurrencyNote> withdraw(CurrencyType type, double amount) {
        if (!hasCurrency(type)) this.currencies.put(type, new Currency(type));
        if(!has(type, amount)) return Optional.empty();
        this.currencies.get(type).remove(amount);
        Players.msg(this.player.getPlayer(), "&dYou've withdrawn &5" + type.getSymbol()  + Number.format(amount) + " " + type.getName() + "&d!");
        return Optional.of(new CurrencyNote(player.getPlayer(), type, 1, amount));
    }

    public void add(CurrencyType type, double amount) {
        if (!hasCurrency(type)) this.currencies.put(type, new Currency(type));
        this.currencies.get(type).add(amount);
        Players.msg(this.player.getPlayer(), "&dYou have received &5" + type.getSymbol()  + Number.format(amount) + " " + type.getName() + "&d!");
    }

    public void remove(CurrencyType type, double amount) {
        if (!hasCurrency(type)) this.currencies.put(type, new Currency(type));
        this.currencies.get(type).remove(amount);
        Players.msg(this.player.getPlayer(), "&5" + type.getSymbol()  + Number.format(amount) + " " + type.getName() + "&d has been taken from you!");
    }

    public boolean has(CurrencyType type, double amount) {
        return this.currencies.get(type).has(amount);
    }

    private boolean hasCurrency(CurrencyType type) {
        return this.currencies.containsKey(type);
    }

}
