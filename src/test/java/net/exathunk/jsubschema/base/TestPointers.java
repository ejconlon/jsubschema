package net.exathunk.jsubschema.base;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.node.IntNode;
import org.codehaus.jackson.node.TextNode;
import org.junit.Test;

import java.io.IOException;
import java.util.Map;
import java.util.TreeMap;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNot.not;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

/**
 * charolastra 11/16/12 7:22 PM
 */
public class TestPointers {

    JsonNode makeExampleNode() {
        String s = "{";
        s += "\"foo\": [\"bar\", \"baz\"],";
        s += "\"\": 0,";
        s += "\"a/b\": 1,";
        s += "\"c%d\": 2,";
        s += "\"e^f\": 3,";
        s += "\"g|h\": 4,";
        s += "\"i\\\\j\": 5,";
        s += "\"k\\\"l\": 6,";
        s += "\" \": 7,";
        s += "\"m~n\": 8";
        s += "}";
        try {
            return Util.parse(s);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    Map<String, JsonNode> makeCases() {
        final JsonNode example = makeExampleNode();
        Map<String, JsonNode> m = new TreeMap<String, JsonNode>();

        m.put("",           example);
        m.put("/foo",       example.get("foo"));
        m.put("/foo/0",     new TextNode("bar"));
        m.put("/",          new IntNode(0));
        m.put("/a~1b",      new IntNode(1));
        m.put("/c%d",       new IntNode(2));
        m.put("/e^f",       new IntNode(3));
        m.put("/g|h",       new IntNode(4));
        m.put("/i\\j",      new IntNode(5));
        m.put("/k\"l",      new IntNode(6));
        m.put("/ ",         new IntNode(7));
        m.put("/m~0n",      new IntNode(8));
        return m;
    }

    @Test
    public void testParts() {
        assertEquals("a/b", Part.fromPointerString("a~1b").getKey());
        assertEquals("m~n", Part.fromPointerString("m~0n").getKey());
        assertEquals("a~1b", Part.asKey("a/b").toPointerString());
        assertEquals("m~0n", Part.asKey("m~n").toPointerString());

        assertEquals("c~01d", Part.asKey("c~1d").toPointerString());
        assertEquals("c~10d", Part.asKey("c/0d").toPointerString());

        assertEquals("c~1d", Part.fromPointerString("c~01d").getKey());
        assertEquals("c/0d", Part.fromPointerString("c~10d").getKey());
    }

    @Test
    public void testPoint() {
        final JsonNode example = makeExampleNode();
        final Map<String, JsonNode> cases = makeCases();
        for (Map.Entry<String, JsonNode> entry : cases.entrySet()) {
            final String ref = entry.getKey();
            final JsonNode expected = entry.getValue();
            final Either<JsonNode, String> actual = Pointers.point(ref, example);
            assertEquals(Either.makeFirst(expected), actual);
        }
    }

    @Test
    public void testReverse() {
        final String goldPointer = "/foo/0/bar";
        final Pointer goldPath = new Pointer().cons(Part.asKey("foo")).cons(Part.asIndex(0)).cons(Part.asKey("bar"));
        final Pointer goldReversed = new Pointer().reversed().cons(Part.asKey("bar")).cons(Part.asIndex(0)).cons(Part.asKey("foo"));
        final Pointer badReversed = new Pointer().cons(Part.asKey("bar")).cons(Part.asIndex(0)).cons(Part.asKey("foo"));

        assertEquals(goldPointer, goldPath.toPointerString());
        assertEquals(goldPath, Pointer.fromPointerString(goldPointer).getFirst());
        assertThat(goldPath.reversed(), is(goldReversed));
        assertThat(goldPath.reversed(), is(not(badReversed)));
    }
}
