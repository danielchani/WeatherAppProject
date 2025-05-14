package Algos;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class RandomCache implements AlgoCache {
    private final int capacity;
    private final Map<String, String> cache;
    private final List<String> keys;
    private final Random random;

    public RandomCache(int capacity) {
        this.capacity = capacity;
        this.cache = new HashMap<>();
        this.keys = new ArrayList<>();
        this.random = new Random();
    }

    @Override
    public void put(String key, String value) {
        if (!cache.containsKey(key) && cache.size() >= capacity) {
            // Randomly remove an existing entry
            int randomIndex = random.nextInt(keys.size());
            String randomKey = keys.remove(randomIndex);
            cache.remove(randomKey);
        }
        
        if (!cache.containsKey(key)) {
            keys.add(key);
        }
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

