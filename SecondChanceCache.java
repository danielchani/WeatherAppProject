package main.Algos;

import java.util.ArrayList;
import java.util.List;

/**
 * Second-Chance (Clock) cache:
 * כשצריך לפנות מקום – סורקים במעגל ושוברים useBit=false.
 */
public class SecondChanceCache implements AlgoCache {
    private final int capacity;
    private final List<Integer> pages    = new ArrayList<>();
    private final List<Boolean> useBit   = new ArrayList<>();
    private int pointer = 0;

    public SecondChanceCache(int capacity) {
        this.capacity = capacity;
    }

    @Override
    public void requestPage(int pageNumber) {
        int idx = pages.indexOf(pageNumber);
        if (idx >= 0) {
            // Hit: סמן second chance
            useBit.set(idx, true);
            System.out.println("2ndChance hit " + pageNumber);
            return;
        }
        // Miss:
        if (pages.size() < capacity) {
            pages.add(pageNumber);
            useBit.add(false);
        } else {
            // מצא מקום במעגל
            while (useBit.get(pointer)) {
                useBit.set(pointer, false);
                pointer = (pointer + 1) % capacity;
            }
            pages.set(pointer, pageNumber);
            useBit.set(pointer, false);
            pointer = (pointer + 1) % capacity;
        }
        System.out.println("2ndChance requested " + pageNumber + " -> cache=" + pages);
    }

    @Override
    public boolean contains(int pageNumber) {
        return pages.contains(pageNumber);
    }
}
