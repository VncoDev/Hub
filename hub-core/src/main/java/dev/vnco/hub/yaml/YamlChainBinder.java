package dev.vnco.hub.yaml;

import dev.henko.message.bukkit.source.YamlSource;
import me.yushust.inject.Module;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Map;

public class YamlChainBinder {

    private final JavaPlugin plugin;
    private final Map<String, YamlSource> yamlSourceMap;

    public YamlChainBinder(JavaPlugin javaPlugin) {
        plugin = javaPlugin;
        yamlSourceMap = new HashMap<>();
    }

    public YamlChainBinder append(String name) {
        yamlSourceMap.put(name, new YamlSource(plugin, name));
        return this;
    }

    public Module toModule() {
        return binder -> yamlSourceMap.forEach((name, yamlSource) -> binder.bind(YamlSource.class)
                .named(name).toInstance(yamlSource));
    }

}
