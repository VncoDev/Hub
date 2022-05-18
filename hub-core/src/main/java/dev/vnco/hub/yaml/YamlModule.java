package dev.vnco.hub.yaml;

import dev.vnco.hub.Hub;
import lombok.AllArgsConstructor;
import me.yushust.inject.AbstractModule;

@AllArgsConstructor
public class YamlModule extends AbstractModule {

    private final Hub hub;

    @Override
    protected void configure() {
        YamlChainBinder chainBinder = new YamlChainBinder(hub);

        chainBinder
                .append("config")
                .append("hotbar")
                .append("spawn")
                .append("menus");

        install(chainBinder.toModule());
    }
}
