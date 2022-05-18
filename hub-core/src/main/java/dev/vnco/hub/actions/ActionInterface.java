package dev.vnco.hub.actions;

import dev.vnco.hub.user.User;

@FunctionalInterface
public interface ActionInterface {

    void run(User user, String regex);

}
