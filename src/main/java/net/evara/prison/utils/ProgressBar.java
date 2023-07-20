package net.evara.prison.utils;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ProgressBar {

    private final double max;
    private final double current;
    private final int length;
    private final char symbol;
    private final char emptySymbol;
    private final String prefix;
    private final String suffix;

    public ProgressBar(double max, double current, int length, char symbol, char emptySymbol, String prefix, String suffix) {
        if(current > max) current = max;
        this.max = max;
        this.current = current;
        this.length = length;
        this.symbol = symbol;
        this.emptySymbol = emptySymbol;
        this.prefix = prefix;
        this.suffix = suffix;
    }

    public String getBar() {
        StringBuilder builder = new StringBuilder();
        builder.append(prefix);
        int progress = (int) ((current / max) * length);
        for (int i = 0; i < length; i++) {
            if (i <= progress) {
                builder.append(symbol);
            } else {
                builder.append(emptySymbol);
            }
        }
        builder.append(suffix);
        return builder.toString();
    }

    public String getBarWithPercent() {
        StringBuilder builder = new StringBuilder();
        builder.append("&7" + prefix);
        int progress = (int) ((current / max) * length);
        for (int i = 0; i < length; i++) {
            if (i <= progress) {
                builder.append("&a" + symbol);
            } else {
                builder.append("&c" + emptySymbol);
            }
        }
        builder.append("&7" + suffix);
        builder.append(" ");
        builder.append(getPercent());
        return Text.colorize(builder.toString());
    }

    public String getPercent() {
        return (int) (((double) current / (double) max) * 100) + "%";
    }


}
