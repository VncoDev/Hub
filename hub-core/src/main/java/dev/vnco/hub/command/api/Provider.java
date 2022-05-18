package dev.vnco.hub.command.api;

import java.util.Collections;
import java.util.List;

import dev.vnco.hub.command.api.parameter.Parameter;

public interface Provider<T> {

    T provide(CommandSource<?> source, String string, Parameter<T> parameter);

    default List<String> suggest(CommandSource<?> source, Parameter<T> parameter) {
        return Collections.emptyList();
    }
}
