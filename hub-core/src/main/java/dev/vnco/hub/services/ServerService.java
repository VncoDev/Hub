package dev.vnco.hub.services;

import com.pequla.server.ping.Players;
import com.pequla.server.ping.ServerPing;
import com.pequla.server.ping.StatusResponse;
import dev.henko.message.bukkit.source.YamlSource;
import dev.vnco.hub.api.cache.Cache;
import dev.vnco.hub.api.model.Service;
import dev.vnco.hub.server.Server;
import dev.vnco.hub.utils.Task;
import lombok.SneakyThrows;
import me.yushust.inject.InjectAll;
import me.yushust.inject.InjectIgnore;
import org.bukkit.configuration.ConfigurationSection;

import javax.inject.Named;
import javax.inject.Singleton;
import java.net.InetSocketAddress;
import java.util.concurrent.TimeUnit;

@InjectAll @Singleton
public class ServerService implements Service {

    @Named("config")
    private YamlSource config;

    private Cache<String, Server> serverCache;

    @InjectIgnore
    private Task task;

    @Override
    public void start() {
        ConfigurationSection section = config.getConfigurationSection("server-config.list");

        if (section == null) {
            return;
        }

        for (String key : section.getKeys(false)) {
            Server server = new Server();

            server.setName(key);

            String[] split = section.getString(key).split(":");

            server.setAddress(split[0]);
            server.setPort(Integer.parseInt(split[1]));

            serverCache.add(key, server);
        }

        task = new Task(1, 1L, TimeUnit.SECONDS) {

            @SneakyThrows
            @Override
            public void run() {
                for (Server server : serverCache.get().values()) {
                    ServerPing serverPing = new ServerPing(new InetSocketAddress(server.getAddress(), server.getPort()));
                    StatusResponse response = serverPing.fetchData();

                    if (response == null) {
                        continue;
                    }

                    try {
                        Players players = response.getPlayers();

                        server.setOnlinePlayers(players.getOnline());
                        server.setMaxPlayers(players.getMax());
                        server.setOnline(true);
                    } catch (Exception exception) {
                        server.setOnlinePlayers(0);
                        server.setMaxPlayers(0);
                        server.setOnline(false);
                    }
                }
            }
        };
    }

    @Override
    public void stop() {
        serverCache.get().clear();
        task.cancel();
    }

    @Override
    public int getPriority() {
        return 6;
    }
}
