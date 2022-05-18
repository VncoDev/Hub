package dev.vnco.hub.services;

import dev.vnco.hub.Hub;
import dev.vnco.hub.api.model.Service;
import dev.vnco.hub.command.ScoreboardCommand;
import dev.vnco.hub.command.SpawnCommand;
import dev.vnco.hub.command.api.CommandHandler;
import dev.vnco.hub.command.api.annotation.Sender;
import dev.vnco.hub.command.api.binding.Binder;
import dev.vnco.hub.command.message.MessageCommand;
import dev.vnco.hub.user.User;
import dev.vnco.hub.user.command.UserCommandProvider;
import dev.vnco.hub.user.command.UserCommandSenderProvider;
import me.yushust.inject.InjectAll;

@InjectAll
public class CommandService implements Service {

    private Hub hub;

    private CommandHandler commandHandler;

    private MessageCommand messageCommand;
    private ScoreboardCommand scoreboardCommand;
    private SpawnCommand spawnCommand;

    private UserCommandProvider userCommandProvider;
    private UserCommandSenderProvider userCommandSenderProvider;

    @Override
    public void start() {
        Binder binder = commandHandler.getBinder();

        binder.bind(User.class).toProvider(userCommandProvider);
        binder.bind(User.class).annotatedWith(Sender.class).toProvider(userCommandSenderProvider);

        registerCommands(

                messageCommand,
                scoreboardCommand,
                spawnCommand

        );

        commandHandler.applyTo(hub.getName());
    }

    @SafeVarargs
    private final <C> void registerCommands(C... cs) {
        for (C c : cs) {
            commandHandler.register(c);
        }
    }

    @Override
    public int getPriority() {
        return 4;
    }
}
