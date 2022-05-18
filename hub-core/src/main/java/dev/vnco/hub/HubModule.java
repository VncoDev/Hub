package dev.vnco.hub;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dev.henko.message.api.Messenger;
import dev.vnco.hub.actions.ActionHandler;
import dev.vnco.hub.api.cache.Cache;
import dev.vnco.hub.api.cache.local.LocalObjectsCache;
import dev.vnco.hub.api.message.BuildMessage;
import dev.vnco.hub.api.model.Service;
import dev.vnco.hub.api.storage.JsonStorage;
import dev.vnco.hub.api.storage.Storage;
import dev.vnco.hub.command.api.CommandHandler;
import dev.vnco.hub.command.api.bukkit.BukkitCommandHandler;
import dev.vnco.hub.hotbar.HotbarItem;
import dev.vnco.hub.message.BuildMessageProvider;
import dev.vnco.hub.message.MessengerProvider;
import dev.vnco.hub.scoreboard.ScoreboardHandler;
import dev.vnco.hub.scoreboard.fast.FastBoard;
import dev.vnco.hub.server.Server;
import dev.vnco.hub.services.*;
import dev.vnco.hub.spawn.SpawnHandler;
import dev.vnco.hub.user.User;
import dev.vnco.menu.Menu;
import lombok.AllArgsConstructor;
import me.yushust.inject.AbstractModule;
import me.yushust.inject.key.TypeReference;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@AllArgsConstructor
public class HubModule extends AbstractModule {

    private final Hub hub;

    @Override
    protected void configure() {
        bind(Hub.class).toInstance(hub);

        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .serializeNulls()
                .enableComplexMapKeySerialization().create();

        bind(BuildMessage.class).toProvider(new BuildMessageProvider()).singleton();
        bind(new TypeReference<Messenger<String>>(){}).toProvider(new MessengerProvider()).singleton();

        bind(new TypeReference<Cache<UUID, User>>(){}).toInstance(new LocalObjectsCache<>());
        bind(new TypeReference<Storage<User>>(){}).toInstance(new JsonStorage<>(gson, User.class, hub));

        bind(new TypeReference<Cache<Integer, HotbarItem>>(){}).toInstance(new LocalObjectsCache<>());
        bind(new TypeReference<Cache<String, Menu>>(){}).toInstance(new LocalObjectsCache<>());
        bind(new TypeReference<Cache<UUID, FastBoard>>(){}).toInstance(new LocalObjectsCache<>());

        bind(ActionHandler.class).singleton();
        bind(CommandHandler.class).toInstance(new BukkitCommandHandler(hub));

        bind(SpawnHandler.class).singleton();

        bind(new TypeReference<Cache<String, Server>>(){}).toInstance(new LocalObjectsCache<>());

        bind(ScoreboardHandler.class).singleton();

        multibind(Service.class)
                .asCollection(Set.class, HashSet::new)

                .to(ListenerService.class)
                .to(CommandService.class)
                .to(MenuService.class)
                .to(HotbarService.class)
                .to(ScoreboardService.class)
                .to(ServerService.class)

                .singleton();
    }
}
