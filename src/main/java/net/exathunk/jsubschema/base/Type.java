package net.exathunk.jsubschema.base;

import java.util.Map;
import java.util.TreeMap;

/**
 * charolastra 11/13/12 7:58 PM
 */
public class Type {
    private final String name;

    private static boolean sealed = false;
    private static final Map<String, Type> types = new TreeMap<String, Type>();
    public synchronized static void seal() {
        sealed = true;
    }
    public synchronized static Type of(String name) {
        Type t = types.get(name);
        if (t == null) {
            if (sealed) throw new IllegalStateException("Types sealed: "+name);
            t = new Type(name);
            types.put(name, t);
        }
        return t;
    }

    protected Type(String name) {
        this.name = name;
        assert name != null;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "Type{" +
                "name='" + name + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Type)) return false;

        Type type = (Type) o;

        if (!name.equals(type.name)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }
}
