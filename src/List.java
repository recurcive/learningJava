import java.util.Iterator;

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
            super("No element of thje list");
        }
    }

    private class ListIterator implements Iterator<T> {
        ListElem<T> next = first;

        @Override
        public boolean hasNext() {
            return next != null;
        }

        @Override
        public T next() {
            if (next == null) throw new IllegalStateException();
            T info = next.info;
            next = next.next;
            return info;
        }
    }

    ListElem<T> first = null, last = null;

    public void addFirst(T elem) {
        ListElem<T> newElem = new ListElem<>(elem, first, null);
        if (first != null) {
            first.pred = newElem;
        } else {
            last = newElem;
        }
        first = newElem;
    }

    public void addLast(T elem) {
        ListElem<T> newElem = new ListElem<>(elem, null, last);
        if (last != null) {
            last.next = newElem;
        } else {
            first = newElem;
        }
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
        return info;
    }

    public T removeLast() {

    }

    public T getAt(int i) {

    }

    public void setAt(int i, T elem) {

    }

    public void insertAt(int i, T elem) {

    }

    public T removeAt(int i) {

    }

    @Override
    public String toString() {
        return "List{}";
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
