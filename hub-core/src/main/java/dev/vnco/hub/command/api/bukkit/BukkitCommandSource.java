package dev.vnco.hub.command.api.bukkit;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import dev.vnco.hub.command.api.CommandSource;

public class BukkitCommandSource extends CommandSource<CommandSender> {

	public BukkitCommandSource(CommandSender handle) {
        super(handle);
    }

    @Override
    public boolean hasPermission(String permission) {
        return getHandle().hasPermission(permission);
    }

    @Override
    public void message(String message) {
        getHandle().sendMessage(ChatColor.translateAlternateColorCodes('&', message));
    }
}
