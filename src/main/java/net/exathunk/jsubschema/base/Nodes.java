package net.exathunk.jsubschema.base;

import java.util.*;

/**
 * charolastra 11/13/12 7:26 PM
 */
public class Nodes {

    private static final Set<Type> TYPES;

    static {
        TYPES = Util.asSet(Arrays.asList(
            Type.of("object"), Type.of("array"),
            Type.of("string"), Type.of("boolean"),
            Type.of("long"), Type.of("double")));
        Type.seal();
    }

    public Set<Type> getTypes() {
        return TYPES;
    }

    public static Set<String> propsForType(Type type) {
        Set<String> s = new TreeSet<String>();
        if (TYPES.contains(type)) {
            s.add("type");
        } else {
            throw new IllegalArgumentException("Bad type: "+type);
        }
        if (type.equals(Type.of("object"))) {
            s.add("properties");
        } else if (type.equals(Type.of("array"))) {
            s.add("items");
        }
        return s;
    }
}
