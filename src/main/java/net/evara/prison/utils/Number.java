package net.evara.prison.utils;

public class Number {

    public static String format(double amount) {
        // Format to i.e. 2.4k, 5.5m, 35.3b, etc.
        if (amount >= 1000000000000000L) return String.format("%.1fq", amount / 1000000000000000L);
        if (amount >= 1000000000000L) return String.format("%.1ft", amount / 1000000000000L);
        if (amount >= 1000000000) return String.format("%.1fb", amount / 1000000000);
        if (amount >= 1000000) return String.format("%.1fm", amount / 1000000);
        if (amount >= 1000) return String.format("%.1fk", amount / 1000);
        return String.format("%.1f", amount);
    }

}
