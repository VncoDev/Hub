package dev.vnco.hub.actions.impl;

import dev.vnco.hub.actions.ActionInterface;
import dev.vnco.hub.api.message.BuildMessage;
import dev.vnco.hub.user.User;
import dev.vnco.hub.utils.ChatUtil;
import org.bukkit.entity.Player;

import javax.inject.Inject;

public class UserSendMessageAction implements ActionInterface {

    @Inject
    private BuildMessage buildMessage;

    @Override
    public void run(User user, String regex) {
        Player player = user.getAsPlayer();
        ChatUtil.toSender(player, buildMessage.of(player, regex));
    }
}
