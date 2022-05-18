package dev.vnco.hub.actions.impl;

import dev.vnco.hub.actions.ActionInterface;
import dev.vnco.hub.api.cache.Cache;
import dev.vnco.hub.server.Server;
import dev.vnco.hub.user.User;

import javax.inject.Inject;
import java.util.Objects;

public class UserConnectServerAction implements ActionInterface {

    @Inject
    private Cache<String, Server> serverCache;

    @Override
    public void run(User user, String regex) {
        Server server = serverCache.get(regex);
        Objects.requireNonNull(server, "Server can't be null").send(user.getAsPlayer());
    }
}
