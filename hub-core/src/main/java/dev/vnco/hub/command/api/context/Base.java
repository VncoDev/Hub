package dev.vnco.hub.command.api.context;

import java.lang.reflect.Method;

import dev.vnco.hub.command.api.annotation.Command;

public interface Base {
	
    Command getCommand();

    Method getMethod();

    Object getInstance();
}
