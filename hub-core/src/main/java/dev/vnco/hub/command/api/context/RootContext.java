package dev.vnco.hub.command.api.context;

public interface RootContext extends Context {
	
    @Override
    public RootContext getRoot();

    @Override
    boolean isRoot();
}
