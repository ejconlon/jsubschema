package net.exathunk.jsubschema.functional;

/**
 * charolastra 11/16/12 11:28 AM
 */
public class Either<First, Second> {

    private static enum Which { FIRST, SECOND }

    private final Which which;
    private final First first;
    private final Second second;

    private Either(Which which, First first, Second second) {
        this.which = which;
        this.first = first;
        this.second = second;
    }

    public boolean isFirst() {
        return Which.FIRST.equals(which);
    }

    public boolean isSecond() {
        return Which.SECOND.equals(which);
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
        return new Either<A, B>(Which.FIRST, first, null);
    }

    public static <A, B> Either<A, B> makeSecond(B second) {
        return new Either<A, B>(Which.SECOND, null, second);
    }

    @Override
    public String toString() {
        return "Either{" +
                (isFirst() ? "first=" + first : "") +
                (isSecond() ? "second=" + second : "") +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Either)) return false;

        Either either = (Either) o;

        if (first != null ? !first.equals(either.first) : either.first != null) return false;
        if (second != null ? !second.equals(either.second) : either.second != null) return false;
        if (which != either.which) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = which.hashCode();
        result = 31 * result + (first != null ? first.hashCode() : 0);
        result = 31 * result + (second != null ? second.hashCode() : 0);
        return result;
    }
}
