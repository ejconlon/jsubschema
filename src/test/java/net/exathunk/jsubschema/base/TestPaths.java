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

        Either<Schema, String> reqSchema = Pather.pathSchema(schema, new Path().cons(Part.asKey("type")));
        assertNotNull(reqSchema);
        assertEquals("string", reqSchema.getFirst().type);

        Either<Schema, String> idForbidSchema = Pather.pathSchema(schema, new Path().cons(Part.asKey("id")).cons(Part.asKey("forbids")).reversed());
        assertNotNull(idForbidSchema);
        assertEquals("array", idForbidSchema.getFirst().type);

        Either<Schema, String> idForbid0Schema = Pather.pathSchema(schema, new Path().cons(Part.asKey("id")).cons(Part.asKey("forbids")).cons(Part.asIndex(0)).reversed());
        assertNotNull(idForbid0Schema);
        assertEquals("string", idForbid0Schema.getFirst().type);
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

        List<PathTuple> flattened = Util.asList(Util.withSelfDepthFirst(new PathTuple(schema, node)));
        //System.out.println(flattened);

        assertEquals("object", flattened.get(0).eitherSchema.getFirst().type);
        assertEquals(true, flattened.get(0).path.isEmpty());

        assertEquals("string", flattened.get(1).eitherSchema.getFirst().type);
        assertEquals("id", flattened.get(1).path.getHead().getKey());

        assertEquals("string", flattened.get(2).eitherSchema.getFirst().type);
        assertEquals("description", flattened.get(2).path.getHead().getKey());

        assertEquals("string", flattened.get(3).eitherSchema.getFirst().type);
        assertEquals("type", flattened.get(3).path.getHead().getKey());

        assertEquals("object", flattened.get(4).eitherSchema.getFirst().type);
        assertEquals("properties", flattened.get(4).path.getHead().getKey());

        assertEquals("object", flattened.get(5).eitherSchema.getFirst().type);
        assertEquals("latitude", flattened.get(5).path.getHead().getKey());

        assertEquals("string", flattened.get(6).eitherSchema.getFirst().type);
        assertEquals("type", flattened.get(6).path.getHead().getKey());

        assertEquals("object", flattened.get(7).eitherSchema.getFirst().type);
        assertEquals("longitude", flattened.get(7).path.getHead().getKey());

        assertEquals("string", flattened.get(8).eitherSchema.getFirst().type);
        assertEquals("type", flattened.get(8).path.getHead().getKey());
    }

    @Test
    public void testValidTypes() throws IOException, TypeException {
        Session session = Session.loadDefaultSession();
        Schema schema = session.schemas.get("http://exathunk.net/schemas/schema");
        assertNotNull(schema);

        Validator validator = new TypeValidator();

        {
            JsonNode node = Loader.loadSchemaNode("geo");
            VContext context = Util.runValidator(validator, new PathTuple(schema, node));
            assertEquals(0, context.errors.size());
        }

        {
            JsonNode node = Util.parse("{ \"type\":\"object\" }");
            VContext context = Util.runValidator(validator, new PathTuple(schema, node));
            assertEquals(0, context.errors.size());
        }

        {
            JsonNode node = Util.parse("{ \"x\":\"object\" }");
            VContext context = Util.runValidator(validator, new PathTuple(schema, node));
            assertEquals(1, context.errors.size());
        }

        {
            JsonNode node = Util.parse("[1, 2]");
            VContext context = Util.runValidator(validator, new PathTuple(schema, node));
            assertEquals(3, context.errors.size());
        }
    }

    @Test
    public void testValidTypes2() throws IOException, TypeException {
        Session session = Session.loadDefaultSession();
        Schema schema = session.schemas.get("http://exathunk.net/schemas/geo");
        assertNotNull("schema");

        Validator validator = new TypeValidator();

        {
            JsonNode node = Util.parse("{ \"latitude\": 3.14, \"longitude\": 5 }");
            VContext context = Util.runValidator(validator, new PathTuple(schema, node));
            assertEquals(0, context.errors.size());
        }

        {
            JsonNode node = Util.parse("{ \"latitude\": 3.14, \"longitude\": \"derp\" }");
            VContext context = Util.runValidator(validator, new PathTuple(schema, node));
            assertEquals(1, context.errors.size());
        }

        {
            JsonNode node = Util.parse("{ \"x\": [1,2,3] }");
            VContext context = Util.runValidator(validator, new PathTuple(schema, node));
            assertEquals(1, context.errors.size());
        }
    }

    @Test
    public void testRequired() throws IOException, TypeException {
        Session session = Session.loadDefaultSession();
        Schema schema = session.schemas.get("http://exathunk.net/schemas/schema");
        assertNotNull(schema);

        Validator validator = new RequiredValidator();

        {
            JsonNode node = Loader.loadSchemaNode("geo");
            VContext context = Util.runValidator(validator, new PathTuple(schema, node));
            assertEquals(0, context.errors.size());
        }

        {
            JsonNode node = Util.parse("{ \"type\": \"string\", \"id\":\"foo\" }");
            VContext context = Util.runValidator(validator, new PathTuple(schema, node));
            assertEquals(0, context.errors.size());
        }

        {
            JsonNode node = Util.parse("{ \"id\":\"foo\" }");
            VContext context = Util.runValidator(validator, new PathTuple(schema, node));
            assertEquals(1, context.errors.size());
        }
    }
}
