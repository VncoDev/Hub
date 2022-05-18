package dev.vnco.hub.command;

import dev.henko.message.api.Messenger;
import dev.vnco.hub.command.api.annotation.Command;
import dev.vnco.hub.command.api.annotation.Sender;
import dev.vnco.hub.spawn.SpawnHandler;
import me.yushust.inject.InjectAll;
import org.bukkit.Location;
import org.bukkit.entity.Player;

@InjectAll
public class SpawnCommand {

    private SpawnHandler spawnHandler;
    private Messenger<String> messenger;

    @Command(aliases = "spawn", permission = "spawn.command")
    public void onSpawnCommand(@Sender Player player) {
        Location location = spawnHandler.getSpawnLocation();

        if (location == null) {
            messenger.send(player, "spawn.not-found");
            return;
        }

        player.teleport(location);
        messenger.send(player, "spawn.teleported");
    }

    @Command(aliases = "setspawn", permission = "setspawn.command")
    public void onSetSpawnCommand(@Sender Player player) {
        spawnHandler.setSpawnLocation(player.getLocation());
        messenger.send(player, "spawn.set");
    }

}
