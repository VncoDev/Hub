package dev.vnco.hub.command.api.bukkit;

import java.util.Collection;

import dev.vnco.hub.command.api.CommandHandler;
import dev.vnco.hub.command.api.binding.Binder;
import dev.vnco.hub.command.api.bukkit.defaults.BukkitModule;
import dev.vnco.hub.command.api.context.ContextParser;
import dev.vnco.hub.command.api.context.RootContext;
import dev.vnco.hub.command.api.internal.InternalArgumentFormatter;
import dev.vnco.hub.command.api.internal.InternalCommandExecutor;
import dev.vnco.hub.command.api.internal.InternalContextParser;
import dev.vnco.hub.command.api.internal.InternalParameterParser;
import org.bukkit.plugin.java.JavaPlugin;

import com.google.common.collect.Sets;

import dev.vnco.hub.command.api.CommandExecutor;
import dev.vnco.hub.command.api.Module;

public class BukkitCommandHandler implements CommandHandler {

    private Binder binder;
    private ContextParser contextParser;
    private InternalArgumentFormatter argumentFormatter;
    private BukkitCommandRegistrar registrar;

    public BukkitCommandHandler(JavaPlugin plugin, Module... modules) {
        this.binder = Binder.newBinder(modules);
        binder.install(new BukkitModule(plugin.getServer()));
        
        this.contextParser = new InternalContextParser(new InternalParameterParser(binder));
        this.argumentFormatter = new InternalArgumentFormatter();
        this.registrar = new BukkitCommandRegistrar(plugin.getServer());
    }

    @Override
    public Binder getBinder() {
        return (binder);
    }

    @Override
    public <T> CommandHandler register(T instance, Class<? extends T> aClass) {
        contextParser.append(aClass, instance);
        return (this);
    }

    @Override
    public <T> CommandHandler register(T instance) {
        return (register(instance, instance.getClass()));
    }

    @Override
    public void apply() {
        Iterable<RootContext> contexts = contextParser.collect();
        Collection<BukkitCommand> commands = Sets.newHashSet();

        for (RootContext rootContext : contexts) {
            CommandExecutor commandExecutor = new InternalCommandExecutor(rootContext, this.argumentFormatter);
            commands.add(new BukkitCommand(rootContext, commandExecutor, commandExecutor.getGenerator().generate(rootContext)));
        }

        registrar.register(commands);
    }

    @Override
    public void applyTo(String owner) {
        Iterable<RootContext> contexts = contextParser.collect();
        Collection<BukkitCommand> commands = Sets.newHashSet();

        for (RootContext rootContext : contexts) {
            CommandExecutor commandExecutor = new InternalCommandExecutor(rootContext, this.argumentFormatter);
            commands.add(new BukkitCommand(rootContext, commandExecutor, commandExecutor.getGenerator().generate(rootContext)));
        }

        registrar.register(owner, commands);
    }
}
