package net.exathunk.jsubschema.base;

import java.util.*;

/**
 * charolastra 11/13/12 7:26 PM
 */
public class Nodes {

    private static final List<Type> TYPES;

    static {
        TYPES = Arrays.asList(
            Type.of("object"), Type.of("array"),
            Type.of("string"), Type.of("boolean"),
            Type.of("integer"), Type.of("number"));
        Type.seal();
    }

    public List<Type> getTypes() {
        return TYPES;
    }

    public static Set<String> propsForType(Type type) {
        Set<String> s = new TreeSet<String>();
        return s;
    }
}
