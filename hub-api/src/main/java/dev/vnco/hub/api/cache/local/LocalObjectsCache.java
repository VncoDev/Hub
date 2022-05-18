package dev.vnco.hub.api.cache.local;

import dev.vnco.hub.api.cache.Cache;

import java.util.HashMap;
import java.util.Map;

public class LocalObjectsCache<K, V> implements Cache<K, V> {

    private final Map<K, V> map = new HashMap<>();

    @Override
    public Map<K, V> get() {
        return map;
    }
}
