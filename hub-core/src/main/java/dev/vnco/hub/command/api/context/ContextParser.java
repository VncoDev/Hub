package dev.vnco.hub.command.api.context;

public interface ContextParser {

	public <T> void append(Class<? extends T> aClass, T instance);

	public Iterable<RootContext> collect();
}
