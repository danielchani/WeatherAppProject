package AlgoTests;

import main.Algos.*;
import org.junit.Test;
import static org.junit.Assert.*;

public class AlgoCacheTest {

    @Test
    public void testSecondChanceBasic() {
        AlgoCache cache = new SecondChanceCache(2);
        cache.requestPage(1);  // add 1
        cache.requestPage(2);  // add 2
        cache.requestPage(1);  // hit 1 → useBit[0]=true
        cache.requestPage(3);  // miss: pointer מפנה ל-[0]? useBit[0]==true → מתאפס, ממשיך ל-[1], שם useBit=false → מחליף
        assertTrue(cache.contains(1) || cache.contains(3));
    }

    @Test
    public void testStrategySwitching() {
        CacheContext context = new CacheContext(new LRUCache(2));
        context.request(1);
        context.request(2);
        // בתצורת LRU: אחרי request(3) – הדף הישן ביותר (1) יורד
        context.request(3);
        assertFalse(context.contains(1));
        // עכשיו נשנה לאלגוריתם אקראי
        context.setStrategy(new RandomCache(1));
        context.request(42);
        // capacity=1 → חייב להיות רק 42 במטמון
        assertTrue(context.contains(42));
        assertFalse(context.contains(2));
    }
}
