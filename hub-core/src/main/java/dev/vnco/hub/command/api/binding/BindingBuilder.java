package dev.vnco.hub.command.api.binding;

import dev.vnco.hub.command.api.Provider;

public interface BindingBuilder<T> {

    void toProvider(Provider<T> provider);

    void toInstance(T instance);
}
