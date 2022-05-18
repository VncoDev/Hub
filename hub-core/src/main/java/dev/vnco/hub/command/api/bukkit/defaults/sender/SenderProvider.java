package dev.vnco.hub.command.api.bukkit.defaults.sender;

import dev.vnco.hub.command.api.exception.ProviderException;
import dev.vnco.hub.command.api.parameter.Parameter;
import org.bukkit.command.CommandSender;

import lombok.AllArgsConstructor;
import dev.vnco.hub.command.api.CommandSource;
import dev.vnco.hub.command.api.Provider;

@AllArgsConstructor
public class SenderProvider<C extends CommandSender> implements Provider<C> {

    private Class<C> type;
    private String name;

    @Override
    public C provide(CommandSource<?> source, String string, Parameter<C> parameter) {
        if (!type.isInstance(source.getHandle())) {
            throw new ProviderException("This can only be executed by %s.", name);
        }

        return type.cast(source.getHandle());
    }
}
