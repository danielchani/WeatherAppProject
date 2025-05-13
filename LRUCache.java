package main.Algos;

import java.util.LinkedHashMap;
import java.util.Map;

/**
LRU algorithm keeps cache till it's capacity over and if it reaches the max the oldest cache is deleted

 */
public class LRUCache implements AlgoCache {
    private final int capacity;
    private final LinkedHashMap<Integer, Integer> cache;

    public LRUCache(int capacity) {
        this.capacity = capacity;
        // accessOrder=true => סדר ההסרה לפי שימוש אחרון
        this.cache = new LinkedHashMap<>(capacity, 0.75f, true) {
            @Override
            protected boolean removeEldestEntry(Map.Entry<Integer, Integer> eldest) {
                // ברגע שהגענו לגודל גדול מהקיבולת – מסירים את ה"זקן" ביותר
                return size() > LRUCache.this.capacity;
            }
        };
    }

    @Override
    public void requestPage(int pageNumber) {
        // הכנסת הדף (אם כבר קיים – הוא ייסומן כ"חדש" בסדר הגי
        cache.put(pageNumber, pageNumber);
        System.out.println("LRU requested " + pageNumber + " -> cache=" + cache.keySet());
    }

    @Override
    public boolean contains(int pageNumber) {
        return cache.containsKey(pageNumber);
    }
}
