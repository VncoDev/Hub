package dev.vnco.hub;

import dev.vnco.hub.services.HubService;
import me.yushust.inject.Injector;
import org.bukkit.plugin.java.JavaPlugin;

import javax.inject.Inject;

public class Hub extends JavaPlugin {

    @Inject
    private HubService hubService;

    @Override
    public void onEnable() {
        Injector.create(new HubModule(this)).injectMembers(this);

        hubService.start();
    }

    @Override
    public void onDisable() {
        hubService.stop();
    }

}
