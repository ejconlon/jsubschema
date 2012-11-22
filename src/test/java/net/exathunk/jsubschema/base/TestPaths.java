package net.exathunk.jsubschema.base;

import net.exathunk.jsubschema.Util;
import net.exathunk.jsubschema.functional.Either;
import net.exathunk.jsubschema.gen.Loader;
import net.exathunk.jsubschema.genschema.event.Event;
import net.exathunk.jsubschema.genschema.geo.Geo;
import net.exathunk.jsubschema.genschema.schema.SchemaLike;
import net.exathunk.jsubschema.pointers.*;
import net.exathunk.jsubschema.validation.DefaultValidator;
import net.exathunk.jsubschema.validation.VContext;
import net.exathunk.jsubschema.validation.VError;
import net.exathunk.jsubschema.validation.Validator;
import org.codehaus.jackson.JsonNode;
import org.junit.Test;

import java.io.IOException;
import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * charolastra 11/15/12 12:37 PM
 */
public class TestPaths {

    @Test
    public void testPathNode() throws IOException, TypeException, PathException {
        JsonNode node = Loader.loadSchemaNode("schema");

        JsonNode reqNode = Pather.pathNode(node, new Pointer().cons(Part.asKey("type")));
        assertNotNull(reqNode);
        assertEquals("object", reqNode.asText());

        JsonNode idForbidNode = Pather.pathNode(node, new Pointer().cons(Part.asKey("forbids")).cons(Part.asKey("id")));
        assertNotNull(idForbidNode);
        assertEquals(1, idForbidNode.size());

        JsonNode idForbid0Node = Pather.pathNode(node, new Pointer().cons(Part.asKey("forbids")).cons(Part.asKey("id")).cons(Part.asIndex(0)));
        assertNotNull(idForbid0Node);
        assertEquals("$ref", idForbid0Node.asText());
    }

    private static final Util.Func<PointedNode, String> refTupleRefStrings = new Util.Func<PointedNode, String>() {
        @Override
        public String runFunc(PointedNode pointedNode) {
            return "#"+pointedNode.getPointer().toPointerString();
        }
    };

    private static final Util.Func<PointedNode, String> refTupleStringVals = new Util.Func<PointedNode, String>() {
        @Override
        public String runFunc(PointedNode pointedNode) {
            if (pointedNode.getNode().isTextual()) {
                return pointedNode.getNode().asText();
            } else {
                return null;
            }
        }
    };

    @Test
    public void testTupling() throws IOException {
        JsonNode node = Loader.loadSchemaNode("geo");

        List<PointedNode> flattened = Util.asList(Util.withSelfDepthFirst(new PointedNode(node)));

        List<String> goldRefStrings = Arrays.asList(
                "#", "#/id", "#/description", "#/type", "#/properties",
                "#/properties/latitude", "#/properties/latitude/type",
                "#/properties/longitude", "#/properties/longitude/type");

        List<String> actualRefStrings = Util.map(refTupleRefStrings, flattened);

        assertEquals(goldRefStrings, actualRefStrings);
    }

    @Test
    public void testTupling2() throws IOException, TypeException {
        Session session = Session.loadDefaultSession();
        SchemaLike schema = session.schemas.get("http://exathunk.net/schemas/proptree");
        assertNotNull(schema);

        JsonNode node = Loader.loadNode("/test/testproptree");
        //List<RefTuple> flattened = Util.asList(Util.withSelfDepthFirst(new RefTuple(node)));
        List<SchemaNode> flattened = flatten(schema, node, new MetaResolver(new SelfResolver(schema)));

        List<String> goldRefStrings = Arrays.asList(
                "#", "#/props", "#/props/name", "#/props/food", "#/children",
                "#/children/0", "#/children/0/props", "#/children/0/props/name", "#/children/0/props/location",
                "#/children/0/children", "#/children/0/children/0", "#/children/0/children/0/props",
                "#/children/0/children/0/props/name", "#/children/0/children/0/props/born",
                "#/children/0/children/0/children", "#/children/0/children/0/children/0",
                "#/children/0/children/0/children/0/props", "#/children/0/children/0/children/0/props/name",
                "#/children/0/children/0/children/0/props/savior",
                "#/children/1", "#/children/1/props", "#/children/1/props/name", "#/children/1/props/food");

        List<String> actualRefStrings = Util.map(schemaTupleRefStrings, flattened);

        assertEquals(goldRefStrings, actualRefStrings);

        List<String> goldStringVals = Arrays.asList(
                null, null, "Pat", "steak", null,
                null, null, "Eric", "Saturn",
                null, null, null,
                "River Rock", "no",
                null, null,
                null, "Jesus Jr.", "yes",
                null, null, "Alec", "taters");

        List<String> actualStringVals = Util.map(schemaTupleStringVals, flattened);

        assertEquals(goldStringVals, actualStringVals);

        List<String> goldTypes = Arrays.asList(
                "object", "object", "string", "string", "array",
                "object", "object", "string", "string",
                "array", "object", "object",
                "string", "string",
                "array", "object",
                "object", "string", "string",
                "object", "object", "string", "string");

        List<String> actualTypes = Util.map(schemaTupleTypes, flattened);

        assertEquals(goldTypes, actualTypes);

        FullRefResolver fullRefResolver = new MetaResolver(new SelfResolver(schema));
        Validator validator = new DefaultValidator();
        VContext context = Util.runValidator(validator, new SchemaNode(schema, new PointedNode(node), fullRefResolver));
        assertEquals(new ArrayList<VError>(), context.errors);
    }

    @Test
    public void testTupling3() throws IOException, TypeException {
        Session session = Session.loadDefaultSession();
        SchemaLike schema = session.schemas.get("http://exathunk.net/schemas/proptree");
        assertNotNull(schema);

        JsonNode node = Loader.loadNode("/test/testproptree2");
        List<SchemaNode> flattened = flatten(schema, node, new MetaResolver(new SelfResolver(schema)));

        List<String> goldRefStrings = Arrays.asList(
                "#", "#/props", "#/props/name", "#/children",
                "#/children/0", "#/children/0/props", "#/children/0/props/name");

        List<String> actualRefStrings = Util.map(schemaTupleRefStrings, flattened);

        assertEquals(goldRefStrings, actualRefStrings);

        List<String> goldStringVals = Arrays.asList(
                null, null, "parent", null,
                null, null, "child");

        List<String> actualStringVals = Util.map(schemaTupleStringVals, flattened);

        assertEquals(goldStringVals, actualStringVals);

        List<String> goldTypes = Arrays.asList(
                "object", "object", "string", "array",
                "object", "object", "string");

        List<String> actualTypes = Util.map(schemaTupleTypes, flattened);

        assertEquals(goldTypes, actualTypes);

        final String x = "http://exathunk.net/schemas/proptree";
        List<String> goldSchemaRefs = Arrays.asList(
                x+"#", x+"#/properties/props", x+"#/properties/props/items", x+"#/properties/children",
                x+"#", x+"#/properties/props", x+"#/properties/props/items");

        List<String> actualSchemaRefs = Util.map(schemaTupleSchemaRefs, flattened);

        assertEquals(goldSchemaRefs, actualSchemaRefs);

        FullRefResolver fullRefResolver = new MetaResolver(new SelfResolver(schema));
        Validator validator = new DefaultValidator();
        VContext context = Util.runValidator(validator, new SchemaNode(schema, new PointedNode(node), fullRefResolver));
        assertEquals(new ArrayList<VError>(), context.errors);
    }

    @Test
    public void testTupling4() throws IOException, TypeException {
        Session session = Session.loadDefaultSession();
        SchemaLike schema = session.schemas.get("http://exathunk.net/schemas/stringmultimap");
        assertNotNull(schema);

        JsonNode node = Loader.loadNode("/test/teststringmultimap");
        List<PointedNode> flattened = Util.asList(Util.withSelfDepthFirst(new PointedNode(node)));

        List<String> goldRefStrings = Arrays.asList(
                "#", "#/a", "#/a/0", "#/a/1", "#/b", "#/b/0", "#/c");

        List<String> actualRefStrings = Util.map(refTupleRefStrings, flattened);

        assertEquals(goldRefStrings, actualRefStrings);
    }

    private static Either<SchemaRef, String> resolve(FullRefResolver fullRefResolver, String ref) {
        Either<Reference, String> eitherRef = Reference.fromReferenceString(ref);
        if (eitherRef.isSecond()) return Either.makeSecond(eitherRef.getSecond());
        PointedRef pointedRef = new PointedRef(Reference.fromId(eitherRef.getFirst().getUrl()), eitherRef.getFirst().getPointer().reversed());
        return fullRefResolver.fullyResolveRef(pointedRef);
    }

    @Test
    public void testTupling5() throws IOException, TypeException {
        Session session = Session.loadDefaultSession();
        SchemaLike schema = session.schemas.get("http://exathunk.net/schemas/stringmultimap");
        assertNotNull(schema);

        JsonNode node = Loader.loadNode("/test/teststringmultimap");
        List<SchemaNode> flattened = flatten(schema, node, new MetaResolver(new SelfResolver(schema)));

        List<String> goldRefStrings = Arrays.asList(
                "#", "#/a", "#/a/0", "#/a/1", "#/b", "#/b/0", "#/c");

        List<String> actualRefStrings = Util.map(schemaTupleRefStrings, flattened);

        assertEquals(goldRefStrings, actualRefStrings);

        List<String> goldStringVals = Arrays.asList(
                null, null, "alpha", "apple", null, "beta", null);

        List<String> actualStringVals = Util.map(schemaTupleStringVals, flattened);

        assertEquals(goldStringVals, actualStringVals);

        List<String> goldTypes = Arrays.asList(
                "object", "array", "string", "string", "array", "string", "array");

        List<String> actualTypes = Util.map(schemaTupleTypes, flattened);

        assertEquals(goldTypes, actualTypes);

        final String x = "http://exathunk.net/schemas/stringmultimap";
        List<String> goldSchemaRefs = Arrays.asList(
                x+"#", x+"#/declarations/stringArray", x+"#/declarations/stringArray/items", x+"#/declarations/stringArray/items",
                x+"#/declarations/stringArray", x+"#/declarations/stringArray/items", x+"#/declarations/stringArray");

        List<String> actualSchemaRefs = Util.map(schemaTupleSchemaRefs, flattened);

        assertEquals(goldSchemaRefs, actualSchemaRefs);

        FullRefResolver fullRefResolver = new MetaResolver(new SelfResolver(schema));
        Validator validator = new DefaultValidator();
        VContext context = Util.runValidator(validator, new SchemaNode(schema, new PointedNode(node), fullRefResolver));
        assertEquals(new ArrayList<VError>(), context.errors);
    }

    private static final Util.Func<SchemaNode, String> schemaTupleRefStrings = new Util.Func<SchemaNode, String>() {
        @Override
        public String runFunc(SchemaNode schemaNode) {
            return refTupleRefStrings.runFunc(schemaNode.getPointedNode());
        }
    };

    private static final Util.Func<SchemaNode, String> schemaTupleTypes = new Util.Func<SchemaNode, String>() {
        @Override
        public String runFunc(SchemaNode schemaNode) {
            if (schemaNode.getEitherSchema().isFirst()) {
                return schemaNode.getEitherSchema().getFirst().getSchema().getType();
            } else {
                return "ERROR: "+ schemaNode.getEitherSchema().getSecond();
            }
        }
    };

    private static final Util.Func<SchemaNode, String> schemaTupleSchemaRefs = new Util.Func<SchemaNode, String>() {
        @Override
        public String runFunc(SchemaNode schemaNode) {
            if (schemaNode.getEitherSchema().isFirst()) {
                return schemaNode.getEitherSchema().getFirst().getReference().toReferenceString();
            } else {
                return "ERROR: "+ schemaNode.getEitherSchema().getSecond();
            }
        }
    };

    private static final Util.Func<SchemaNode, String> schemaTupleStringVals = new Util.Func<SchemaNode, String>() {
        @Override
        public String runFunc(SchemaNode schemaNode) {
            return refTupleStringVals.runFunc(schemaNode.getPointedNode());
        }
    };

    private static List<SchemaNode> flatten(SchemaLike schema, JsonNode node, FullRefResolver fullRefResolver) {
        return Util.asList(Util.withSelfDepthFirst(new SchemaNode(schema, new PointedNode(node), fullRefResolver)));
    }

    @Test
    public void testScheming() throws IOException, TypeException {
        Session session = Session.loadDefaultSession();
        SchemaLike schema = session.schemas.get("http://exathunk.net/schemas/schema");
        assertNotNull(schema);

        JsonNode node = Loader.loadSchemaNode("geo");

        List<SchemaNode> flattened = flatten(schema, node, new MetaResolver(new SelfResolver(schema)));

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
        SchemaLike schema = session.schemas.get("http://exathunk.net/schemas/event");
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

        List<SchemaNode> flattened = flatten(schema, node, fullRefResolver);

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

        Validator validator = new DefaultValidator();
        VContext context = Util.runValidator(validator, new SchemaNode(schema, new PointedNode(node), fullRefResolver));
        assertEquals(new ArrayList<VError>(), context.errors);
    }

    @Test
    public void testSelfResolver() throws IOException, TypeException {
        Session session = Session.loadDefaultSession();
        SchemaLike schema = session.schemas.get("http://exathunk.net/schemas/schema");
        assertNotNull(schema);
        RefResolver resolver = new SelfResolver(schema);

        for (Map.Entry<String, SchemaLike> entry : makeSelfCases(schema).entrySet()) {
            final String refString = entry.getKey();
            final SchemaLike subSchema = entry.getValue();
            final Either<SchemaRef, String> actual = resolve(new MetaResolver(Util.asList(resolver)), refString);
            assertEquals(refString, actual.getFirst().getSchema(), subSchema);
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
        SchemaLike eventSchema = session.schemas.get("http://exathunk.net/schemas/event");
        SchemaLike geoSchema = session.schemas.get("http://exathunk.net/schemas/geo");
        assertNotNull(eventSchema);
        assertNotNull(geoSchema);
        RefResolver resolver = new SessionResolver(session);
        RefResolver resolver2 = new SelfResolver(eventSchema);

        for (Map.Entry<String, SchemaLike> entry : makeSessionCases(eventSchema, geoSchema).entrySet()) {
            final String refString = entry.getKey();
            final SchemaLike subSchema = entry.getValue();
            // The second resolver captures relative references
            final Either<SchemaRef, String> actual = resolve(new MetaResolver(Util.asList(resolver, resolver2)), refString);
            assertEquals(refString, actual.getFirst().getSchema(), subSchema);
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
