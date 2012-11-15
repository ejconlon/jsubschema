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

}
