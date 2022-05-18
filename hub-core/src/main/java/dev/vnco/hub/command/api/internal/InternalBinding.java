package dev.vnco.hub.command.api.internal;

import dev.vnco.hub.command.api.Key;
import dev.vnco.hub.command.api.Provider;
import dev.vnco.hub.command.api.binding.Binding;

public class InternalBinding<T> implements Binding<T> {

    private Key<T> key;
    private Provider<T> provider;

    public InternalBinding(Key<T> key) {
        this.key = key;
    }

    @Override
    public Key<T> getKey() {
        return (key);
    }

    public void setKey(Key<T> key) {
        this.key = key;
    }

    @Override
    public Provider<T> getProvider() {
        return (provider);
    }

    void setProvider(Provider<T> provider) {
        this.provider = provider;
    }
}
