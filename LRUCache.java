// Time Complexity : O(1) for get() and put()
// Space Complexity : O(capacity) due to hashmap + linked list
// Did this code successfully run on Leetcode : Yes
// Any problem you faced while coding this : No
// Approach -
//   - Use a HashMap to store key → node mappings for O(1) lookup.
//   - Use a doubly linked list to maintain the order of usage (most recently used at head).
//   - On get(), move accessed node to the head.
//   - On put(), insert new node at head and remove least recently used node if capacity exceeded.
//   - Removing and inserting nodes are done in O(1) time using direct pointer updates.

import java.util.HashMap;
import java.util.Map;

class ListNode {
    int key;
    int value;
    ListNode next;
    ListNode prev;

    public ListNode (int key, int value) {
        this.key = key;
        this.value = value;
    }
}

public class LRUCache {
    int capacity;
    Map<Integer, ListNode> hmap;
    ListNode head, tail;

    public LRUCache(int capacity) {
        this.capacity = capacity;
        hmap = new HashMap<>();
        head = new ListNode(-1, -1);    //adding -1 as value due to constraints
        tail = new ListNode(-1, -1);    //adding -1 as value due to constraints
        head.next = tail;
        tail.prev = head;
    }

    public int get(int key) {
        if(hmap.containsKey(key)) {
            ListNode node = hmap.get(key);
            remove(node);
            insert(node);
            return node.value;
        } else {
            return -1;
        }
    }

    public void put(int key, int value) {
        if(hmap.containsKey(key)) {
            remove(hmap.get(key));
        }
        if(hmap.size() == capacity) {
            remove(tail.prev);
        }
        insert(new ListNode(key, value));
    }

    private void insert(ListNode node) {
        hmap.put(node.key, node);   //add in map
        node.next = head.next;
        node.next.prev = node;
        head.next = node;
        node.prev = head;
    }

    private void remove(ListNode node) {
        hmap.remove(node.key);
        node.prev.next = node.next;
        node.next.prev = node.prev;
    }

    public static void main(String[] args) {
        LRUCache cache = new LRUCache(2); // capacity 2

        cache.put(1, 1);
        cache.put(2, 2);
        System.out.println("get(1): " + cache.get(1)); // returns 1

        cache.put(3, 3); // evicts key 2
        System.out.println("get(2): " + cache.get(2)); // returns -1 (not found)

        cache.put(4, 4); // evicts key 1
        System.out.println("get(1): " + cache.get(1)); // returns -1 (not found)
        System.out.println("get(3): " + cache.get(3)); // returns 3
        System.out.println("get(4): " + cache.get(4)); // returns 4
    }
}
/**
 * Your LRUCache object will be instantiated and called as such:
 * LRUCache obj = new LRUCache(capacity);
 * int param_1 = obj.get(key);
 * obj.put(key,value);
 */