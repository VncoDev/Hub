package dev.vnco.hub.command.api.binding;

import dev.vnco.hub.command.api.Key;
import dev.vnco.hub.command.api.Provider;

public interface Binding<T> {

    Key<T> getKey();

    Provider<T> getProvider();
}
