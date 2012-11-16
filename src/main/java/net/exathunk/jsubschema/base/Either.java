package net.exathunk.jsubschema.base;

/**
 * charolastra 11/16/12 11:28 AM
 */
public class Either<First, Second> {
    private final boolean isfirst;
    private final First first;
    private final Second second;

    private Either(boolean isfirst, First first, Second second) {
        this.isfirst = isfirst;
        this.first = first;
        this.second = second;
    }

    public boolean isFirst() {
        return isfirst;
    }

    public boolean isSecond() {
        return !isFirst();
    }

    public First getFirst() {
        if (!isFirst()) throw new IllegalStateException();
        return first;
    }

    public Second getSecond() {
        if (!isSecond()) throw new IllegalStateException();
        return second;
    }

    public static <A, B> Either<A, B> makeFirst(A first) {
        return new Either<A, B>(true, first, null);
    }

    public static <A, B> Either<A, B> makeSecond(B second) {
        return new Either<A, B>(false, null, second);
    }

    @Override
    public String toString() {
        return "Either{" +
                (isfirst ? "first=" + first : "second=" + second) +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Either)) return false;

        Either either = (Either) o;

        if (isfirst != either.isfirst) return false;
        if (first != null ? !first.equals(either.first) : either.first != null) return false;
        if (second != null ? !second.equals(either.second) : either.second != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (isfirst ? 1 : 0);
        result = 31 * result + (first != null ? first.hashCode() : 0);
        result = 31 * result + (second != null ? second.hashCode() : 0);
        return result;
    }
}
