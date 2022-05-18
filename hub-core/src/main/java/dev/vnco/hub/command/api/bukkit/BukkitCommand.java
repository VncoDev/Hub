package dev.vnco.hub.command.api.bukkit;

import java.util.List;
import java.util.concurrent.ForkJoinPool;

import dev.vnco.hub.command.api.context.Context;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import dev.vnco.hub.command.api.CommandExecutor;
import dev.vnco.hub.command.api.CommandSource;

public class BukkitCommand extends Command {

    private CommandExecutor commandExecutor;
    private Context context;

    public BukkitCommand(Context context, CommandExecutor commandExecutor, String usage) {
        super(context.getName());
        
        this.commandExecutor = commandExecutor;
        this.context = context;

        setAliases(context.getAliases());

        if (context.hasDescription()) {
            setDescription(context.getDescription());
        }

        if (context.hasPermission()) {
            setPermission(context.getPermission());
        }

        setUsage("/<command> " + usage);
    }

    public Context getContext() {
        return (context);
    }

    @Override
    public boolean execute(CommandSender sender, String alias, String[] args) {
        CommandSource<?> source = new BukkitCommandSource(sender);
        
        if (context.isAsync()) {
            ForkJoinPool.commonPool().submit(() -> {
                commandExecutor.execute(source, alias, args);
            });
        } else {
            commandExecutor.execute(source, alias, args);
        }

        return (true);
    }

    @Override
    public List<String> tabComplete(CommandSender sender, String alias, String[] args) {
        return (commandExecutor.suggest(new BukkitCommandSource(sender), args));
    }
}
