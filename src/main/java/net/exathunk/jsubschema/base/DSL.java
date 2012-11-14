package net.exathunk.jsubschema.base;

/**
 * charolastra 11/13/12 8:22 PM
 */
public interface DSL {
    ObjectDSL seeObject();
    ArrayDSL seeArray();
    void seeScalar(Thing scalar);
}
