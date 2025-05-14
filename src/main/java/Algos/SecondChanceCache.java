package Algos;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.HashMap;

public class SecondChanceCache implements AlgoCache {
    private final int capacity;
    private final Map<String, String> cache;
    private final Map<String, Boolean> secondChance;
    private final LinkedHashMap<String, String> order;

    public SecondChanceCache(int capacity) {
        this.capacity = capacity;
        this.cache = new HashMap<>();
        this.secondChance = new HashMap<>();
        this.order = new LinkedHashMap<>();
    }

    @Override
    public void put(String key, String value) {
        if (!cache.containsKey(key) && cache.size() >= capacity) {
            evict();
        }
        
        cache.put(key, value);
        secondChance.put(key, false);
        order.put(key, value);
    }

    private void evict() {
        String firstKey = order.keySet().iterator().next();
        while (secondChance.get(firstKey)) {
            // Give second chance
            secondChance.put(firstKey, false);
            order.remove(firstKey);
            order.put(firstKey, cache.get(firstKey));  // Move to end
            firstKey = order.keySet().iterator().next();
        }
        
        // Remove the first entry that didn't use its second chance
        cache.remove(firstKey);
        secondChance.remove(firstKey);
        order.remove(firstKey);
    }

    @Override
    public String get(String key) {
        String value = cache.get(key);
        if (value != null) {
            // Mark as accessed
            secondChance.put(key, true);
            // Move to end
            order.remove(key);
            order.put(key, value);
        }
        return value;
    }

    @Override
    public String toString() {
        return cache.toString();
    }
}
