package net.evara.prison.player.enchants;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum EnchantType {

    NORMAL("&dNormal"),
    REBIRTH("&5Rebirth");

    private final String name;

}
