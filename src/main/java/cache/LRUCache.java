package cache;

import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

public class LRUCache<K, V> {
    private final Map<K,V> values;
    private final int capacity;

    public LRUCache(int capacity) {
        this.values = new LinkedHashMap<>();
        this.capacity = capacity;
    }

    public V get(K key){
        if(!keyExist(key)){
            return null;
        }
        return this.values.get(key);
    }

    public boolean keyExist(K key){
        return values.containsKey(key);
    }

    public void put(K key, V value){
        if(this.values.size() == capacity){
            Map.Entry<K,V> entry = this.values.entrySet().iterator().next();
            K firstKey = entry.getKey();
            this.values.remove(firstKey);
        }
        this.values.put(key,value);
    }

    public void remove(K key) {
        this.values.remove(key);
    }

}
