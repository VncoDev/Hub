package dev.vnco.hub.hotbar;

import dev.vnco.hub.actions.ActionHandler;
import dev.vnco.hub.api.cache.Cache;
import dev.vnco.hub.api.message.BuildMessage;
import dev.vnco.hub.api.storage.Storage;
import dev.vnco.hub.user.User;
import me.yushust.inject.InjectAll;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.Objects;

@InjectAll
public class HotbarListener implements Listener {

    private ActionHandler actionHandler;

    private Storage<User> userStorage;
    private Cache<Integer, HotbarItem> hotbarItemCache;

    private BuildMessage buildMessage;

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        event.setCancelled(true);

        Player player = event.getPlayer();
        Action action = event.getAction();

        if (action == Action.RIGHT_CLICK_BLOCK || action == Action.RIGHT_CLICK_AIR) {
            for (HotbarItem hotbarItem : hotbarItemCache.get().values()) {

                if (!Objects.equals(event.getItem(), hotbarItem.getItemStack())) {
                    continue;
                }

                if (!player.hasPermission(hotbarItem.getPermission())) {
                    continue;
                }

                actionHandler.runActionList(userStorage.get(player.getUniqueId().toString()), buildMessage.of(player, hotbarItem.getActions()));
            }
        }
    }

}
