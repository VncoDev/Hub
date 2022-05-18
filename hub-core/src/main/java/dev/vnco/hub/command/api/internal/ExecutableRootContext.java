package dev.vnco.hub.command.api.internal;

import java.lang.reflect.Method;

import dev.vnco.hub.command.api.context.RootContext;

public class ExecutableRootContext extends ExecutableContext implements RootContext {

    public ExecutableRootContext(String name, Method method, Object instance) {
        super(null, name, method, instance);
    }

    @Override
    public RootContext getRoot() {
        return (this);
    }

    @Override
    public boolean isRoot() {
        return (true);
    }
}
