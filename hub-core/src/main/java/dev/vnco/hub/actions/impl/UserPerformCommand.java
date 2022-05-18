package dev.vnco.hub.actions.impl;

import dev.vnco.hub.actions.ActionInterface;
import dev.vnco.hub.api.message.BuildMessage;
import dev.vnco.hub.user.User;
import org.bukkit.entity.Player;

import javax.inject.Inject;

public class UserPerformCommand implements ActionInterface {

    @Inject
    private BuildMessage buildMessage;

    @Override
    public void run(User user, String regex) {
        Player player = user.getAsPlayer();
        player.performCommand(buildMessage.of(player, regex));
    }
}
