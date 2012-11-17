package net.exathunk.jsubschema.functional;

/**
 * charolastra 11/16/12 11:28 AM
 */
public class Either3<First, Second, Third> {

    private static enum Which { FIRST, SECOND, THIRD }

    private final Which which;
    private final First first;
    private final Second second;
    private final Third third;

    private Either3(Which which, First first, Second second, Third third) {
        this.which = which;
        this.first = first;
        this.second = second;
        this.third = third;
    }

    public boolean isFirst() {
        return Which.FIRST.equals(which);
    }

    public boolean isSecond() {
        return Which.SECOND.equals(which);
    }

    public boolean isThird() {
        return Which.THIRD.equals(which);
    }

    public First getFirst() {
        if (!isFirst()) throw new IllegalStateException();
        return first;
    }

    public Second getSecond() {
        if (!isSecond()) throw new IllegalStateException();
        return second;
    }

    public Third getThird() {
        if (!isThird()) throw new IllegalStateException();
        return third;
    }

    public static <A, B, C> Either3<A, B, C> makeFirst(A first) {
        return new Either3<A, B, C>(Which.FIRST, first, null, null);
    }

    public static <A, B, C> Either3<A, B, C> makeSecond(B second) {
        return new Either3<A, B, C>(Which.SECOND, null, second, null);
    }

    public static <A, B, C> Either3<A, B, C> makeThird(C third) {
        return new Either3<A, B, C>(Which.THIRD, null, null, third);
    }

    @Override
    public String toString() {
        return "Either{" +
                (isFirst() ? "first=" + first : "") +
                (isSecond() ? "second=" + second : "") +
                (isThird() ? "third=" + third : "") +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Either3)) return false;

        Either3 either3 = (Either3) o;

        if (first != null ? !first.equals(either3.first) : either3.first != null) return false;
        if (second != null ? !second.equals(either3.second) : either3.second != null) return false;
        if (third != null ? !third.equals(either3.third) : either3.third != null) return false;
        if (which != either3.which) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = which.hashCode();
        result = 31 * result + (first != null ? first.hashCode() : 0);
        result = 31 * result + (second != null ? second.hashCode() : 0);
        result = 31 * result + (third != null ? third.hashCode() : 0);
        return result;
    }
}
