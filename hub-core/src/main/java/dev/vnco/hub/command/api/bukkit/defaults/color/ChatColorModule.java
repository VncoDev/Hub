package dev.vnco.hub.command.api.bukkit.defaults.color;

import org.bukkit.ChatColor;

import dev.vnco.hub.command.api.AbstractModule;

public class ChatColorModule extends AbstractModule {

	@Override
	protected void configure() {
		bind(ChatColor.class).toProvider(new ChatColorProvider());
	}
}
