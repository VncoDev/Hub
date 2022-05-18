package dev.vnco.hub.command.message;

import dev.henko.message.api.Messenger;
import dev.vnco.hub.api.storage.Storage;
import dev.vnco.hub.command.api.annotation.Command;
import dev.vnco.hub.command.api.annotation.Sender;
import dev.vnco.hub.command.api.parameter.Param;
import dev.vnco.hub.user.User;
import me.yushust.inject.InjectAll;
import org.bukkit.entity.Player;

@InjectAll
public class MessageCommand {

    private Storage<User> userStorage;
    private Messenger<String> messenger;

    @Command(aliases = {"message", "m", "w", "whisper", "tell", "pm", "privatemessage"}, async = true)
    public void onMessageCommand(@Sender User user, @Param(name = "target") User target, @Param(name = "message", wildcard = true) String message) {
        String name = target.getName();

        if (!user.getLastConverser().equals(name)) {
            user.setLastConverser(name);
        }

        new MessageEvent(user, target, message);
    }

    @Command(aliases = {"reply", "response", "r"}, async = true)
    public void onReplyCommand(@Sender User user, @Param(name = "message", wildcard = true) String message) {
        Player lastConverser = user.getLastConverserAsPlayer();

        if (lastConverser == null) {
            messenger.sendReplacing(user, "user-not-found", "%target_name%", user.getLastConverser());
            return;
        }

        new MessageEvent(user, userStorage.get(lastConverser.getUniqueId().toString()), message);
    }

}
