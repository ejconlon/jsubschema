package net.exathunk.jsubschema.gen;

/**
 * charolastra 11/16/12 2:40 PM
 */
public interface Mergable<T> {
    void mergeFrom(T other);
}
