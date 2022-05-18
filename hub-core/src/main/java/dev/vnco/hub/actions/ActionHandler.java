package dev.vnco.hub.actions;

import dev.vnco.hub.actions.impl.*;
import dev.vnco.hub.api.cache.Cache;
import dev.vnco.hub.user.User;
import me.yushust.inject.InjectAll;
import org.apache.commons.lang.Validate;

import java.util.List;
import java.util.Objects;

@InjectAll
public class ActionHandler {

    private Cache<String, ActionInterface> actionCache;

    private UserSendMessageAction userSendMessageAction;
    private UserCloseInventoryAction userCloseInventoryAction;
    private UserConnectServerAction userConnectServerAction;
    private UserOpenMenuAction userOpenMenuAction;
    private UserPerformCommand userPerformCommand;

    public ActionHandler() {
        setupAllActions();
    }

    private void setupAllActions() {
        registerActions(

                "user-send-message",
                userSendMessageAction,
                "user-close-inventory",
                userCloseInventoryAction,
                "user-connect-server",
                userConnectServerAction,
                "user-open-menu",
                userOpenMenuAction,
                "user-perform-command",
                userPerformCommand

        );
    }

    /**
     * Original credits to Camilo
     */
    private void registerActions(Object... actions) {
        Validate.isTrue(actions.length % 2 == 0, "Actions array must be pair!");

        for (int i = 0; i < actions.length; i++) {
            Object name = actions[i];
            Validate.isTrue(name instanceof String, "The element at index " + i + " found in the command array must be a string!");

            i++;

            Object action = actions[i];
            Validate.isTrue(action instanceof ActionInterface, "The element at index " + i + " found in the command array must be a action!");

            actionCache.add((String) name, (ActionInterface) action);
        }
    }

    public void runActionList(User user, List<String> actions) {
        for (String stringAction : actions) {
            String[] split = stringAction.split(":");
            ActionInterface action = actionCache.get(split[0]);
            
            Validate.notNull(action, "Action can't be null");

            action.run(user, split[1]);
        }
    }
}
