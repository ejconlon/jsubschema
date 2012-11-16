package net.exathunk.jsubschema.base;

import net.exathunk.jsubschema.gen.Loader;
import net.exathunk.jsubschema.genschema.Schema;
import org.codehaus.jackson.JsonNode;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * charolastra 11/15/12 12:37 PM
 */
public class TestPaths {
    @Test
    public void testPath() {
        final String goldPointer = "/foo/0/bar";
        final Path goldPath = new Path().cons(Part.asKey("foo")).cons(Part.asIndex(0)).cons(Part.asKey("bar"));
        final Path goldReversed = new Path().cons(Part.asKey("bar")).cons(Part.asIndex(0)).cons(Part.asKey("foo"));

        assertEquals(goldPointer, goldPath.toPointer());
        assertEquals(goldPath, Path.fromPointer(goldPointer));
        assertEquals(goldReversed, goldPath.reversed());
    }

    @Test
    public void testPathSchema() throws IOException, TypeException, PathException {
        Session session = Session.loadDefaultSession();
        Schema schema = session.schemas.get("http://exathunk.net/schemas/schema");
        assertNotNull(schema);

        Schema reqSchema = Pather.pathSchema(schema, new Path().cons(Part.asKey("type")));
        assertNotNull(reqSchema);
        assertEquals("string", reqSchema.type);

        Schema idForbidSchema = Pather.pathSchema(schema, new Path().cons(Part.asKey("id")).cons(Part.asKey("forbids")).reversed());
        assertNotNull(idForbidSchema);
        assertEquals("array", idForbidSchema.type);

        Schema idForbid0Schema = Pather.pathSchema(schema, new Path().cons(Part.asKey("id")).cons(Part.asKey("forbids")).cons(Part.asIndex(0)).reversed());
        assertNotNull(idForbid0Schema);
        assertEquals("string", idForbid0Schema.type);
    }

    @Test
    public void testPathNode() throws IOException, TypeException, PathException {
        JsonNode node = Loader.loadSchemaNode("schema");

        JsonNode reqNode = Pather.pathNode(node, new Path().cons(Part.asKey("type")));
        assertNotNull(reqNode);
        assertEquals("object", reqNode.asText());

        JsonNode idForbidNode = Pather.pathNode(node, new Path().cons(Part.asKey("properties")).cons(Part.asKey("id")).cons(Part.asKey("forbids")).reversed());
        assertNotNull(idForbidNode);
        assertEquals(1, idForbidNode.size());

        JsonNode idForbid0Node = Pather.pathNode(node, new Path().cons(Part.asKey("properties")).cons(Part.asKey("id")).cons(Part.asKey("forbids")).cons(Part.asIndex(0)).reversed());
        assertNotNull(idForbid0Node);
        assertEquals("$ref", idForbid0Node.asText());
    }

    @Test
    public void testTupling() throws IOException, TypeException, PathException {
        Session session = Session.loadDefaultSession();
        Schema schema = session.schemas.get("http://exathunk.net/schemas/schema");
        assertNotNull(schema);

        JsonNode node = Loader.loadSchemaNode("geo");

        List<PathTuple> flattened = new PathTuple(schema, node).flattened();
        //System.out.println(flattened);

        assertEquals("object", flattened.get(0).schema.type);
        assertEquals(true, flattened.get(0).path.isEmpty());

        assertEquals("string", flattened.get(1).schema.type);
        assertEquals("id", flattened.get(1).path.getHead().getKey());

        assertEquals("string", flattened.get(2).schema.type);
        assertEquals("description", flattened.get(2).path.getHead().getKey());

        assertEquals("string", flattened.get(3).schema.type);
        assertEquals("type", flattened.get(3).path.getHead().getKey());

        assertEquals("object", flattened.get(4).schema.type);
        assertEquals("properties", flattened.get(4).path.getHead().getKey());

        assertEquals("object", flattened.get(5).schema.type);
        assertEquals("latitude", flattened.get(5).path.getHead().getKey());

        assertEquals("string", flattened.get(6).schema.type);
        assertEquals("type", flattened.get(6).path.getHead().getKey());

        assertEquals("object", flattened.get(7).schema.type);
        assertEquals("longitude", flattened.get(7).path.getHead().getKey());

        assertEquals("string", flattened.get(8).schema.type);
        assertEquals("type", flattened.get(8).path.getHead().getKey());
    }
}
