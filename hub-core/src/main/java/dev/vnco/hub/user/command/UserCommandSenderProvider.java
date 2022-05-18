package dev.vnco.hub.user.command;

import dev.henko.message.api.Messenger;
import dev.vnco.hub.api.storage.Storage;
import dev.vnco.hub.command.api.CommandSource;
import dev.vnco.hub.command.api.Provider;
import dev.vnco.hub.command.api.exception.ProviderException;
import dev.vnco.hub.command.api.parameter.Parameter;
import dev.vnco.hub.user.User;
import me.yushust.inject.InjectAll;
import org.bukkit.entity.Player;

@InjectAll
public class UserCommandSenderProvider implements Provider<User> {

    private Messenger<String> messenger;
    private Storage<User> userStorage;

    @Override
    public User provide(CommandSource<?> source, String string, Parameter<User> parameter) {
        Object handle = source.getHandle();

        if (!(handle instanceof Player)) {
            messenger.send(handle, "only-player");
            return null;
        }

        try {
            return userStorage.get(((Player) handle).getUniqueId().toString());
        } catch (Exception exception) {
            throw new ProviderException(exception);
        }
    }
}
