package main.Algos;
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

    /** מפנה בקשה לדף דרך האסטרטגיה הנוכחית */
    public void request(int pageNumber) {
        strategy.requestPage(pageNumber);
    }

    /** בדיקת hit/miss דרך האסטרטגיה הנוכחית */
    public boolean contains(int pageNumber) {
        return strategy.contains(pageNumber);
    }
}