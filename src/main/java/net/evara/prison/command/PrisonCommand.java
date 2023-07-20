package net.evara.prison.command;

import lombok.Getter;
import lombok.Setter;
import net.evara.prison.PrisonCore;
import net.evara.prison.command.context.ICommandContext;
import net.evara.prison.command.parameter.CommandParameter;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandMap;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Getter
@Setter
public abstract class PrisonCommand extends Command {


    private final PrisonCore plugin;
    private final String name;
    private String commandDescription = "";
    private String permission = "";
    private String noPermissionMessage = "";
    private String onlyPlayerMessage = "";
    private boolean requirePermission = false;
    private boolean playerOnly = false;
    private final List<String> aliases = new ArrayList<>();
    private final List<CommandParameter> parameterList = new ArrayList<>();
    private final List<PrisonCommand> children = new ArrayList<>();

    private static CommandMap commandMap;

    static {
        try {
            final Field bukkitCommandMap = Bukkit.getServer().getClass().getDeclaredField("commandMap");
            bukkitCommandMap.setAccessible(true);
            commandMap = (CommandMap) bukkitCommandMap.get(Bukkit.getServer());
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public PrisonCommand(PrisonCore plugin, String name) {
        super(name);
        this.plugin = plugin;
        this.name = name;
        this.aliases.add(name);
    }


    public abstract void execute(ICommandContext context);

    public void register() {
        commandMap.register(this.name, this);
    }

    @Override
    public boolean execute(@NotNull CommandSender sender, @NotNull String label, @NotNull String[] args) {

        if (this.aliases.contains(label) || label.equalsIgnoreCase(this.name)) {

            if (this.requirePermission && !sender.hasPermission(this.permission)) {
                sender.sendMessage(this.noPermissionMessage);
                return false;
            }

            if (this.playerOnly && !(sender instanceof Player)) {
                sender.sendMessage(this.onlyPlayerMessage);
                return false;
            }

            if (this.children.isEmpty() && args.length >= this.parameterList.size()) {
                this.execute(new ICommandContext(sender, args));
                return true;
            }

            if (args.length == 0) {
                this.execute(new ICommandContext(sender, args));
                return false;
            }

            Optional<PrisonCommand> child = this.getChildByName(args[0]);
            child.ifPresentOrElse(childCommand -> {
                childCommand.execute(new ICommandContext(sender, Arrays.copyOfRange(args, 1, args.length)));
            }, () -> sendHelp(sender));

            return true;
        }

        return true;
    }

    @Override
    public @NotNull List<String> tabComplete(@NotNull CommandSender sender, @NotNull String alias, @NotNull String[] args) throws IllegalArgumentException {

         /*
            index(length)
            /test 0(1) 1(2) 2(3) 3(4)
        */

        if (args.length == 1 && hasChildren()) {
            return this.children.stream().map(PrisonCommand::getName).collect(Collectors.toList());
        }

        if (!hasChildren() && hasParameters()) {
            // Parameters work in such a way that after the sub command they start at index 0
            // So if have a command /main sub <param1> <param2> <param3>
            // The first parameter will be at index 0, the second at index 1 and so on

            int index = args.length - 1;
            Optional<CommandParameter> parameter = getByIndex(index);
            if (parameter.isPresent()) {
                return parameter.get().tabComplete();
            }

        }

        Optional<PrisonCommand> child = this.getChildByName(args[0]);
        return child.map(command -> command.tabComplete(sender, alias, Arrays.copyOfRange(args, 1, args.length))).orElseGet(ArrayList::new);

    }


    public Optional<CommandParameter> getByIndex(int index) {
        if (index >= this.parameterList.size()) return Optional.empty();
        return this.parameterList.stream()
                .filter(parameter -> parameter.getIndex() == index)
                .findFirst();
    }

    public void sendHelp(CommandSender sender) {
        this.getCommandUsage().forEach(sender::sendMessage);
    }

    public List<String> getCommandUsage() {
        List<String> usage = new ArrayList<>();
        usage.add("&7&m----------------------------------------");
        usage.add("&6&l" + this.name + " &7- &e" + this.description);
        usage.add("&7&m----------------------------------------");
        if (hasChildren()) {
            this.children.forEach(child -> {
                usage.add("&6/" + this.name + " " + child.getName() + " &e" + child.getParameters());
            });
        } else {
            usage.add("&6/" + this.name + " &e" + this.getParameters());
        }
        return usage;
    }

    public String getParameters() {
        StringBuilder builder = new StringBuilder();
        for (CommandParameter parameter : this.parameterList) {
            builder.append(parameter.toString()).append(" ");
        }
        return builder.toString();
    }

    public List<String> getChildrenNames() {
        return this.children.stream()
                .map(PrisonCommand::getName)
                .toList();
    }

    public Optional<PrisonCommand> getChildByName(String name) {
        return this.children.stream()
                .filter(child -> child.getName().equalsIgnoreCase(name))
                .findFirst();
    }

    public boolean hasParameters() {
        return !this.parameterList.isEmpty();
    }

    public void addChild(PrisonCommand command) {
        this.children.add(command);
    }

    public void addChildren(PrisonCommand... commands) {
        this.children.addAll(Arrays.asList(commands));
    }

    public void addParameter(CommandParameter parameter) {
        this.parameterList.add(parameter);
    }

    public void addParameters(CommandParameter... parameters) {
        this.parameterList.addAll(Arrays.asList(parameters));
    }

    public void addAlias(String alias) {
        this.aliases.add(alias);
    }

    public void addAliases(String... aliases) {
        this.aliases.addAll(Arrays.asList(aliases));
    }

    public boolean hasChildren() {
        return !this.children.isEmpty();
    }

    public <T> T parse(ICommandContext context, int index) {
        Optional<CommandParameter> parameter = this.getByIndex(index);
        if (parameter.isEmpty()) return null;

        CommandParameter commandParameter = parameter.get();

        if (commandParameter.isVarArg()) {
            StringBuilder builder = new StringBuilder();
            for (int i = index; i < context.getArgs().length; i++) {
                builder.append(context.getArgs()[i]).append(" ");
            }
            return (T) commandParameter.getProvider().convert(builder.toString().trim(), context);
        }

        if (commandParameter.isOptional()) {
            if (context.getArgs().length <= index) {
                return (T) commandParameter.getProvider().convert(parameter.get().getDefaultValue(), context);
            }
        }

        return (T) commandParameter.getProvider().convert(context.getArgs()[index], context);
    }


}
