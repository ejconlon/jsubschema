package net.exathunk.jsubschema.base;

/**
 * charolastra 11/13/12 8:33 PM
 */
public interface ObjectDSL {
    ObjectDSL seeObject(String key);
    ArrayDSL seeArray(String key);
    void seeScalar(String key, Thing value);
}
