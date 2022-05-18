package dev.vnco.hub.scoreboard;

import dev.vnco.hub.api.cache.Cache;
import dev.vnco.hub.scoreboard.fast.FastBoard;
import org.bukkit.entity.Player;

import javax.inject.Inject;
import java.util.UUID;

public class ScoreboardHandler {

    @Inject
    private Cache<UUID, FastBoard> fastBoardCache;

    public void add(Player player) {
        fastBoardCache.add(player.getUniqueId(), new FastBoard(player));
    }

    public void remove(UUID id) {
        FastBoard fastBoard = fastBoardCache.get(id);

        if (fastBoard == null) {
            return;
        }

        fastBoard.delete();
        fastBoardCache.remove(id);
    }
}
