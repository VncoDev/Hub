package dev.vnco.hub.services;

import dev.vnco.hub.Hub;
import dev.vnco.hub.api.model.Service;
import dev.vnco.hub.hotbar.HotbarListener;
import dev.vnco.hub.listeners.MessageListener;
import dev.vnco.hub.listeners.PlayerListener;
import dev.vnco.hub.user.UserListener;
import me.yushust.inject.InjectAll;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;

@InjectAll
public class ListenerService implements Service {

    private Hub hub;

    private UserListener userListener;
    private MessageListener messageListener;
    private HotbarListener hotbarListener;
    private PlayerListener playerListener;

    @Override
    public void start() {
        registerListeners(

                userListener,
                messageListener,
                hotbarListener,
                playerListener

        );
    }

    private void registerListeners(Listener... listeners) {
        for (Listener listener : listeners) {
            Bukkit.getPluginManager().registerEvents(listener, hub);
        }
    }

    @Override
    public int getPriority() {
        return 1;
    }
}
