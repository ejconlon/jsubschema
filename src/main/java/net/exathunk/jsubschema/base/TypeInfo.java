package net.exathunk.jsubschema.base;

import java.util.*;

/**
 * charolastra 11/13/12 7:26 PM
 */
public class TypeInfo {

    private static final Set<Type> SCALARS;
    private static final Set<Type> TYPES;

    static {
        SCALARS = Util.asSet(Arrays.asList(
            Type.of("string"), Type.of("boolean"),
            Type.of("long"), Type.of("double")));
        TYPES = Util.asSet(Arrays.asList(
            Type.of("object"), Type.of("array"),
            Type.of("string"), Type.of("boolean"),
            Type.of("long"), Type.of("double")));
        Type.seal();
    }

    public Set<Type> getTypes() {
        return TYPES;
    }

    public static Set<Type> getScalars() {
        return SCALARS;
    }

    public static Set<String> propsForThing(Thing thing) {
        if (thing.isObject()) {
            return thing.getObject().keySet();
        }
        return new TreeSet<String>();
    }
}
