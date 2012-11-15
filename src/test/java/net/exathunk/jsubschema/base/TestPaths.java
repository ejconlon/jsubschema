package net.exathunk.jsubschema.base;

import net.exathunk.jsubschema.gen.Loader;
import net.exathunk.jsubschema.genschema.Schema;
import org.codehaus.jackson.JsonNode;
import org.junit.Test;

import java.io.IOException;

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
}
