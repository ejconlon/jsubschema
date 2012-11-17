package net.exathunk.jsubschema.base;

import net.exathunk.jsubschema.gen.Loader;
import net.exathunk.jsubschema.genschema.*;
import org.codehaus.jackson.JsonNode;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * charolastra 11/15/12 12:37 PM
 */
public class TestPaths {

    @Test
    public void testPathSchema() throws IOException, TypeException, PathException {
        Session session = Session.loadDefaultSession();
        SchemaLike schema = session.schemas.get("http://exathunk.net/schemas/schema");
        assertNotNull(schema);

        Either<SchemaLike, String> reqSchema = Pather.pathSchema(schema, new Pointer().cons(Part.asKey("type")), new EmptyResolver());
        assertNotNull(reqSchema);
        assertEquals("string", reqSchema.getFirst().getType());

        Either<SchemaLike, String> idForbidSchema = Pather.pathSchema(schema, new Pointer().cons(Part.asKey("id")).cons(Part.asKey("forbids")).reversed(), new EmptyResolver());
        assertNotNull(idForbidSchema);
        assertEquals("array", idForbidSchema.getFirst().getType());

        Either<SchemaLike, String> idForbid0Schema = Pather.pathSchema(schema, new Pointer().cons(Part.asKey("id")).cons(Part.asKey("forbids")).cons(Part.asIndex(0)).reversed(), new EmptyResolver());
        assertNotNull(idForbid0Schema);
        assertEquals("string", idForbid0Schema.getFirst().getType());
    }

    @Test
    public void testPathNode() throws IOException, TypeException, PathException {
        JsonNode node = Loader.loadSchemaNode("schema");

        JsonNode reqNode = Pather.pathNode(node, new Pointer().cons(Part.asKey("type")));
        assertNotNull(reqNode);
        assertEquals("object", reqNode.asText());

        JsonNode idForbidNode = Pather.pathNode(node, new Pointer().cons(Part.asKey("properties")).cons(Part.asKey("id")).cons(Part.asKey("forbids")).reversed());
        assertNotNull(idForbidNode);
        assertEquals(1, idForbidNode.size());

        JsonNode idForbid0Node = Pather.pathNode(node, new Pointer().cons(Part.asKey("properties")).cons(Part.asKey("id")).cons(Part.asKey("forbids")).cons(Part.asIndex(0)).reversed());
        assertNotNull(idForbid0Node);
        assertEquals("$ref", idForbid0Node.asText());
    }

    @Test
    public void testTupling() throws IOException, TypeException, PathException {
        Session session = Session.loadDefaultSession();
        SchemaLike schema = session.schemas.get("http://exathunk.net/schemas/schema");
        assertNotNull(schema);

        JsonNode node = Loader.loadSchemaNode("geo");

        List<PathTuple> flattened = Util.asList(Util.withSelfDepthFirst(new PathTuple(schema, node, new EmptyResolver())));
        //System.out.println(flattened);

        assertEquals("object", flattened.get(0).eitherSchema.getFirst().getType());
        assertEquals(true, flattened.get(0).reference.getPointer().isEmpty());

        assertEquals("string", flattened.get(1).eitherSchema.getFirst().getType());
        assertEquals("id", flattened.get(1).reference.getPointer().getHead().getKey());

        assertEquals("string", flattened.get(2).eitherSchema.getFirst().getType());
        assertEquals("description", flattened.get(2).reference.getPointer().getHead().getKey());

        assertEquals("string", flattened.get(3).eitherSchema.getFirst().getType());
        assertEquals("type", flattened.get(3).reference.getPointer().getHead().getKey());

        assertEquals("object", flattened.get(4).eitherSchema.getFirst().getType());
        assertEquals("properties", flattened.get(4).reference.getPointer().getHead().getKey());

        assertEquals("object", flattened.get(5).eitherSchema.getFirst().getType());
        assertEquals("latitude", flattened.get(5).reference.getPointer().getHead().getKey());

        assertEquals("string", flattened.get(6).eitherSchema.getFirst().getType());
        assertEquals("type", flattened.get(6).reference.getPointer().getHead().getKey());

        assertEquals("object", flattened.get(7).eitherSchema.getFirst().getType());
        assertEquals("longitude", flattened.get(7).reference.getPointer().getHead().getKey());

        assertEquals("string", flattened.get(8).eitherSchema.getFirst().getType());
        assertEquals("type", flattened.get(8).reference.getPointer().getHead().getKey());
    }

    @Test
    public void testEventGeoRef() throws TypeException, IOException {
        Session session = Session.loadDefaultSession();
        RefResolver resolver = new SessionResolver(session);

        Event event = new Event();
        event.setDtstart("x");
        event.setDtend("x");
        event.setSummary("x");
        event.setLocation("x");
        event.setUrl("x");
        event.setRdate("x");
        event.setDuration("x");
        event.setRrule("x");
        event.setCategory("x");
        event.setDescription("x");
        Geo geo = new Geo();
        geo.setLatitude(3.14);
        geo.setLongitude(3.14);
        event.setGeo(geo);

        JsonNode node = Util.quickUnbind(event);
        //System.out.println(node);

        SchemaLike schema = resolver.resolveRef("http://exathunk.net/schemas/event").getFirst();

        List<PathTuple> flattened = Util.asList(Util.withSelfDepthFirst(new PathTuple(schema, node, resolver)));

        assertEquals("object", flattened.get(0).eitherSchema.getFirst().getType());
        assertEquals(true, flattened.get(0).reference.getPointer().isEmpty());

        assertEquals("string", flattened.get(1).eitherSchema.getFirst().getType());
        assertEquals("dtstart", flattened.get(1).reference.getPointer().getHead().getKey());

        assertEquals("string", flattened.get(2).eitherSchema.getFirst().getType());
        assertEquals("dtend", flattened.get(2).reference.getPointer().getHead().getKey());

        assertEquals("string", flattened.get(3).eitherSchema.getFirst().getType());
        assertEquals("summary", flattened.get(3).reference.getPointer().getHead().getKey());

        assertEquals("string", flattened.get(4).eitherSchema.getFirst().getType());
        assertEquals("location", flattened.get(4).reference.getPointer().getHead().getKey());

        assertEquals("string", flattened.get(5).eitherSchema.getFirst().getType());
        assertEquals("url", flattened.get(5).reference.getPointer().getHead().getKey());

        assertEquals("string", flattened.get(6).eitherSchema.getFirst().getType());
        assertEquals("duration", flattened.get(6).reference.getPointer().getHead().getKey());

        assertEquals("string", flattened.get(7).eitherSchema.getFirst().getType());
        assertEquals("rdate", flattened.get(7).reference.getPointer().getHead().getKey());

        assertEquals("string", flattened.get(8).eitherSchema.getFirst().getType());
        assertEquals("rrule", flattened.get(8).reference.getPointer().getHead().getKey());

        assertEquals("string", flattened.get(9).eitherSchema.getFirst().getType());
        assertEquals("category", flattened.get(9).reference.getPointer().getHead().getKey());

        assertEquals("string", flattened.get(10).eitherSchema.getFirst().getType());
        assertEquals("description", flattened.get(10).reference.getPointer().getHead().getKey());

        assertEquals("object", flattened.get(11).eitherSchema.getFirst().getType());
        assertEquals("geo", flattened.get(11).reference.getPointer().getHead().getKey());

        assertEquals("number", flattened.get(12).eitherSchema.getFirst().getType());
        assertEquals("latitude", flattened.get(12).reference.getPointer().getHead().getKey());

        assertEquals("number", flattened.get(13).eitherSchema.getFirst().getType());
        assertEquals("longitude", flattened.get(13).reference.getPointer().getHead().getKey());

        Validator validator = new DefaultValidator();
        VContext context = Util.runValidator(validator, new PathTuple(schema, node, resolver));
        assertEquals(new ArrayList<VError>(), context.errors);
    }
}
