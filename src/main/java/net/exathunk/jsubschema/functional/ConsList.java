package net.exathunk.jsubschema.functional;

import net.exathunk.jsubschema.pointers.Consable;

import java.util.Iterator;

/**
 * charolastra 11/15/12 11:52 AM
 */
public class ConsList<T> implements Iterable<T>, Consable<T, ConsList<T>> {

    private final boolean isNil;
    private final T head;
    private final ConsList<T> tail;

    protected ConsList(boolean isNil, T head, ConsList<T> tail) {
        this.isNil = isNil;
        this.head = head;
        this.tail = tail;
    }

    public static <T> ConsList<T> nil() {
        return new ConsList<T>(true, null, null);
    }

    @Override
    public ConsList<T> cons(T head) {
        return new ConsList<T>(false, head, this);
    }

    @Override
    public boolean isEmpty() {
        return isNil;
    }

    public T getHead() {
        return head;
    }

    @Override
    public ConsList<T> getTail() {
        return tail;
    }

    @Override
    public String toString() {
        return "ConsList{" +
                "isNil=" + isNil +
                ", head=" + head +
                ", tail=" + tail +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ConsList)) return false;

        ConsList consList = (ConsList) o;

        if (isNil != consList.isNil) return false;
        if (head != null ? !head.equals(consList.head) : consList.head != null) return false;
        if (tail != null ? !tail.equals(consList.tail) : consList.tail != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (isNil ? 1 : 0);
        result = 31 * result + (head != null ? head.hashCode() : 0);
        result = 31 * result + (tail != null ? tail.hashCode() : 0);
        return result;
    }

    @Override
    public Iterator<T> iterator() {
        return new ConsListIterator(this);
    }

    private class ConsListIterator implements Iterator<T> {

        private ConsList<T> list;

        public ConsListIterator(ConsList<T> list) {
            this.list = list;
        }

        @Override
        public boolean hasNext() {
            return !list.isEmpty();
        }

        @Override
        public T next() {
            T head = list.getHead();
            list = list.getTail();
            return head;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }
}
