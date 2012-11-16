package net.exathunk.jsubschema.gendeps;

/**
 * charolastra 11/14/12 9:14 PM
 */
public interface DomainFactory<T> {
    Class<T> getDomainClass();
    T makeDomain();
}
