package dev.vnco.hub.command.api;

import java.util.List;

import dev.vnco.hub.command.api.context.Context;

public interface ArgumentFormatter {

    Object[] format(CommandSource<?> source, Context context, String... args);

    List<String> suggest(CommandSource<?> source, Context context, String lastWord, int pos);
}
