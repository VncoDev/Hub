package dev.vnco.hub.command.api.bukkit;

import java.lang.reflect.Field;
import java.util.Collection;

import org.bukkit.Server;
import org.bukkit.command.CommandMap;
import org.bukkit.help.HelpMap;
import org.bukkit.help.HelpTopic;

import com.google.common.collect.Sets;

public class BukkitCommandRegistrar {

    private CommandMap commandMap;
    private HelpMap helpMap;

    public BukkitCommandRegistrar(Server server) {
        this.commandMap = getCommandMap(server);
        this.helpMap = server.getHelpMap();
    }

    public void register(String owner, Collection<BukkitCommand> commands) {
        Collection<HelpTopic> topics = Sets.newHashSet();

        for (BukkitCommand command : commands) {
            topics.add(new BukkitCommandTopic(command));
            commandMap.register(owner, command);
        }

        HelpTopic topic = new BukkitCommandIndexHelpTopic(owner, topics);
        helpMap.addTopic(topic);
    }

    public void register(Collection<BukkitCommand> commands) {
        for (BukkitCommand command : commands) {
            helpMap.addTopic(new BukkitCommandTopic(command));
            commandMap.register(command.getName(), command);
        }
    }

    private CommandMap getCommandMap(Server server) {
        try {
            Field field = server.getClass().getDeclaredField("commandMap");
            field.setAccessible(true);

            return (CommandMap) field.get(server);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}
