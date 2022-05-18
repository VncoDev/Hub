package dev.vnco.hub.services;

import dev.vnco.hub.Hub;
import dev.vnco.hub.api.model.Service;
import me.yushust.inject.InjectAll;
import org.bukkit.Bukkit;

import java.util.Collection;
import java.util.Comparator;
import java.util.Set;
import java.util.stream.Collectors;

@InjectAll
public class HubService implements Service {

    private Hub hub;
    private Set<Service> services;

    @Override
    public void start() {
        for (Service service : getOrderedServices()) {
            service.start();
        }

        Bukkit.getMessenger().registerOutgoingPluginChannel(hub, "BungeeCord");
    }

    @Override
    public void stop() {
        for (Service service : services) {
            service.stop();
        }
    }

    private Collection<Service> getOrderedServices() {
        return services.stream()
                .sorted(Comparator.comparingInt(Service::getPriority)).collect(Collectors.toList());
    }

}
