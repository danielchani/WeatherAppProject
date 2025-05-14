package Algos;

import java.util.LinkedHashMap;
import java.util.Map;

/**
LRU algorithm keeps cache till it's capacity over and if it reaches the max the oldest cache is deleted

 */
public class LRUCache implements AlgoCache {
    private final int capacity;
    private final Map<String, String> cache;

    public LRUCache(int capacity) {
        this.capacity = capacity;
        this.cache = new LinkedHashMap<String, String>(capacity, 0.75f, true) {
            @Override
            protected boolean removeEldestEntry(Map.Entry<String, String> eldest) {
                return size() > capacity;
            }
        };
    }

    @Override
    public void put(String key, String value) {
        cache.put(key, value);
    }

    @Override
    public String get(String key) {
        return cache.get(key);
    }

    @Override
    public String toString() {
        return cache.toString();
    }
}
