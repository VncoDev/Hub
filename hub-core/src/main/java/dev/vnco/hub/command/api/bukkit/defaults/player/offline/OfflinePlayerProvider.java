package dev.vnco.hub.command.api.bukkit.defaults.player.offline;

import java.util.List;

import dev.vnco.hub.command.api.exception.ProviderException;
import dev.vnco.hub.command.api.parameter.Parameter;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.Server;

import com.google.common.collect.Lists;

import lombok.AllArgsConstructor;
import dev.vnco.hub.command.api.CommandSource;
import dev.vnco.hub.command.api.Provider;

@AllArgsConstructor
public class OfflinePlayerProvider implements Provider<OfflinePlayer> {

    private Server server;

    @Override
    public OfflinePlayer provide(CommandSource<?> source, String string, Parameter<OfflinePlayer> parameter) {
    	OfflinePlayer offlinePlayer = server.getOfflinePlayer(string);

        if (offlinePlayer == null) {
            throw new ProviderException("No offline player named '%s' found.", string);
        }

        // Make sure the player is visible to the sender.
        if (!offlinePlayer.hasPlayedBefore()) {
            throw new ProviderException("No offline player named '%s' found.", string);
        }

        return (offlinePlayer);
    }

    @Override
    public List<String> suggest(CommandSource<?> source, Parameter<OfflinePlayer> parameter) {
    	List<String> names = Lists.newArrayList();
    	
    	for(OfflinePlayer offlinePlayer : Bukkit.getOfflinePlayers()) {
    		names.add(offlinePlayer.getName());
    	}
    	
        return (names);
    }
}
