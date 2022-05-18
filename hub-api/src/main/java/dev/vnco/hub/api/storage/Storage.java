package dev.vnco.hub.api.storage;

import dev.vnco.hub.api.model.Savable;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.Optional;
public interface Storage<O extends Savable<?>> {

    void save(O o);

    @Nullable O get(String id);

    default Optional<O> find(String id) {
        return Optional.ofNullable(get(id));
    }

    Collection<O> values();

}
