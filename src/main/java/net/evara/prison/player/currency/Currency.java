package net.evara.prison.player.currency;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Currency {

    private final CurrencyType type;
    private double amount;

    public Currency(CurrencyType type){
        this.type = type;
        this.amount = Double.MAX_VALUE;
    }

    public void add(double amount) {
        this.amount += amount;
    }

    public void remove(double amount) {
        if (this.amount - amount < 0) this.amount = 0;
        else this.amount -= amount;
    }

    public boolean has(double amount){
        return this.amount >= amount;
    }

}
