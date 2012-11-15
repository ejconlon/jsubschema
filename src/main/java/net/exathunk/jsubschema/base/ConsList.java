package net.exathunk.jsubschema.base;

/**
 * charolastra 11/15/12 11:52 AM
 */
public class ConsList<T> {

    private final T head;
    private final ConsList<T> tail;

    protected ConsList(T head, ConsList<T> tail) {
        this.head = head;
        this.tail = tail;
    }

    public static <T> ConsList<T> nil() {
        return new ConsList<T>(null, null);
    }

    public ConsList<T> cons(T head) {
        return new ConsList<T>(head, this);
    }

    public boolean isEmpty() {
        return tail == null;
    }

    public T getHead() {
        return head;
    }

    public ConsList<T> getTail() {
        return tail;
    }

    @Override
    public String toString() {
        return "ConsList{" +
                "head=" + head +
                ", tail=" + tail +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ConsList)) return false;

        ConsList consList = (ConsList) o;

        if (head != null ? !head.equals(consList.head) : consList.head != null) return false;
        if (tail != null ? !tail.equals(consList.tail) : consList.tail != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = head != null ? head.hashCode() : 0;
        result = 31 * result + (tail != null ? tail.hashCode() : 0);
        return result;
    }
}
