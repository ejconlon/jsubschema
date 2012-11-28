package net.exathunk.jsubschema.validation;

import net.exathunk.jsubschema.Util;
import net.exathunk.jsubschema.base.*;
import net.exathunk.jsubschema.gen.Loader;
import net.exathunk.jsubschema.genschema.schema.Schema;
import net.exathunk.jsubschema.genschema.schema.SchemaFactory;
import net.exathunk.jsubschema.genschema.schema.SchemaLike;
import org.codehaus.jackson.JsonNode;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * charolastra 11/16/12 9:35 PM
 */
public class TestValidation {

    @Test
    public void testValidTypes() throws IOException, TypeException {
        Session session = Session.loadDefaultSession();
        SchemaLike schema = session.schemas.get("http://exathunk.net/schemas/schema");
        assertNotNull(schema);

        FullRefResolver fullRefResolver = new MetaResolver(new SessionResolver(session), new SelfResolver(schema));
        Validator validator = new TypeValidator();

        {
            JsonNode node = Loader.loadSchemaNode("geo");
            VContext context = Util.runValidator(validator, new SchemaNode(schema, new PointedNode(node), fullRefResolver));
            assertEquals(new ArrayList<VError>(), context.errors);
        }

        {
            JsonNode node = Util.parse("{ \"type\":\"object\" }");
            VContext context = Util.runValidator(validator, new SchemaNode(schema, new PointedNode(node), fullRefResolver));
            assertEquals(new ArrayList<VError>(), context.errors);
        }

        {
            JsonNode node = Util.parse("{ \"x\":\"object\" }");
            VContext context = Util.runValidator(validator, new SchemaNode(schema, new PointedNode(node), fullRefResolver));
            assertEquals(1, context.errors.size());
        }

        {
            JsonNode node = Util.parse("[1, 2]");
            VContext context = Util.runValidator(validator, new SchemaNode(schema, new PointedNode(node), fullRefResolver));
            assertEquals(3, context.errors.size());
        }
    }

    @Test
    public void testValidTypes2() throws IOException, TypeException {
        Session session = Session.loadDefaultSession();
        SchemaLike schema = session.schemas.get("http://exathunk.net/schemas/geo");
        assertNotNull("schema");

        FullRefResolver fullRefResolver = new MetaResolver(new SessionResolver(session), new SelfResolver(schema));
        Validator validator = new TypeValidator();

        {
            JsonNode node = Util.parse("{ \"latitude\": 3.14, \"longitude\": 5 }");
            VContext context = Util.runValidator(validator, new SchemaNode(schema, new PointedNode(node), fullRefResolver));
            assertEquals(new ArrayList<VError>(), context.errors);
        }

        {
            JsonNode node = Util.parse("{ \"latitude\": 3.14, \"longitude\": \"derp\" }");
            VContext context = Util.runValidator(validator, new SchemaNode(schema, new PointedNode(node), fullRefResolver));
            assertEquals(1, context.errors.size());
        }

        {
            JsonNode node = Util.parse("{ \"x\": [1,2,3] }");
            VContext context = Util.runValidator(validator, new SchemaNode(schema, new PointedNode(node), fullRefResolver));
            assertEquals(1, context.errors.size());
        }
    }

    @Test
    public void testRequired() throws IOException, TypeException {
        Session session = Session.loadDefaultSession();
        SchemaLike schema = session.schemas.get("http://exathunk.net/schemas/schema");
        assertNotNull(schema);

        FullRefResolver fullRefResolver = new MetaResolver(new SessionResolver(session), new SelfResolver(schema));
        Validator validator = new RequiredValidator();

        {
            JsonNode node = Loader.loadSchemaNode("geo");
            VContext context = Util.runValidator(validator, new SchemaNode(schema, new PointedNode(node), fullRefResolver));
            assertEquals(new ArrayList<VError>(), context.errors);
        }

        {
            JsonNode node = Util.parse("{ \"type\": \"string\", \"id\":\"foo\" }");
            VContext context = Util.runValidator(validator, new SchemaNode(schema, new PointedNode(node), fullRefResolver));
            assertEquals(new ArrayList<VError>(), context.errors);
        }

        {
            JsonNode node = Util.parse("{ \"id\":\"foo\" }");
            VContext context = Util.runValidator(validator, new SchemaNode(schema, new PointedNode(node), fullRefResolver));
            assertEquals(2, context.errors.size());  // one for type and one for ref
        }

        {
            JsonNode node = Util.parse("{ \"type\": \"string\" }");
            VContext context = Util.runValidator(validator, new SchemaNode(schema, new PointedNode(node), fullRefResolver));
            assertEquals(new ArrayList<VError>(), context.errors);
        }

        {
            JsonNode node = Util.parse("{ \"$ref\": \"bar\" }");
            VContext context = Util.runValidator(validator, new SchemaNode(schema, new PointedNode(node), fullRefResolver));
            assertEquals(new ArrayList<VError>(), context.errors);
        }
    }

    @Test
    public void testForbidden() throws IOException, TypeException {
        Session session = Session.loadDefaultSession();
        SchemaLike schema = session.schemas.get("http://exathunk.net/schemas/schema");
        assertNotNull(schema);

        FullRefResolver fullRefResolver = new MetaResolver(new SessionResolver(session), new SelfResolver(schema));
        Validator validator = new ForbidsValidator();

        {
            JsonNode node = Util.parse("{ \"type\": \"object\", \"id\":\"foo\" }");
            VContext context = Util.runValidator(validator, new SchemaNode(schema, new PointedNode(node), fullRefResolver));
            assertEquals(new ArrayList<VError>(), context.errors);
        }

        {
            JsonNode node = Util.parse("{ \"$ref\":\"bar\" }");
            VContext context = Util.runValidator(validator, new SchemaNode(schema, new PointedNode(node), fullRefResolver));
            assertEquals(new ArrayList<VError>(), context.errors);
        }

        {
            JsonNode node = Util.parse("{ \"id\":\"foo\", \"$ref\":\"bar\" }");
            VContext context = Util.runValidator(validator, new SchemaNode(schema, new PointedNode(node), fullRefResolver));
            assertEquals(2, context.errors.size());
        }
    }

    @Test
    public void testDependencies() throws IOException, TypeException {
        JsonNode schemaNode = Util.parse("{\"type\":\"object\", \"dependencies\": {\"a\":[\"b\"], \"b\":[\"b\"]}, \"properties\": { \"a\": {\"type\":\"integer\"}, \"b\": {\"type\":\"integer\"} }}");
        Schema schema = Util.quickBind(schemaNode, new SchemaFactory());
        assertNotNull(schema);

        FullRefResolver fullRefResolver = new MetaResolver(new SelfResolver(schema));
        Validator validator = new DependenciesValidator();

        {
            JsonNode node = Util.parse("{ \"a\":\"1\" }");
            VContext context = Util.runValidator(validator, new SchemaNode(schema, new PointedNode(node), fullRefResolver));
            assertEquals(1, context.errors.size());
        }

        {
            JsonNode node = Util.parse("{ \"b\":\"2\" }");
            VContext context = Util.runValidator(validator, new SchemaNode(schema, new PointedNode(node), fullRefResolver));
            assertEquals(0, context.errors.size());
        }

        {
            JsonNode node = Util.parse("{ \"a\":\"1\", \"b\":\"2\" }");
            VContext context = Util.runValidator(validator, new SchemaNode(schema, new PointedNode(node), fullRefResolver));
            assertEquals(0, context.errors.size());
        }
    }

    @Test
    public void testRefs() throws IOException, TypeException {
        Session session = Session.loadDefaultSession();
        SchemaLike schema = session.schemas.get("http://exathunk.net/schemas/schema");
        assertNotNull(schema);
        FullRefResolver fullRefResolver = new MetaResolver(new SessionResolver(session), new SelfResolver(schema));
        Validator validator = new RefValidator();

        List<String> keys = Util.asList("$ref", "$schema", "$instance");
        List<String> values = Util.asList("http://exathunk.net/schemas/schema", "http://whatever.com/not/here");

        for (final String key : keys) {
            for (int i = 0; i < values.size(); ++i) {
                final String value = values.get(i);
                JsonNode node = Util.parse("{ \""+key+"\": \""+value+"\" }");
                VContext context = Util.runValidator(validator, new SchemaNode(schema, new PointedNode(node), fullRefResolver));
                assertEquals(i, context.errors.size());
            }
        }

    }

    @Test
    public void testStringEnumType() throws IOException, TypeException {
        Session session = Session.loadDefaultSession();
        SchemaLike schema = session.schemas.get("http://exathunk.net/schemas/schema");
        assertNotNull(schema);
        FullRefResolver fullRefResolver = new MetaResolver(new SessionResolver(session), new SelfResolver(schema));
        Validator validator = new StringEnumTypeValidator();

        List<String> types = Util.asList("object", "array", "string", "boolean", "integer", "number");

        for (String type : types) {
            boolean isString = "string".equals(type);
            int expectedNum = (isString) ? 0 : 1;
            String str = "{ \"stringEnum\":[\"a\",\"b\",\"c\"], \"type\":\""+type+"\" }";
            JsonNode node = Util.parse(str);
            VContext context = Util.runValidator(validator, new SchemaNode(schema, new PointedNode(node), fullRefResolver));
            assertEquals(str, expectedNum, context.errors.size());
        }
    }

    @Test
    public void testStringEnumValue() throws IOException, TypeException {
        Session session = Session.loadDefaultSession();
        SchemaLike schema = session.schemas.get("http://exathunk.net/schemas/schema");
        assertNotNull(schema);
        FullRefResolver fullRefResolver = new MetaResolver(new SessionResolver(session), new SelfResolver(schema));
        Validator validator = new StringEnumValueValidator();

        List<String> types = Util.asList("object", "array", "string", "boolean", "integer", "number", "foo", "bar", "baz");

        for (String type : types) {
                int expectedNum = (schema.getProperties().get("type").getStringEnum().contains(type) ? 0 : 1);
                String str = "{ \"type\":\""+type+"\" }";
                JsonNode node = Util.parse(str);
                VContext context = Util.runValidator(validator, new SchemaNode(schema, new PointedNode(node), fullRefResolver));
                assertEquals(str, expectedNum, context.errors.size());
        }
    }

    @Test
    public void testValidateSession() throws IOException, TypeException {
        Session session = Session.loadDefaultSession();
        assertEquals(new ArrayList<VError>(), session.validate().errors);
    }
}
