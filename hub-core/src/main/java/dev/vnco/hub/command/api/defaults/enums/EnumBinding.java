package dev.vnco.hub.command.api.defaults.enums;

import dev.vnco.hub.command.api.Key;
import dev.vnco.hub.command.api.Provider;
import dev.vnco.hub.command.api.binding.Binding;

public class EnumBinding<E extends Enum<E>> implements Binding<E> {

    private Key<E> key;
    private Provider<E> provider;

    public EnumBinding(Key<E> key) {
        this.key = key;
        this.provider = new EnumProvider<E>(key);
    }

    @Override
    public Key<E> getKey() {
        return key;
    }

    @Override
    public Provider<E> getProvider() {
        return provider;
    }
}
