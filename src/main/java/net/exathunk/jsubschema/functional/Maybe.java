package net.exathunk.jsubschema.functional;

/**
 * charolastra 11/20/12 12:07 AM
 */
public class Maybe<T> {
    private final boolean _isJust;
    private final T _just;

    private Maybe(boolean isJust, T just) {
        this._isJust = isJust;
        this._just = just;
    }

    public static <T> Maybe<T> just(T just) {
        return new Maybe<T>(true, just);
    }

    public static <T> Maybe<T> nothing() {
        return new Maybe<T>(false, null);
    }

    public boolean isJust() {
        return _isJust;
    }

    public boolean isNothing() {
        return !isJust();
    }

    public T getJust() {
        if (!_isJust) throw new IllegalStateException("Nothing!");
        return _just;
    }

    @Override
    public String toString() {
        return "Maybe{" +
                "_isJust=" + _isJust +
                ", _just=" + _just +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Maybe)) return false;

        Maybe maybe = (Maybe) o;

        if (_isJust != maybe._isJust) return false;
        if (_just != null ? !_just.equals(maybe._just) : maybe._just != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (_isJust ? 1 : 0);
        result = 31 * result + (_just != null ? _just.hashCode() : 0);
        return result;
    }
}
