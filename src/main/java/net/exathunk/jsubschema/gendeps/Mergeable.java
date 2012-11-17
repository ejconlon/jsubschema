package net.exathunk.jsubschema.gendeps;

/**
 * charolastra 11/16/12 2:40 PM
 */
public interface Mergeable<T> {
    void mergeFrom(T other);
}
