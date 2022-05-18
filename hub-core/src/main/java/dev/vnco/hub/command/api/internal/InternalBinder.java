package dev.vnco.hub.command.api.internal;

import java.util.Map;

import com.google.common.collect.Maps;

import dev.vnco.hub.command.api.Key;
import dev.vnco.hub.command.api.Module;
import dev.vnco.hub.command.api.binding.AnnotatedBindingBuilder;
import dev.vnco.hub.command.api.binding.Binder;
import dev.vnco.hub.command.api.binding.Binding;

public class InternalBinder implements Binder {

    private Map<Key<?>, Binding<?>> bindings;

    public InternalBinder(Module... modules) {
        this.bindings = Maps.newConcurrentMap();
        
        for (Module module : modules) {
            install(module);
        }
    }

    @Override
    public void install(Module module) {
        module.configure(this);
    }

    @Override
    public <T> AnnotatedBindingBuilder<T> bind(Key<T> key) {
        return (new InternalAnnotatedBindingBuilder<>(bindings, new InternalBinding<>(key)));
    }

    @Override
    public Map<Key<?>, Binding<?>> getBindings() {
        return (bindings);
    }

    @Override
    public <T> Binding<T> getBinding(Key<T> key) {
        Binding<?> binding = this.bindings.get(key);
        
        return (binding == null ? null : (Binding<T>) binding);
    }
}
