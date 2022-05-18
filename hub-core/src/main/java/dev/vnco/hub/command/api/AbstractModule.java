package dev.vnco.hub.command.api;

import dev.vnco.hub.command.api.binding.AnnotatedBindingBuilder;
import dev.vnco.hub.command.api.binding.Binder;
import dev.vnco.hub.command.api.binding.Binding;
import com.google.common.base.Preconditions;

import java.util.Map;

public class AbstractModule implements Module {

    private Binder binder;
    private boolean configured;

    protected void configure() {}

    @Override
    public void configure(Binder binder) {
        Preconditions.checkState(!configured, "Module already configured.");
        try {
            this.binder = binder;
            configure();
        } finally {
            this.binder = null;
            configured = true;
        }
    }

    protected Binder binder() {
        return (binder);
    }

    protected void install(Module module) {
        Preconditions.checkState(!configured, "Module already configured.");
        binder.install(module);
    }

    protected <T> AnnotatedBindingBuilder<T> bind(Key<T> key) {
        Preconditions.checkState(!configured, "Module already configured.");
        return (binder.bind(key));
    }

    protected <T> AnnotatedBindingBuilder<T> bind(Class<T> aClass) {
        Preconditions.checkState(!configured, "Module already configured.");
        return (binder.bind(aClass));
    }

    protected Map<Key<?>, Binding<?>> getBindings() {
        Preconditions.checkState(!configured, "Module already configured.");
        return (binder.getBindings());
    }

    protected <T> Binding<T> getBinding(Key<T> key) {
        Preconditions.checkState(!configured, "Module already configured.");
        return (binder.getBinding(key));
    }

    protected <T> Binding<T> getBinding(Class<T> aClass) {
        Preconditions.checkState(!configured, "Module already configured.");
        return (binder.getBinding(aClass));
    }
}
