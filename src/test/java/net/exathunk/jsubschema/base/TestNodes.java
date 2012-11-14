package net.exathunk.jsubschema.base;

import org.junit.Test;

import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import static org.junit.Assert.assertEquals;

/**
 * charolastra 11/13/12 7:18 PM
 */
public class TestNodes {

    final String[][] strings = new String[][] {
            {"object", ""},
            {"array", ""},
            {"string", ""},
            {"boolean", ""},
            {"integer", ""},
            {"number", ""},
    };

    @Test
    public void testNodes() {
        for (String[] ss : strings) {
            Set<String> as = Nodes.propsForType(Type.of(ss[0]));
            Set<String> bs = Util.asSet(Util.split(ss[1], ","));
            Util.assertTrue("propsForType", as, bs, Util.setEquals(as, bs));
        }
    }
}
