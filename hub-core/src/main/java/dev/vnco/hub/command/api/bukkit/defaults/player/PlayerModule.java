package dev.vnco.hub.command.api.bukkit.defaults.player;

import org.bukkit.Server;
import org.bukkit.entity.Player;

import lombok.AllArgsConstructor;
import dev.vnco.hub.command.api.AbstractModule;

@AllArgsConstructor
public class PlayerModule extends AbstractModule {

    private Server server;

    @Override
    protected void configure() {
        bind(Player.class).toProvider(new PlayerProvider(server));
    }
}
