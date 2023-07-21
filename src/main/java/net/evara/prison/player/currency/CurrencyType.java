package net.evara.prison.player.currency;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum CurrencyType {

    TOKENS("Tokens", '⛃');

    private final String name;
    private final char symbol;

}
