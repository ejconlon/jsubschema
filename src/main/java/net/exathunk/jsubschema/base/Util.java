package net.exathunk.jsubschema.base;

import net.sf.json.JSON;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;

import java.util.*;

/**
 * charolastra 11/13/12 7:22 PM
 */
public class Util {

    public static <X> Set<X> asSet(Iterable<X> xs) {
        Set<X> s = new HashSet<X>();
        for (X x : xs) {
            s.add(x);
        }
        return s;
    }

    public static <X> Set<X> asSet(X... xs) {
        Set<X> s = new HashSet<X>();
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

    public static Thing parse(String s) {
        JSON json = (new JSONSerializer()).toJSON(s);
        return parseInner(json);
    }

    private static Thing parseInner(Object json) {
        if (json instanceof JSONArray) {
            JSONArray a = (JSONArray)json;
            List<Thing> list = new ArrayList<Thing>(a.size());
            for (Object o : a) {
                list.add(parseInner(o));
            }
            return Thing.makeArray(list);
        } else if (json instanceof JSONObject) {
            JSONObject o = (JSONObject)json;
            Map<String, Thing> map = new TreeMap<String, Thing>();
            for (Object entries : o.entrySet()) {
                String key = ((Map.Entry<String, Thing>)entries).getKey();
                Object value = ((Map.Entry<String, Object>)entries).getValue();
                map.put(key, parseInner(value));
            }
            return Thing.makeObject(map);
        } else {
            return Thing.makeScalar(json);
        }
    }
}
