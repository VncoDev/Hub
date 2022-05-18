package dev.vnco.hub.command.api;

import dev.vnco.hub.command.api.binding.Binder;

public interface Module {

    void configure(Binder binder);

}
