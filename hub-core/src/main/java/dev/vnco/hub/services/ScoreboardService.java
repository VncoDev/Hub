package dev.vnco.hub.services;

import dev.henko.message.bukkit.source.YamlSource;
import dev.vnco.hub.api.cache.Cache;
import dev.vnco.hub.api.message.BuildMessage;
import dev.vnco.hub.api.model.Service;
import dev.vnco.hub.scoreboard.fast.FastBoard;
import dev.vnco.hub.utils.Task;
import me.yushust.inject.InjectAll;
import me.yushust.inject.InjectIgnore;
import org.bukkit.entity.Player;

import javax.inject.Named;
import javax.inject.Singleton;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@InjectAll @Singleton
public class ScoreboardService implements Service {

    @Named("config")
    private YamlSource config;

    private BuildMessage buildMessage;
    private Cache<UUID, FastBoard> fastBoardCache;

    @InjectIgnore
    private Task task;

    @Override
    public void start() {
        task = new Task(2, 200L, TimeUnit.MILLISECONDS) {

            @Override
            public void run() {
                for (FastBoard fastBoard : fastBoardCache.get().values()) {
                    Player player = fastBoard.getPlayer();

                    List<String> list = new ArrayList<>();

                    fastBoard.updateTitle(buildMessage.of(player, config.getString("scoreboard.title")));

                    for (String string : config.getStringList("scoreboard.lines")) {
                        list.add(buildMessage.of(player, string));
                    }

                    fastBoard.updateLines(list);
                }
            }
        };
    }

    @Override
    public void stop() {
        fastBoardCache.get().clear();
        task.cancel();
    }

    @Override
    public int getPriority() {
        return 5;
    }
}
