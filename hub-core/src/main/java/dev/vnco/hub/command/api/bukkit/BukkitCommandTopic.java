package dev.vnco.hub.command.api.bukkit;

import org.bukkit.command.CommandSender;
import org.bukkit.help.GenericCommandHelpTopic;

public class BukkitCommandTopic extends GenericCommandHelpTopic {

    private BukkitCommand bukkitCommand;

    public BukkitCommandTopic(BukkitCommand bukkitCommand) {
        super(bukkitCommand);
        
        this.bukkitCommand = bukkitCommand;
    }

    @Override
    public boolean canSee(CommandSender sender) {
        if (!bukkitCommand.getContext().hasPermission()) {
            return (true);
        }

        return (sender.hasPermission(bukkitCommand.getContext().getPermission()));
    }
}
