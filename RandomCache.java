package main.Algos;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
when the capacity of the cache is full the randomcache deleted a random piece for the new one

 */
public class RandomCache implements AlgoCache {
    private final int capacity;
    private final List<Integer> cache;
    private final Random random;

    public RandomCache(int capacity) {
        this.capacity = capacity;
        this.cache = new ArrayList<>(capacity);
        this.random = new Random();
    }

    @Override
    public void requestPage(int pageNumber) {
        if (cache.contains(pageNumber)) {
            System.out.println("Random hit " + pageNumber);
            return;
        }
        if (cache.size() < capacity) {
            cache.add(pageNumber);
        } else {
            // בחר אינדקס אקראי והחלף
            int idx = random.nextInt(capacity);
            cache.set(idx, pageNumber);
        }
        System.out.println("Random requested " + pageNumber + " -> cache=" + cache);
    }

    @Override
    public boolean contains(int pageNumber) {
        return cache.contains(pageNumber);
    }
}

