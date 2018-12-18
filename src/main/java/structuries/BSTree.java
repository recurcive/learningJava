package structuries;

import java.util.Iterator;

public class BSTree<K extends Comparable<K>, V> implements Iterable<V> {
    private static class Node<K, V> {
        K key;
        V info;
        Node<K, V> parent, left, right;

        Node(K key, V value) {
            this.key = key;
            this.info = value;
        }
    }

    Node<K, V> root;

    public void put(K key, V value) {

    }

    public V get(K key) {

        return get(key, root);
    }

    private V get(K key, Node<K, V> node) {
        if (node == null) return null;
        if (key.compareTo(node.key) == 0) return node.info;
        if (key.compareTo(node.key) < 0) return get(key, node.left);
        return get(key, node.right);
    }

    public boolean contains(K key) {
        return false;
    }

    public V remove(K key) {
        return null;
    }

    @Override
    public Iterator<V> iterator() {
        return null;
    }

}
