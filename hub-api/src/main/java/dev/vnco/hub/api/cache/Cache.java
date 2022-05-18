package dev.vnco.hub.api.cache;

import java.util.Map;
import java.util.Optional;

public interface Cache<K, V> {

    Map<K, V> get();

    default Optional<V> find(K k) {
        return Optional.ofNullable(this.get().get(k));
    }

    default V get(K k) {
        return this.get().get(k);
    }

    default void add(K k, V v) {
        this.get().put(k, v);
    }

    default void remove(K k) {
        this.get().remove(k);
    }

    default boolean contains(K k) {
        return this.get().containsKey(k);
    }

}
