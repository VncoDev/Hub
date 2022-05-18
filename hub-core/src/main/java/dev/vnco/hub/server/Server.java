package dev.vnco.hub.server;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import dev.vnco.hub.Hub;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

@Getter @Setter
public class Server {

    private String name, address;
    private boolean online;
    private int port, onlinePlayers, maxPlayers;

    public void send(Player player) {
        ByteArrayDataOutput output = ByteStreams.newDataOutput();

        output.writeUTF("Connect");
        output.writeUTF(name);

        player.sendPluginMessage(JavaPlugin.getPlugin(Hub.class), "BungeeCord", output.toByteArray());
    }

}
