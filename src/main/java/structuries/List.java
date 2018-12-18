package structuries;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.PriorityQueue;

public class List<T> implements Iterable<T> {

    private static class ListElem<E> {
        E info;
        ListElem<E> next, pred;

        ListElem(E info, ListElem<E> next, ListElem<E> pred) {
            this.info = info;
            this.next = next;
            this.pred = pred;
        }
    }

    public static class NoElementException extends RuntimeException {
        public NoElementException() {
            super("No element of the list");
        }
    }

    private class ListIterator implements Iterator<T> {
        ListElem<T> next = first;
        long currentVersion = version;

        @Override
        public boolean hasNext() {
            if (version != currentVersion) throw new ConcurrentModificationException();
            return next != null;
        }

        @Override
        public T next() {
            if (version != currentVersion) throw new ConcurrentModificationException();
            if (next == null) throw new IllegalStateException();
            T info = next.info;
            next = next.next;
            return info;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    ListElem<T> first = null, last = null;
    private int size = 0;
    long version = 0;

    public void addFirst(T elem) {
        ListElem<T> newElem = new ListElem<>(elem, first, null);
        if (first != null) {
            first.pred = newElem;
        } else {
            last = newElem;
        }
        size++;
        version++;
        first = newElem;
    }

    public void addLast(T elem) {
        ListElem<T> newElem = new ListElem<>(elem, null, last);
        if (last != null) {
            last.next = newElem;
        } else {
            first = newElem;
        }
        size++;
        version++;
        last = newElem;
    }

    public T getFirst() {
        if (first == null) throw new NoElementException();
        return first.info;
    }

    public T getLast() {
        if (last == null) throw new NoElementException();
        return last.info;
    }

    public T removeFirst() {
        if (first == null) throw new NoElementException();

        T info = first.info;
        first = first.next;
        if (first == null) {
            last = null;
        } else {
            first.pred = null;
        }
        size--;
        version++;
        return info;
    }

    public T removeLast() {
        if (last == null) throw new NoElementException();

        T info = last.info;

        last = last.pred;
        if (last == null) {
            first = null;
        } else {
            last.next = null;
        }
        size--;
        version++;
        return info;
    }

    public T getAt(int index) {
        if (index == 0) return first.info;
        if (index == size-1) return last.info;


        int i = 1;
        if (index < (size / 2) ) {
            ListElem<T> nextElem = first.next;
            while (i < index) {
                nextElem = nextElem.next;
                i++;
            }
            return nextElem.info;
        } else {
            ListElem<T> previousElem = last.pred;
            while (i <= size-index) {
                previousElem = previousElem.pred;
                i++;
            }
            return previousElem.info;
        }
    }

    public void setAt(int i, T elem) {

    }

    public void insertAt(int i, T elem) {

    }

    public T removeAt(int i) {
        return null;
    }

    public int getSize() {
        return size;
    }

    @Override
    public String toString() {
        return "structuries.List{}";
    }

    @Override
    public Iterator<T> iterator() {
        return new ListIterator();
    }

    @Override
    public boolean equals(Object o) {
        return false;
    }

    @Override
    public int hashCode() {
        return 0;
    }
}
