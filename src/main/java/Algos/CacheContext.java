package Algos;

/** this will help us to switch dynamically between the usage of the algorithms */
public class CacheContext {
    private AlgoCache strategy;

    public CacheContext(AlgoCache initial) {
        this.strategy = initial;
    }

    /** מחליף אלגורתים בשעת ריצה */
    public void setStrategy(AlgoCache strategy) {
        this.strategy = strategy;
    }

    public void put(String key, String value) {
        strategy.put(key, value);
    }

    public String get(String key) {
        return strategy.get(key);
    }

    @Override
    public String toString() {
        return strategy.toString();
    }
}