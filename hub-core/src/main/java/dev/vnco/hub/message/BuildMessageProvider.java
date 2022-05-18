package dev.vnco.hub.message;

import dev.henko.message.bukkit.source.YamlSource;
import dev.vnco.hub.api.cache.Cache;
import dev.vnco.hub.api.message.BuildMessage;
import dev.vnco.hub.server.Server;
import me.clip.placeholderapi.PlaceholderAPI;
import me.yushust.inject.InjectAll;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import javax.inject.Named;
import javax.inject.Provider;
import javax.inject.Singleton;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@InjectAll @Singleton
public class BuildMessageProvider implements Provider<BuildMessage> {

    @Named("config")
    private YamlSource config;

    private Cache<String, Server> serverCache;

    @Override
    public BuildMessage get() {
        return new BuildMessage() {

            @Override
            public String of(Player player, String text) {
                Map<String, String> map = new HashMap<>();

                map.put("%user_name%", player.getName());

                int globalPlayers = Bukkit.getOnlinePlayers().size();

                for (Server server : serverCache.get().values()) {
                    globalPlayers += server.getOnlinePlayers();
                }

                map.put("%global_players%", String.valueOf(globalPlayers));

                matchAndExecute("server_online_players", text, s -> {
                    Server server = serverCache.get(s);
                    if (server != null) {
                        map.put("%server_online_players:" + s + "%", String.valueOf(server.getOnlinePlayers()));
                    }
                });

                matchAndExecute("server_max_players", text, s -> {
                    Server server = serverCache.get(s);
                    if (server != null) {
                        map.put("%server_max_players:" + s + "%", String.valueOf(server.getMaxPlayers()));
                    }
                });

                matchAndExecute("server_status", text, s -> {

                    Server server = serverCache.get(s);

                    Objects.requireNonNull(server, "Server can't be null");

                    String variable = "%server_status:" + s + "%";

                    if (server.isOnline()) {
                        map.put(variable, config.getString("server-config.online-status"));
                    } else {
                        map.put(variable, config.getString("server-config.offline-status"));
                    }
                    
                });

                if (Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI")) {
                    text = PlaceholderAPI.setPlaceholders(player, text);
                }

                return replace(map.entrySet(), text);
            }

        };
    }
}
