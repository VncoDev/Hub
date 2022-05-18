package dev.vnco.hub.command.api.defaults.instance;

import dev.vnco.hub.command.api.parameter.Parameter;
import lombok.AllArgsConstructor;
import dev.vnco.hub.command.api.CommandSource;
import dev.vnco.hub.command.api.Provider;

@AllArgsConstructor
public class InstanceProvider<I> implements Provider<I> {

    private I instance;

    @Override
    public I provide(CommandSource<?> source, String string, Parameter<I> parameter) {
        return (instance);
    }
}
