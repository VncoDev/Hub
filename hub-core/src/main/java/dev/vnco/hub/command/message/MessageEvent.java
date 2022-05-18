package dev.vnco.hub.command.message;

import dev.vnco.hub.user.User;
import lombok.Getter;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

@Getter
public class MessageEvent extends Event {

    @Getter
    private final static HandlerList handlerList = new HandlerList();

    private final User user, target;
    private final String message;

    public MessageEvent(User user, User target, String message) {
        this.user = user;
        this.target = target;
        this.message = message;
    }

    @Override
    public HandlerList getHandlers() {
        return handlerList;
    }
}
