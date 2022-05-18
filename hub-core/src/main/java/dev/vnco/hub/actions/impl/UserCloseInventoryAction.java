package dev.vnco.hub.actions.impl;

import dev.vnco.hub.actions.ActionInterface;
import dev.vnco.hub.user.User;

public class UserCloseInventoryAction implements ActionInterface {

    @Override
    public void run(User user, String regex) {
        user.getAsPlayer().closeInventory();
    }
}
