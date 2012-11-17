package net.exathunk.jsubschema.pointers;

/**
 * charolastra 11/17/12 5:30 AM
 */
public interface Reversible<T> extends Directed {
    T reversed();
}
