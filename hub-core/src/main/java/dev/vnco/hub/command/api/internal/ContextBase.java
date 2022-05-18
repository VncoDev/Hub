package dev.vnco.hub.command.api.internal;

import java.lang.reflect.Method;

import dev.vnco.hub.command.api.annotation.Command;
import lombok.AllArgsConstructor;
import dev.vnco.hub.command.api.context.Base;

@AllArgsConstructor
public class ContextBase implements Base {

    private Command command;
    private Method method;
    private Object instance;

    @Override
    public Command getCommand() {
        return (command);
    }

    @Override
    public Method getMethod() {
        return (method);
    }

    @Override
    public Object getInstance() {
        return (instance);
    }
}
