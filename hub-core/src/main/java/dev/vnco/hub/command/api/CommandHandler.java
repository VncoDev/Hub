package dev.vnco.hub.command.api;

import dev.vnco.hub.command.api.binding.Binder;

public interface CommandHandler {

    Binder getBinder();

    <T> CommandHandler register(T instance, Class<? extends T> aClass);

    <T> CommandHandler register(T instance);

    void apply();

    void applyTo(String owner);
}
