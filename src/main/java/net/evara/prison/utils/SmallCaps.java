package net.evara.prison.utils;

import lombok.Getter;
import org.apache.commons.lang3.StringUtils;
import org.bukkit.util.StringUtil;

@Getter
public enum SmallCaps {

    A('ᴀ'),
    B('ʙ'),
    C('ᴄ'),
    D('ᴅ'),
    E('ᴇ'),
    F('ꜰ'),
    G('ɢ'),
    H('ʜ'),
    I('ɪ'),
    J('ᴊ'),
    K('ᴋ'),
    L('ʟ'),
    M('ᴍ'),
    N('ɴ'),
    O('ᴏ'),
    P('ᴘ'),
    Q('ǫ'),
    R('ʀ'),
    S('ꜱ'),
    T('ᴛ'),
    U('ᴜ'),
    V('ᴠ'),
    W('ᴡ'),
    X('x'),
    Y('ʏ'),
    Z('ᴢ');

    private final char character;

    SmallCaps(char character) {
        this.character = character;
    }

    public static String convert(String input) {
        StringBuilder sb = new StringBuilder();
        for (char c : input.toCharArray()) {
            if (Character.isAlphabetic(c)) {
                char smallCapChar = SmallCaps.valueOf(Character.toUpperCase(c) + "").getCharacter();
                sb.append(smallCapChar);
            } else {
                sb.append(c);
            }
        }
        return sb.toString();
    }


}
