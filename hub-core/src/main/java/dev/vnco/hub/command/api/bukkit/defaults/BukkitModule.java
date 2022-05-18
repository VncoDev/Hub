package dev.vnco.hub.command.api.bukkit.defaults;

import dev.vnco.hub.command.api.bukkit.defaults.color.ChatColorModule;
import dev.vnco.hub.command.api.bukkit.defaults.gamemode.GamemodeModule;
import dev.vnco.hub.command.api.bukkit.defaults.player.PlayerModule;
import dev.vnco.hub.command.api.bukkit.defaults.player.offline.OfflinePlayerModule;
import dev.vnco.hub.command.api.bukkit.defaults.sender.SenderModule;
import dev.vnco.hub.command.api.defaults.DefaultModule;
import org.bukkit.Server;

import lombok.AllArgsConstructor;
import dev.vnco.hub.command.api.AbstractModule;

@AllArgsConstructor
public class BukkitModule extends AbstractModule {

    private Server server;

    @Override
    protected void configure() {
        install(new DefaultModule());
        install(new ChatColorModule());
        install(new GamemodeModule());
        install(new OfflinePlayerModule(server));
        install(new PlayerModule(server));
        install(new SenderModule());
    }
}
