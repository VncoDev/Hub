package dev.vnco.hub.command.api.binding;

import java.util.Map;

import dev.vnco.hub.command.api.internal.InternalBinder;
import dev.vnco.hub.command.api.Key;
import dev.vnco.hub.command.api.Module;

public interface Binder {

	public static Binder newBinder(Module... modules) {
        return new InternalBinder(modules);
    }

	public void install(Module module);

	public <T> AnnotatedBindingBuilder<T> bind(Key<T> key);

	public default <T> AnnotatedBindingBuilder<T> bind(Class<T> aClass) {
        return bind(Key.get(aClass));
    }

	public Map<Key<?>, Binding<?>> getBindings();

	public <T> Binding<T> getBinding(Key<T> key);

	public default <T> Binding<T> getBinding(Class<T> aClass) {
        return getBinding(Key.get(aClass));
    }
}
