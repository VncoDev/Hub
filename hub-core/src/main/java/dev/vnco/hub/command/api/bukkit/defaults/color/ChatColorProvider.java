package dev.vnco.hub.command.api.bukkit.defaults.color;

import java.util.List;
import java.util.Map;

import dev.vnco.hub.command.api.exception.ProviderException;
import dev.vnco.hub.command.api.parameter.Parameter;
import org.bukkit.ChatColor;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import dev.vnco.hub.command.api.CommandSource;
import dev.vnco.hub.command.api.Provider;

public class ChatColorProvider implements Provider<ChatColor> {

	private Map<String, ChatColor> colorMap = Maps.newConcurrentMap();

	public ChatColorProvider() {
		colorMap.put("pink", ChatColor.LIGHT_PURPLE);
		colorMap.put("orange", ChatColor.GOLD);
		colorMap.put("purple", ChatColor.DARK_PURPLE);

		for (ChatColor chatColor : ChatColor.values()) {
			colorMap.put(chatColor.name().toLowerCase().replace("_", ""), chatColor);
		}
		
	}
	
	@Override
	public ChatColor provide(CommandSource<?> source, String string, Parameter<ChatColor> parameter) {
		if(!colorMap.containsKey(string.toLowerCase())) {
			throw new ProviderException("No color named '%s' found.", string.toLowerCase());
		}
		
		return (getColorFromName(string));
	}
	
	@Override
	public List<String> suggest(CommandSource<?> source, Parameter<ChatColor> parameter) {
		List<String> names = Lists.newArrayList();
		names.addAll(colorMap.keySet());
		
		return (names);
	}
	
	private ChatColor getColorFromName(String name) {
		if (colorMap.containsKey(name.trim().toLowerCase())) {
			return colorMap.get(name.trim().toLowerCase());
		}

		ChatColor color;

		try {
			color = ChatColor.valueOf(name.toUpperCase().replace(" ", "_"));
		} catch (Exception e) {
			return null;
		}

		return color;
	}
}
