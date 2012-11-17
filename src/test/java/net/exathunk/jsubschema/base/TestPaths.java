package net.exathunk.jsubschema.base;

import net.exathunk.jsubschema.Util;
import net.exathunk.jsubschema.functional.Either;
import net.exathunk.jsubschema.functional.Either3;
import net.exathunk.jsubschema.gen.Loader;
import net.exathunk.jsubschema.genschema.Event;
import net.exathunk.jsubschema.genschema.Geo;
import net.exathunk.jsubschema.genschema.SchemaLike;
import net.exathunk.jsubschema.pointers.Part;
import net.exathunk.jsubschema.pointers.Pointer;
import net.exathunk.jsubschema.pointers.Reference;
import org.codehaus.jackson.JsonNode;
import org.junit.Test;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * charolastra 11/15/12 12:37 PM
 */
public class TestPaths {

    @Test
    public void testPathSchema() throws IOException, TypeException, PathException {
        Session session = Session.loadDefaultSession();
        SchemaLike schema = session.getSchema("http://exathunk.net/schemas/schema");
        assertNotNull(schema);

        Either3<SchemaLike, String, Reference> reqSchema = Pather.pathSchema(schema, new Reference("", new Pointer().cons(Part.asKey("type"))));
        assertNotNull(reqSchema);
        assertEquals("string", reqSchema.getFirst().getType());

        Either3<SchemaLike, String, Reference> idForbidSchema = Pather.pathSchema(schema, new Reference("", new Pointer().cons(Part.asKey("id")).cons(Part.asKey("forbids"))));
        assertNotNull(idForbidSchema);
        assertEquals("array", idForbidSchema.getFirst().getType());

        Either3<SchemaLike, String, Reference> idForbid0Schema = Pather.pathSchema(schema, new Reference("", new Pointer().cons(Part.asKey("id")).cons(Part.asKey("forbids")).cons(Part.asIndex(0))));
        assertNotNull(idForbid0Schema);
        assertEquals("string", idForbid0Schema.getFirst().getType());
    }

    @Test
    public void testPathNode() throws IOException, TypeException, PathException {
        JsonNode node = Loader.loadSchemaNode("schema");

        JsonNode reqNode = Pather.pathNode(node, new Pointer().cons(Part.asKey("type")));
        assertNotNull(reqNode);
        assertEquals("object", reqNode.asText());

        JsonNode idForbidNode = Pather.pathNode(node, new Pointer().cons(Part.asKey("properties")).cons(Part.asKey("id")).cons(Part.asKey("forbids")));
        assertNotNull(idForbidNode);
        assertEquals(1, idForbidNode.size());

        JsonNode idForbid0Node = Pather.pathNode(node, new Pointer().cons(Part.asKey("properties")).cons(Part.asKey("id")).cons(Part.asKey("forbids")).cons(Part.asIndex(0)));
        assertNotNull(idForbid0Node);
        assertEquals("$ref", idForbid0Node.asText());
    }

    private static final Util.Func<RefTuple, String> refTupleRefStrings = new Util.Func<RefTuple, String>() {
        @Override
        public String runFunc(RefTuple refTuple) {
            return refTuple.getReference().toReferenceString();
        }
    };

    @Test
    public void testTupling() throws IOException {
        JsonNode node = Loader.loadSchemaNode("geo");

        List<RefTuple> flattened = Util.asList(Util.withSelfDepthFirst(new RefTuple(node)));

        List<String> goldRefStrings = Arrays.asList(
                "#", "#/id", "#/description", "#/type", "#/properties",
                "#/properties/latitude", "#/properties/latitude/type",
                "#/properties/longitude", "#/properties/longitude/type");

        List<String> actualRefStrings = Util.map(refTupleRefStrings, flattened);

        assertEquals(goldRefStrings, actualRefStrings);
    }

    private static final Util.Func<SchemaTuple, String> schemaTupleRefStrings = new Util.Func<SchemaTuple, String>() {
        @Override
        public String runFunc(SchemaTuple schemaTuple) {
            return schemaTuple.getRefTuple().getReference().toReferenceString();
        }
    };

    private static final Util.Func<SchemaTuple, String> schemaTupleTypes = new Util.Func<SchemaTuple, String>() {
        @Override
        public String runFunc(SchemaTuple schemaTuple) {
            return schemaTuple.getEitherSchema().getFirst().getType();
        }
    };

    private static List<SchemaTuple> flatten(SchemaLike schema, JsonNode node, FullRefResolver fullRefResolver) {
        return Util.asList(Util.withSelfDepthFirst(new SchemaTuple(schema, new RefTuple(node), fullRefResolver)));
    }

    @Test
    public void testScheming() throws IOException, TypeException {
        Session session = Session.loadDefaultSession();
        SchemaLike schema = session.getSchema("http://exathunk.net/schemas/schema");
        assertNotNull(schema);

        JsonNode node = Loader.loadSchemaNode("geo");

        List<SchemaTuple> flattened = flatten(schema, node, new MetaResolver(new SelfResolver(schema)));

        List<String> goldRefStrings = Arrays.asList(
                "#", "#/id", "#/description", "#/type", "#/properties",
                "#/properties/latitude", "#/properties/latitude/type",
                "#/properties/longitude", "#/properties/longitude/type");

        List<String> goldTypes = Arrays.asList(
                "object", "string", "string", "string", "object",
                "object", "string", "object", "string");

        List<String> actualRefStrings = Util.map(schemaTupleRefStrings, flattened);

        assertEquals(goldRefStrings, actualRefStrings);

        List<String> actualTypes = Util.map(schemaTupleTypes, flattened);

        assertEquals(goldTypes, actualTypes);
    }

    @Test
    public void testEventGeoRef() throws TypeException, IOException {
        Session session = Session.loadDefaultSession();
        SchemaLike schema = session.getSchema("http://exathunk.net/schemas/event");
        assertNotNull(schema);
        FullRefResolver fullRefResolver = new MetaResolver(new SessionResolver(session), new SelfResolver(schema));

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

        List<SchemaTuple> flattened = flatten(schema, node, fullRefResolver);

        List<String> goldRefStrings = Arrays.asList(
             "#", "#/dtstart", "#/dtend", "#/summary", "#/location",
             "#/url", "#/duration", "#/rdate", "#/rrule", "#/category",
             "#/description", "#/geo", "#/geo/latitude", "#/geo/longitude"
        );
        List<String> goldTypes = Arrays.asList(
             "object", "string", "string", "string", "string",
             "string", "string", "string", "string", "string",
             "string", "object", "number", "number"
        );

        List<String> actualRefStrings = Util.map(schemaTupleRefStrings, flattened);

        assertEquals(goldRefStrings, actualRefStrings);

        List<String> actualTypes = Util.map(schemaTupleTypes, flattened);

        assertEquals(goldTypes, actualTypes);

        //Validator validator = new DefaultValidator();
        //VContext context = Util.runValidator(validator, new RefTuple(schema, node), new MetaResolver(new SessionResolver(session)));
        //assertEquals(new ArrayList<VError>(), context.errors);
    }

    @Test
    public void testSelfResolver() throws IOException, TypeException {
        Session session = Session.loadDefaultSession();
        SchemaLike schema = session.getSchema("http://exathunk.net/schemas/schema");
        assertNotNull(schema);
        RefResolver resolver = new SelfResolver(schema);

        for (Map.Entry<String, SchemaLike> entry : makeSelfCases(schema).entrySet()) {
            final String refString = entry.getKey();
            final SchemaLike subSchema = entry.getValue();
            final Either<SchemaLike, String> actual = MetaResolver.resolveRefString(refString, new MetaResolver(Util.asList(resolver)));
            assertEquals(refString, actual.getFirst(), subSchema);
        }
    }

    private static Map<String, SchemaLike> makeSelfCases(SchemaLike schema) {
        final SchemaLike typeSchema = schema.getProperties().get("type");
        final SchemaLike propertiesSchema = schema.getProperties().get("properties");
        assert !schema.equals(typeSchema);
        assert !schema.equals(propertiesSchema);
        assert !typeSchema.equals(propertiesSchema);
        Map<String, SchemaLike> cs = new HashMap<String, SchemaLike>();
        for (String url : Util.asSet("", schema.getId())) {
            cs.put(url+"", schema);
            cs.put(url+"#/type", typeSchema);
            cs.put(url+"#/properties", propertiesSchema);
            cs.put(url+"#/properties/items", schema);
            cs.put(url+"#/properties/items/type", typeSchema);
        }
        return cs;
    }

    @Test
    public void testSessionResolver() throws IOException, TypeException {
        Session session = Session.loadDefaultSession();
        SchemaLike eventSchema = session.getSchema("http://exathunk.net/schemas/event");
        SchemaLike geoSchema = session.getSchema("http://exathunk.net/schemas/geo");
        assertNotNull(eventSchema);
        assertNotNull(geoSchema);
        RefResolver resolver = new SessionResolver(session);
        RefResolver resolver2 = new SelfResolver(eventSchema);

        for (Map.Entry<String, SchemaLike> entry : makeSessionCases(eventSchema, geoSchema).entrySet()) {
            final String refString = entry.getKey();
            final SchemaLike subSchema = entry.getValue();
            // The second resolver captures relative references
            final Either<SchemaLike, String> actual = MetaResolver.resolveRefString(refString, new MetaResolver(Util.asList(resolver, resolver2)));
            assertEquals(refString, actual.getFirst(), subSchema);
        }
    }

    private static Map<String, SchemaLike> makeSessionCases(SchemaLike schema, SchemaLike geoSchema) {
        final SchemaLike latitudeSchema = geoSchema.getProperties().get("latitude");
        assert !schema.equals(geoSchema);
        assert !schema.equals(latitudeSchema);
        assert !geoSchema.equals(latitudeSchema);
        Map<String, SchemaLike> cs = new HashMap<String, SchemaLike>();
        for (String url : Util.asSet("", schema.getId())) {
            cs.put(url+"", schema);
            cs.put(url+"#/geo", geoSchema);
            cs.put(url+"#/geo/latitude", latitudeSchema);
        }
        return cs;
    }
}
