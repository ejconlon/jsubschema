package net.exathunk.jsubschema.base;

/**
 * charolastra 11/13/12 8:36 PM
 */
public interface ArrayDSL {
    ObjectDSL seeObject();
    ArrayDSL seeArray();
    void seeScalar(Thing item);
}
