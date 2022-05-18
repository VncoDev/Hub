package dev.vnco.hub.command.api;

import java.util.List;

public interface CommandExecutor {

    void execute(CommandSource<?> source, String alias, String[] args);

    List<String> suggest(CommandSource<?> source, String[] args);

    UsageGenerator getGenerator();

}
