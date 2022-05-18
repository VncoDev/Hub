package dev.vnco.hub.command.api.bukkit;

import java.util.Collection;

import org.bukkit.help.HelpTopic;
import org.bukkit.help.IndexHelpTopic;

public class BukkitCommandIndexHelpTopic extends IndexHelpTopic {

    BukkitCommandIndexHelpTopic(String owner, Collection<HelpTopic> topics) {
        super(owner, "All commands for " + owner + ".", null, topics);
    }

}
