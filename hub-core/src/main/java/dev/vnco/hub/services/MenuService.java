package dev.vnco.hub.services;

import dev.henko.message.bukkit.source.YamlSource;
import dev.vnco.hub.Hub;
import dev.vnco.hub.actions.ActionHandler;
import dev.vnco.hub.api.cache.Cache;
import dev.vnco.hub.api.message.BuildMessage;
import dev.vnco.hub.api.model.Service;
import dev.vnco.hub.api.storage.Storage;
import dev.vnco.hub.user.User;
import dev.vnco.hub.utils.ItemCreator;
import dev.vnco.hub.utils.ItemUtils;
import dev.vnco.menu.Menu;
import dev.vnco.menu.MenuHandler;
import dev.vnco.menu.button.Button;
import dev.vnco.menu.type.MenuType;
import me.yushust.inject.InjectAll;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import javax.inject.Named;
import javax.inject.Singleton;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@InjectAll @Singleton
public class MenuService implements Service {

    private Hub hub;

    private ActionHandler actionHandler;

    @Named("menus")
    private YamlSource menus;

    private BuildMessage buildMessage;

    private Storage<User> userStorage;
    private Cache<String, Menu> menuCache;

    @Override
    public void start() {
        new MenuHandler(hub);

        ConfigurationSection menuSection = menus.getConfigurationSection("menus");

        if (menuSection == null) {
            return;
        }

        for (String menuKey : menuSection.getKeys(false)) {
            menuCache.add(menuKey, new Menu(

                    menuSection.getString(menuKey + ".title"),
                    MenuType.valueOf(menuSection.getString(menuKey + ".type")),
                    menuSection.getInt(menuKey + ".size")

                    ) {

                @Override
                public Set<Button> getButtons(Player player) {
                    Set<Button> buttons = new HashSet<>();

                    ConfigurationSection buttonSection = menus.getConfigurationSection("menus." + menuKey + ".buttons");

                    if (buttonSection == null) {
                        return Collections.emptySet();
                    }

                    for (String buttonKey : buttonSection.getKeys(false)) {
                        buttons.add(new Button(Integer.parseInt(buttonKey)) {

                            @Override
                            public void onClick(InventoryClickEvent inventoryClickEvent) {
                                if (!player.hasPermission(buttonSection.getString(buttonKey + ".settings.permissions"))) {
                                    return;
                                }

                                actionHandler.runActionList(

                                        userStorage.get(player.getUniqueId().toString()),
                                        buildMessage.of(player, buttonSection.getStringList(buttonKey + ".settings.actions"))

                                );
                            }

                            @Override
                            public ItemStack getButtonItem() {
                                return new ItemCreator(ItemUtils.parse(buttonSection.getString(buttonKey + ".material-id")))
                                        .setDisplayName(buildMessage.of(player, buttonSection.getString(buttonKey + ".name")))
                                        .setLore(buildMessage.of(player, buttonSection.getStringList(buttonKey + ".lore")))
                                        .setGlow(buttonSection.getBoolean(buttonKey + ".glow"))
                                        .setAmount(buttonSection.getInt(buttonKey + ".amount"))
                                        .build();
                            }
                        });

                    }

                    return buttons;
                }

            });
        }
    }

    @Override
    public void stop() {
        menuCache.get().clear();
    }

    @Override
    public int getPriority() {
        return 2;
    }
}
