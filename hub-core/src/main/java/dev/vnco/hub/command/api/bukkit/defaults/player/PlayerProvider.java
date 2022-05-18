package dev.vnco.hub.command.api.bukkit.defaults.player;

import java.util.List;

import dev.vnco.hub.command.api.exception.ProviderException;
import dev.vnco.hub.command.api.parameter.Parameter;
import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.google.common.collect.Lists;

import lombok.AllArgsConstructor;
import dev.vnco.hub.command.api.CommandSource;
import dev.vnco.hub.command.api.Provider;

@AllArgsConstructor
public class PlayerProvider implements Provider<Player> {

    private Server server;

    @Override
    public Player provide(CommandSource<?> source, String string, Parameter<Player> parameter) {
    	if(source.getHandle() instanceof Player &&  string.equalsIgnoreCase("self")) {
    		return ((Player) source.getHandle());
    	}
    	
        Player player = server.getPlayer(string);

        if (player == null) {
            throw new ProviderException("No player named '%s' found.", string);
        }

        // Make sure the player is visible to the sender.
        if (!canSee(player, (CommandSender) source.getHandle())) {
            throw new ProviderException("No player named '%s' found.", string);
        }

        return (player);
    }

    @Override
    public List<String> suggest(CommandSource<?> source, Parameter<Player> parameter) {
    	List<String> names = Lists.newArrayList();
    	
    	for(Player player : Bukkit.getOnlinePlayers()) {
    		if(canSee(player, (CommandSender) source.getHandle())) {
    			names.add(player.getName());
    		}
    	}
    	
        return (names);
    }

    private boolean canSee(Player player, CommandSender sender) {
        return !(sender instanceof Player) || ((Player) sender).canSee(player);
    }
}
