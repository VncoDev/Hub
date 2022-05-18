package dev.vnco.hub.command.api;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public abstract class CommandSource<S> {

    private S handle;

    public S getHandle() {
        return handle;
    }

    public abstract boolean hasPermission(String permission);

    public abstract void message(String message);

    public void message(String message, Object... args) {
        message(String.format(message, args));
    }
}
