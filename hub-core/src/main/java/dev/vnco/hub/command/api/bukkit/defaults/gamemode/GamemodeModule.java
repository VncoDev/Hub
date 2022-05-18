package dev.vnco.hub.command.api.bukkit.defaults.gamemode;

import org.bukkit.GameMode;

import dev.vnco.hub.command.api.AbstractModule;

public class GamemodeModule extends AbstractModule {
	
	@Override
	protected void configure() {
		bind(GameMode.class).toProvider(new GamemodeProvider());
	}
}
