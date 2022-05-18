package dev.vnco.hub.services;

import dev.henko.message.bukkit.source.YamlSource;
import dev.vnco.hub.actions.ActionInterface;
import dev.vnco.hub.api.cache.Cache;
import dev.vnco.hub.api.model.Service;
import dev.vnco.hub.hotbar.HotbarItem;
import dev.vnco.hub.utils.ItemCreator;
import dev.vnco.hub.utils.ItemUtils;
import me.yushust.inject.InjectAll;
import org.bukkit.configuration.ConfigurationSection;

import javax.inject.Named;
import javax.inject.Singleton;

@InjectAll @Singleton
public class HotbarService implements Service {

    @Named("hotbar")
    private YamlSource hotbar;

    private Cache<String, ActionInterface> actionCache;
    private Cache<Integer, HotbarItem> hotbarItemCache;

    @Override
    public void start() {
        ConfigurationSection section = hotbar.getConfigurationSection("hotbar-items");

        if (section == null) {
            return;
        }

        for (String key : section.getKeys(false)) {
            HotbarItem hotbarItem = new HotbarItem();

            hotbarItem.setItemStack(

                    new ItemCreator(ItemUtils.parse(section.getString(key + ".material-id")))
                            .setAmount(section.getInt(key + ".amount"))
                            .setDisplayName(section.getString(key + ".name"))
                            .setLore(section.getStringList(key + ".lore"))
                            .setGlow(section.getBoolean(key + ".glow"))
                            .build()

            );

            hotbarItem.setPermission(section.getString(key + ".settings.permission"));
            hotbarItem.setActions(section.getStringList(key + ".settings.actions"));

            hotbarItemCache.add(Integer.parseInt(key), hotbarItem);
        }
    }

    @Override
    public void stop() {
        hotbarItemCache.get().clear();
    }

    @Override
    public int getPriority() {
        return 3;
    }
}
