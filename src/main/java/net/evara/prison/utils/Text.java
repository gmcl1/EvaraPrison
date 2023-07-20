package net.evara.prison.utils;

import org.bukkit.ChatColor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Text {

    public static String format(String input) {
        String msg = colorize(input);
        return SmallCaps.convert(msg);
    }

    public static List<String> format(List<String> input) {
        List<String> output = new ArrayList<>();
        input.forEach(line -> output.add(format(line)));
        return output;
    }

    public static List<String> format(String... input) {
        return format(Arrays.asList(input));
    }

    public static String colorize(String input) {

        char from = '&';
        char to = ChatColor.COLOR_CHAR;

        char[] b = input.toCharArray();
        for (int i = 0; i < b.length - 1; i++) {
            if (b[i] == from && "0123456789AaBbCcDdEeFfKkLlMmNnOoRrXx".indexOf(b[i + 1]) > -1) {
                b[i] = to;
                b[i + 1] = Character.toLowerCase(b[i + 1]);
            }
        }
        return new String(b);
    }

}
