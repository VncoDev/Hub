package dev.vnco.hub.user;

import dev.vnco.hub.api.model.Savable;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.beans.ConstructorProperties;
import java.util.UUID;

@Getter @Setter
public class User implements Savable<UUID> {

    private final UUID id;
    private String name, lastConverser;

    private boolean scoreboardEnabled;

    @ConstructorProperties("playerId")
    public User(UUID playerId, String playerName) {
        id = playerId;
        name = playerName;

        scoreboardEnabled = true;
    }

    public Player getLastConverserAsPlayer() {
        return Bukkit.getPlayer(lastConverser);
    }

    public Player getAsPlayer() {
        return Bukkit.getPlayer(id);
    }

}
