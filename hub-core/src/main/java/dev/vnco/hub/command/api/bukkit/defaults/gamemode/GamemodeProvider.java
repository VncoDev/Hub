package dev.vnco.hub.command.api.bukkit.defaults.gamemode;

import java.util.List;
import java.util.Map;

import dev.vnco.hub.command.api.exception.ProviderException;
import dev.vnco.hub.command.api.parameter.Parameter;
import org.bukkit.GameMode;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import dev.vnco.hub.command.api.CommandSource;
import dev.vnco.hub.command.api.Provider;

public class GamemodeProvider implements Provider<GameMode> {

	private Map<String, GameMode> gamemodes = Maps.newConcurrentMap();

	public GamemodeProvider() {
		gamemodes.put("0", GameMode.SURVIVAL);
		gamemodes.put("s", GameMode.SURVIVAL);
		gamemodes.put("surv", GameMode.SURVIVAL);
		gamemodes.put("survival", GameMode.SURVIVAL);

		gamemodes.put("1", GameMode.CREATIVE);
		gamemodes.put("c", GameMode.CREATIVE);
		gamemodes.put("creat", GameMode.CREATIVE);
		gamemodes.put("creative", GameMode.CREATIVE);
	}
	
	@Override
	public GameMode provide(CommandSource<?> source, String string, Parameter<GameMode> parameter) {
		if(!gamemodes.containsKey(string.toLowerCase())) {
			throw new ProviderException("No gamemode named '%s' found.", string.toLowerCase());
		}
		
		return gamemodes.get(string.toLowerCase());
	}

	@Override
	public List<String> suggest(CommandSource<?> source, Parameter<GameMode> parameter) {
		List<String> names = Lists.newArrayList();
		
		for(GameMode gamemode : GameMode.values()) {
			names.add(gamemode.name().toLowerCase());
		}
		
		return (names);
	}
}
