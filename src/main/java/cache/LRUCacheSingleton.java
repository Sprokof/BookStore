package cache;

public class LRUCacheSingleton {
    private static LRUCache<String, Object> cache;
    private static int CAPACITY = 700;

    public static LRUCache<String, Object> cacheInstance() {
        if(cache == null) {
            cache = new LRUCache<>(CAPACITY);
        }
        return cache;
    }
}
