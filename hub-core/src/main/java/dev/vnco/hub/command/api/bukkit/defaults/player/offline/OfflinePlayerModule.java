package dev.vnco.hub.command.api.bukkit.defaults.player.offline;

import org.bukkit.OfflinePlayer;
import org.bukkit.Server;

import lombok.AllArgsConstructor;
import dev.vnco.hub.command.api.AbstractModule;

@AllArgsConstructor
public class OfflinePlayerModule extends AbstractModule {

    private Server server;

    @Override
    protected void configure() {
        bind(OfflinePlayer.class).toProvider(new OfflinePlayerProvider(server));
    }
}
