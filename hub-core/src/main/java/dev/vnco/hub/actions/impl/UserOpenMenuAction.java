package dev.vnco.hub.actions.impl;

import dev.vnco.hub.actions.ActionInterface;
import dev.vnco.hub.api.cache.Cache;
import dev.vnco.hub.user.User;
import dev.vnco.menu.Menu;

import javax.inject.Inject;
import java.util.Objects;

public class UserOpenMenuAction implements ActionInterface {

    @Inject
    private Cache<String, Menu> menuCache;

    @Override
    public void run(User user, String regex) {
        Menu menu = menuCache.get(regex);
        Objects.requireNonNull(menu, "Menu can't be null").openMenu(user.getAsPlayer());
    }
}
