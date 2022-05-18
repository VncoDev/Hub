package dev.vnco.hub.scoreboard;

import dev.vnco.hub.api.cache.Cache;
import dev.vnco.hub.api.storage.Storage;
import dev.vnco.hub.scoreboard.fast.FastBoard;
import dev.vnco.hub.user.User;
import me.yushust.inject.InjectAll;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.UUID;

@InjectAll
public class ScoreboardListener implements Listener {

    private Storage<User> userStorage;
    private ScoreboardHandler scoreboardHandler;

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        UUID id = event.getPlayer().getUniqueId();

        userStorage.find(id.toString()).ifPresent(user -> {

            if (user.isScoreboardEnabled()) {
                scoreboardHandler.add(user.getAsPlayer());
            }

        });
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        scoreboardHandler.remove(event.getPlayer().getUniqueId());
    }
}
