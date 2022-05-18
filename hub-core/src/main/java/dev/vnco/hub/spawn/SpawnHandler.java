package dev.vnco.hub.spawn;

import dev.henko.message.bukkit.source.YamlSource;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.jetbrains.annotations.Nullable;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

@Singleton
public class SpawnHandler {

    @Inject @Named("spawn")
    private YamlSource spawn;

    public @Nullable Location getSpawnLocation() {
        return new Location(

                Bukkit.getWorld("spawn.world-name"),
                spawn.getDouble("spawn.x"),
                spawn.getDouble("spawn.y"),
                spawn.getDouble("spawn.z"),
                (float) spawn.getDouble("spawn.yaw"),
                (float) spawn.getDouble("spawn.pitch")

        );
    }

    public void setSpawnLocation(Location location) {
        spawn.set("spawn.world-name", location.getWorld().getName());
        spawn.set("spawn.x", location.getX());
        spawn.set("spawn.y", location.getY());
        spawn.set("spawn.z", location.getZ());
        spawn.set("spawn.yaw", location.getYaw());
        spawn.set("spawn.pitch", location.getPitch());
    }
}
