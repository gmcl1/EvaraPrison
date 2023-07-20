package net.evara.prison.mines.ui;

import me.lucko.helper.menu.Item;
import me.lucko.helper.menu.paginated.PaginatedGui;
import me.lucko.helper.menu.paginated.PaginatedGuiBuilder;
import me.lucko.helper.menu.scheme.MenuScheme;
import me.lucko.helper.menu.scheme.StandardSchemeMappings;
import net.evara.prison.mines.MineManager;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class PrivateMineListUI extends PaginatedGui {

    private static Function<PaginatedGui, List<Item>> getContent(Player player, MineManager manager) {
        return gui -> {
            return manager.getLoadedPrivateMines()
                    .values()
                    .stream()
                    .map(mine -> {
                        return mine.getInfo().toMenuItem(manager, player);
                    })
                    .collect(Collectors.toList());
        };
    }

    public PrivateMineListUI(Player player, MineManager manager) {
        super(getContent(player, manager), player,
                PaginatedGuiBuilder.create()
                        .title("&5&lPrivate Mine")
                        .scheme(
                                new MenuScheme(StandardSchemeMappings.STAINED_GLASS)
                                        .mask("100000001")
                                        .mask("100000001")
                                        .mask("100000001")
                                        .mask("100000001")
                                        .mask("100000001")
                                        .mask("100000001")
                                        .scheme(10, 10)
                                        .scheme(10, 10)
                                        .scheme(10, 10)
                                        .scheme(10, 10)
                                        .scheme(10, 10)
                                        .scheme(10, 10)
                        )

        );
    }
}
