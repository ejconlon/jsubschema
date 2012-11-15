package net.exathunk.jsubschema.base;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

/**
 * charolastra 11/14/12 3:42 PM
 */
public class Session {
    public final Map<String, Schema> schemas = new TreeMap<String, Schema>();
    public final Map<Class, Binder> binders = new HashMap<Class, Binder>();

    @Override
    public String toString() {
        return "Session{" +
                "schemas=" + schemas +
                ", binders=" + binders +
                '}';
    }
}
