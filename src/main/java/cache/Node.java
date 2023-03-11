package cache;

class Node {
    String key;
    Object value;
    Node prev, next;

    public Node(String key, Object value) {
        this.key = key;
        this.value = value;
    }
}
