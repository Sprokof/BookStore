package cache;

class DoubleLinkedList {
    private int size;
    private Node head, tail;

    void add(Node node){
        if(head == null){
            head = node;
        }
        else {
            tail.next = node;
            node.prev = tail;
        }
        tail = node;

        size ++ ;

    }

    void remove(Node node){
        if(node.next == null) tail = node;
        else node.next.prev = node.prev;

        if(node.prev == null) head = node.next;
        else node.prev.next = node.next;

        size -- ;

    }

    Node head() {
        return head;
    }

    Node tail() {
        return tail;
    }

    int size() {
        return size;
    }


}
