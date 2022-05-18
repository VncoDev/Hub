package dev.vnco.hub.listeners;

import dev.henko.message.api.Messenger;
import dev.vnco.hub.command.message.MessageEvent;
import dev.vnco.hub.user.User;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import javax.inject.Inject;

public class MessageListener implements Listener {

    @Inject
    private Messenger<String> messenger;

    @EventHandler
    public void onMessage(MessageEvent event) {
        User user = event.getUser(), target = event.getTarget();

        messenger.sendReplacing(user, "chat-format.to", "%target_name%", target.getName());
        messenger.sendReplacing(target, "chat-format.from", "%target_name%", user.getName());
    }

}
