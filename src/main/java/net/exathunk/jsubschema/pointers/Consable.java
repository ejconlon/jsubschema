package net.exathunk.jsubschema.pointers;

/**
 * charolastra 11/17/12 5:09 AM
 */
public interface Consable<H, T> {
    T cons(H head);
    T getTail();
    boolean isEmpty();
}
