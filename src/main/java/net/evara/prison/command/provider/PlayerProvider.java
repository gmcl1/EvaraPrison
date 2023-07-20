package net.evara.prison.command.provider;

import net.evara.prison.command.context.ICommandContext;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.stream.Collectors;

public class PlayerProvider extends CommandProvider<Player> {
    @Override
    public Player convert(String input, ICommandContext context) {
        CommandSender sender = context.getSender();
        Player target = Bukkit.getPlayer(input);
        if (target == null && sender instanceof Player player) {
            target = player;
        }
        return target;
    }

    @Override
    public List<String> tabComplete() {
        return Bukkit.getOnlinePlayers().stream().map(Player::getName).collect(Collectors.toList());
    }
}
