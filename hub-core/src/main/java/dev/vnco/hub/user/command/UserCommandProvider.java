package dev.vnco.hub.user.command;

import dev.henko.message.api.Messenger;
import dev.vnco.hub.api.storage.Storage;
import dev.vnco.hub.command.api.CommandSource;
import dev.vnco.hub.command.api.Provider;
import dev.vnco.hub.command.api.parameter.Parameter;
import dev.vnco.hub.user.User;
import me.yushust.inject.InjectAll;

import java.util.List;
import java.util.stream.Collectors;

@InjectAll
public class UserCommandProvider implements Provider<User> {

    private Messenger<String> messenger;
    private Storage<User> userStorage;

    @Override
    public User provide(CommandSource<?> source, String string, Parameter<User> parameter) {
        User user = userStorage.values().stream().filter(us -> us.getName().equalsIgnoreCase(string))
                .findFirst().orElse(null);

        if (user == null) {
            messenger.sendReplacing(source.getHandle(), "user-not-found", "%target_name%", string);
            return null;
        }

        return user;
    }

    @Override
    public List<String> suggest(CommandSource<?> source, Parameter<User> parameter) {
        return userStorage.values().stream().map(User::getName).collect(Collectors.toList());
    }
}
