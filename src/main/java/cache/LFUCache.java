package cache;

import java.util.*;


public class LFUCache {
    private final Map<String, Node> valueMap;
    private final Map<String, Integer> countMap;
    private final TreeMap<Integer, DoubleLinkedList> freqMap;

    private int capacity;
    private int size;

    public LFUCache(int capacity) {
        this.capacity = capacity;
        this.valueMap = new HashMap<>();
        this.countMap = new HashMap<>();
        this.freqMap = new TreeMap<>();
        this.size = 0;
    }

    public Object get(String key){
        if(!valueMap.containsKey(key) || size == 0) return null;

        Node nodeToDelete = valueMap.get(key);
        Node node = new Node(key, nodeToDelete.value);
        int freq = countMap.get(key);
        freqMap.get(freq).remove(nodeToDelete);
        removeIfListEmpty(freq);
        valueMap.remove(key);
        countMap.remove(key);
        valueMap.put(key, node);
        countMap.put(key, freq + 1);
        freqMap.computeIfAbsent(freq + 1, k -> new DoubleLinkedList()).add(node);

        size ++ ;
        return valueMap.get(key).value;
    }

    public void put(String key, Object value){
        Node node = new Node(key, value);
        if(!valueMap.containsKey(key) && size > 0) {
            if (valueMap.size() == capacity) {
                int lowestKey = freqMap.firstKey();
                Node nodeToDelete = freqMap.get(lowestKey).head();
                freqMap.get(lowestKey).remove(nodeToDelete);
                removeIfListEmpty(lowestKey);

                String keyToDelete = nodeToDelete.key;
                valueMap.remove(keyToDelete);
                countMap.remove(keyToDelete);
            }
            freqMap.computeIfAbsent(1, k -> new DoubleLinkedList()).add(node);
            valueMap.put(key, node);
            countMap.put(key, 1);
        }

    else if(size > 0) {
        Node nodeToDelete = valueMap.get(key);
        int freq = countMap.get(key);
        freqMap.get(freq).remove(nodeToDelete);
        removeIfListEmpty(freq);
        valueMap.remove(key);
        countMap.remove(key);

        valueMap.put(key, node);
        countMap.put(key, 1);
        freqMap.computeIfAbsent(freq + 1, k-> new DoubleLinkedList()).add(node);

    }

    }

    private void removeIfListEmpty(int freq) {
        if(freqMap.get(freq).size() == 0){
            freqMap.remove(freq);
        }
    }

    public void updateIfExist(String[] keys, Object value){
        for(String key : keys){
            if(valueMap.containsKey(key)) put(key, value);
        }
    }


    public void remove(String key){
        Node nodeToDelete = valueMap.get(key);
        valueMap.remove(key);
        countMap.remove(key);
        int freq = countMap.get(key);
        freqMap.get(freq).remove(nodeToDelete);

    }

    public boolean keyExist(String key){
        return this.valueMap.containsKey(key);
    }


}