package Algos;

/**
 * An interface representing a cache replacement algorithm.
 * This interface defines the basic operations that any cache implementation must support.
 */
public interface AlgoCache {

    /**
     * Adds a key-value pair to the cache.
     * 
     * @param key The key to add
     * @param value The value associated with the key
     */
    void put(String key, String value);

    /**
     * Retrieves the value associated with a key from the cache.
     * 
     * @param key The key to retrieve the value for
     * @return The value associated with the key, or null if the key is not in the cache
     */
    String get(String key);

}
