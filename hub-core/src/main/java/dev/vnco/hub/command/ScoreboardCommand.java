package dev.vnco.hub.command;

import dev.henko.message.api.Messenger;
import dev.vnco.hub.command.api.annotation.Command;
import dev.vnco.hub.command.api.annotation.Sender;
import dev.vnco.hub.scoreboard.ScoreboardHandler;
import dev.vnco.hub.user.User;
import me.yushust.inject.InjectAll;

@InjectAll
public class ScoreboardCommand {

    private Messenger<String> messenger;
    private ScoreboardHandler scoreboardHandler;

    @Command(aliases = {"togglescoreboard", "togglesb", "tsb"})
    public void onScoreboardCommand(@Sender User user) {
        if (user.isScoreboardEnabled()) {
            user.setScoreboardEnabled(false);
            scoreboardHandler.remove(user.getId());
            messenger.send(user, "scoreboard.disabled");
        } else {
            user.setScoreboardEnabled(true);
            scoreboardHandler.add(user.getAsPlayer());
            messenger.send(user, "scoreboard.enabled");
        }
    }

}
