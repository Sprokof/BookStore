package cache;

public class LFUCacheSingleton {
    private static LFUCache cache;
    private static int CAPACITY = 700;

    public static LFUCache cacheInstance() {
        if(cache == null) {
            cache = new LFUCache(CAPACITY);
        }
        return cache;
    }
}
