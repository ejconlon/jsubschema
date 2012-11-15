package net.exathunk.jsubschema.base;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.node.ObjectNode;

import java.io.IOException;
import java.util.*;

/**
 * charolastra 11/13/12 7:22 PM
 */
public class Util {

    public static <X> Set<X> asSet(Iterator<X> xs) {
        final Set<X> s = new HashSet<X>();
        while (xs.hasNext()) {
            final X next = xs.next();
            s.add(next);
        }
        return s;
    }

    public static <X> Set<X> asSet(Iterable<X> xs) {
        return asSet(xs.iterator());
    }

    public static <X> Set<X> asSet(X... xs) {
        final Set<X> s = new HashSet<X>();
        for (X x : xs) {
            s.add(x);
        }
        return s;
    }

    public static <X> boolean setEquals(Set<X> as, Set<X> bs) {
        if (as == null) return bs == null;
        if (bs == null) return false;
        if (as.size() != bs.size()) return false;
        for (X a : as) {
            if (!bs.contains(a)) return false;
        }
        return true;
    }

    public static void assertTrue(String label, Object a, Object b, boolean asserted) {
        if (!asserted) {
            throw new AssertionError("Assertion failed: label => "+label+"; a => "+a+"; b => "+b);
        }
    }

    public static List<String> split(String source, String delim) {
        String[] parts = source.split(delim);
        List<String> list = new ArrayList<String>(parts.length);
        for (String part : parts) {
            if (part != null && !part.isEmpty()) list.add(part);
        }
        return list;
    }

    public static JsonNode parse(String s) throws IOException {
        return (new ObjectMapper()).readTree(s);
    }

    public static Set<String> propsForNode(JsonNode node) {
        if (node.isObject()) {
            return Util.asSet(((ObjectNode)node).getFieldNames());
        }
        return new TreeSet<String>();
    }

    public static <X> X last(List<X> split) {
        return split.get(split.size()-1);
    }

    public static String capitalize(String s) {
        final String[] parts = s.split("\\W+");
        final StringBuilder sb = new StringBuilder();
        for (String part : parts) {
            sb.append(upperFirst(part));
        }
        return sb.toString();
    }

    public static String camelize(String key) {
        String s = capitalize(key);
        return lowerFirst(s);
    }

    public static String lowerFirst(String s) {
        return s.substring(0, 1).toLowerCase()+s.substring(1);
    }

    public static String upperFirst(String s) {
        return s.substring(0, 1).toUpperCase()+s.substring(1);
    }


    private static final Map<String, String> SUBS;

    static {
        SUBS = new TreeMap<String, String>();
        SUBS.put("\\$", "__dollar__");
    }

    public static String convert(String name) {
        for (Map.Entry<String, String> e : SUBS.entrySet()) {
            name = name.replaceAll(e.getKey(), e.getValue());
        }
        return name;
    }

    public static String unconvert(String name) {
        for (Map.Entry<String, String> e : SUBS.entrySet()) {
            name = name.replaceAll(e.getValue(), e.getKey());
        }
        return name;
    }
}
