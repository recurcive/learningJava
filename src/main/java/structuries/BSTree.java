package structuries;

import java.util.Iterator;
import java.util.Stack;
import java.util.function.Consumer;

public class BSTree<K extends Comparable<K>, V> implements Iterable<V> {
    private static class Node<K, V> {
        K key;
        V info;
        Node<K, V> left, right;

        Node(K key, V value) {
            this.key = key;
            this.info = value;
        }
    }

    private class TreeIterator implements Iterator<V> {
        Stack<Node<K, V>> stack = new Stack<>();

        TreeIterator() {
            Node<K, V> current = root;
            while (current != null) {
                stack.push(current);
                current = current.left;
            }
        }

        @Override
        public boolean hasNext() {
            return !stack.isEmpty();
        }

        @Override
        public V next() {
            if (stack.isEmpty()) throw new IllegalStateException();
            Node<K, V> nextNode = stack.pop();
            Node<K, V> current = nextNode.right;
            while (current != null) {
                stack.push(current);
                current = current.left;
            }
            return nextNode.info;
        }
    }

    Node<K, V> root;

    public void put(K key, V value) {
        root = put(key, value, root);
    }

    private Node<K, V> put(K key, V value, Node<K, V> node) {
        if (node == null) {
            return new Node<>(key, value);
        } else {
            if (key.compareTo(node.key) == 0) {
                node.info = value;
            } else if (key.compareTo(node.key) < 0) {
                node.left = put(key, value, node.left);
            } else {
                node.right = put(key, value, node.right);
            }
            return node;
        }
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

    public void remove(K key) {
        root = remove(key, root);
    }

    private Node<K, V> remove(K key, Node<K, V> node) {
        if (node == null) return null;
        if (key.compareTo(node.key) < 0) {
            node.left = remove(key, node.left);
            return node;
        } else if (key.compareTo(node.key) > 0) {
            node.right = remove(key, node.right);
            return node;
        } else if (node.left == null) {
            return node.right;
        } else if (node.right == null) {
            return node.left;
        } else {
            Node<K, V> minNode = min(node.right);
            K minKey = minNode.key;
            node.key = minNode.key;
            node.info = minNode.info;
            node.right = remove(minKey, node.right);
            return node;
        }
    }

    private Node<K, V> min(Node<K, V> node) {
        if (node.left == null) return node; else return min(node.left);
    }

    public void iterate(Consumer<V> action) {
        iterate (action, root);
    }

    private void iterate(Consumer<V> action, Node<K, V> node) {
        if (node != null) {
            iterate(action, node.left);
            action.accept(node.info);
            iterate(action, node.right);
        }
    }

    @Override
    public Iterator<V> iterator() {
        return new TreeIterator();
    }

    public static void main(String[] args) {
        BSTree<Integer, String> tree = new BSTree<>();
        tree.put(10, "Str 10");
        tree.put(5, "Str 5");
        tree.put(12, "Str 12");
        tree.put(3, "Str 3");
        tree.put(7, "Str 7");
        for (String s : tree) System.out.println(s);
        tree.remove(10);
        tree.iterate(System.out::println);
    }
}
