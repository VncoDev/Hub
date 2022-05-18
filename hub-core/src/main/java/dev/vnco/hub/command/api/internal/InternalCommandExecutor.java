package dev.vnco.hub.command.api.internal;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import dev.vnco.hub.command.api.exception.ArgumentFormatException;
import dev.vnco.hub.command.api.exception.CommandUsageException;
import dev.vnco.hub.command.api.exception.ProviderException;
import dev.vnco.hub.command.api.ArgumentFormatter;
import dev.vnco.hub.command.api.CommandExecutor;
import dev.vnco.hub.command.api.CommandSource;
import dev.vnco.hub.command.api.UsageGenerator;
import dev.vnco.hub.command.api.context.Context;
import dev.vnco.hub.command.api.context.RootContext;

public class InternalCommandExecutor implements CommandExecutor {

    private RootContext rootContext;
    private ArgumentFormatter formatter;
    private UsageGenerator generator = new InternalUsageGenerator();

    public InternalCommandExecutor(RootContext rootContext, ArgumentFormatter formatter) {
        this.rootContext = rootContext;
        this.formatter = formatter;
    }

    @Override
    public void execute(CommandSource<?> source, String alias, String[] args) {
        try {
            execute(rootContext, source, args);
        } catch (CommandUsageException e) {
            Context context = e.getContext();

            if (!context.getChildren().isEmpty()) {
                if (context.getChildren().size() > 1) {
                	if(!source.hasPermission("nightmare.command." + context.getName())) {
                        source.message("&c" + "I'm sorry, but you do not have permission to perform this command. Please contact the server administrators if you believe that this is in error.");
                        return;
                	}
                	
                    // Help message
                	source.message("&cCommand Usage: /%s %s", alias, generator.generate(context));
                } else if (context.isRoot()) {
                    source.message("&cCommand Usage: /%s %s", alias, generator.generate(context));
                }

                return;
            }

            int steps = 0;
            Context parent = context.getParent();
            while (parent != null) {
                steps += 1;
                parent = parent.getParent();
            }

            StringBuilder path = new StringBuilder(alias + " ");

            for (int i = 0; i < args.length && i < steps; i++) {
                path.append(args[i]).append(" ");
            }

            source.message("&cCommand Usage: /%s%s", path.toString(), generator.generate(context));      
        }
    }

    @Override
    public List<String> suggest(CommandSource<?> source, String[] args) {
        try {
            return suggest(rootContext, source, args);
        } catch (CommandUsageException ignored) { }
        return Collections.emptyList();
    }

    @Override
    public UsageGenerator getGenerator() {
        return generator;
    }

    private void execute(Context context, CommandSource<?> source, String... args) {
        Context current;
        if (!context.getChildren().isEmpty()) {
            if (args.length == 0) {
                if (context.isHelp()) {
                    run(context, source, args);
                    return;
                }

                throw new CommandUsageException(args, context);
            }

            current = findCurrent(context, args[0]);

            if (current == null) {
                if (context.isHelp()) {
                    run(context, source, args);
                    return;
                }

                throw new CommandUsageException(args, context);
            }

            execute(current, source, Arrays.copyOfRange(args, 1, args.length));
            return;
        }

        if (context.hasPermission()) {
            if (!source.hasPermission(context.getPermission())) {
                source.message("&c" + "I'm sorry, but you do not have permission to perform this command. Please contact the server administrators if you believe that this is in error.");
                return;
            }
        }

        run(context, source, args);
    }

    private void run(Context context, CommandSource<?> source, String... args) {
        Method method = context.getMethod();

        if (method == null) {
            source.message("&c%s doesn't have a method.", context.getName());
            return;
        }

        try {
            Object[] arguments = this.formatter.format(source, context, args);
            method.invoke(context.getInstance(), arguments);

        } catch (ArgumentFormatException | ProviderException e) {
            if (e.getMessage() == null || e.getMessage().isEmpty()) {
                throw new CommandUsageException(args, context);
            }

            source.message("&c%s", e.getMessage());
        } catch (Exception e) {
            source.message("&cAn exception occurred whilst executing your command.");
            e.printStackTrace();
        }
    }

    private Context findCurrent(Context context, String check) {
        if (context.matchesIgnoreCase(check)) {
            return (context);
        }

        for (Context child : context.getChildren()) {
            if (child.matchesIgnoreCase(check)) {
                return (child);
            }
        }

        return (null);
    }

    private List<String> suggest(Context context, CommandSource<?> source, String... args) {
        Context current;
        
        if (!context.getChildren().isEmpty()) {
            if (args.length == 0) {
                return (context.getChildren().stream()
                        .filter(childNode -> !childNode.hasPermission() || source.hasPermission(childNode.getPermission()))
                        .map(Context::getName)
                        .sorted()
                        .collect(Collectors.toList()
                        )
                );
            }

            current = findCurrent(rootContext, args[0]);

            if (current == null) {
                return (context.getChildren().stream()
                        .filter(childNode -> !childNode.hasPermission() || source.hasPermission(childNode.getPermission()))
                        .map(Context::getName)
                        .filter(s -> s.toLowerCase().startsWith(args[args.length - 1].toLowerCase()))
                        .sorted()
                        .collect(Collectors.toList()
                        )
                );
            }

            return (suggest(current, source, Arrays.copyOfRange(args, 1, args.length)));
        }

        if (args.length == 0) {
            return (Collections.emptyList());
        }

        if (context.hasPermission()) {
            if (!source.hasPermission(context.getPermission())) {
                return (Collections.emptyList());
            }
        }

        String lastWord = args[args.length - 1];
        int pos = args.length - 1;
        
        return (formatter.suggest(source, context, lastWord, pos));
    }
}
