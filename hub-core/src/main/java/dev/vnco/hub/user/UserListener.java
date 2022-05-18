package dev.vnco.hub.user;

import dev.vnco.hub.api.cache.Cache;
import dev.vnco.hub.api.storage.Storage;
import me.yushust.inject.InjectAll;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.server.PluginDisableEvent;

import java.util.UUID;

@InjectAll
public class UserListener implements Listener {

    private Cache<UUID, User> userCache;
    private Storage<User> userStorage;

    @EventHandler
    public void onAsyncPlayerPreLogin(AsyncPlayerPreLoginEvent event) {
        UUID id = event.getUniqueId();
        String name = event.getName();

        User user = userStorage.find(id.toString()).orElseGet(() -> new User(id, name));
        userCache.add(id, user);
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        UUID id = event.getPlayer().getUniqueId();
        userStorage.find(id.toString()).ifPresent(userStorage::save);
        userCache.remove(id);
    }

    @EventHandler
    public void onPluginDisable(PluginDisableEvent event) {
        userStorage.values().forEach(userStorage::save);
        userCache.get().clear();
    }

}
