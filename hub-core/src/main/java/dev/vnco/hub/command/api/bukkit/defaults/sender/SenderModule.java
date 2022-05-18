package dev.vnco.hub.command.api.bukkit.defaults.sender;

import dev.vnco.hub.command.api.annotation.Sender;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import dev.vnco.hub.command.api.AbstractModule;

public class SenderModule extends AbstractModule {

    @Override
    protected void configure() {
        addBinding(Player.class, "a player");
        addBinding(ConsoleCommandSender.class, "the console");
        addBinding(CommandSender.class, "anyone");
    }

    private <C extends CommandSender> void addBinding(Class<C> aClass, String name) {
        bind(aClass).annotatedWith(Sender.class).toProvider(new SenderProvider<>(aClass, name));
    }
}
