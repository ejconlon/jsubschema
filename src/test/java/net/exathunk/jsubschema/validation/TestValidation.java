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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * charolastra 11/16/12 9:35 PM
 */
public class TestValidation {

    @Test
    public void testValidTypes() throws IOException, TypeException {
        Session session = Session.loadDefaultSession();
        SchemaLike schema = session.getSchema("http://exathunk.net/schemas/schema");
        assertNotNull(schema);

        FullRefResolver fullRefResolver = new MetaResolver(new SessionResolver(session), new SelfResolver(schema));
        Validator validator = new TypeValidator();

        {
            JsonNode node = Loader.loadSchemaNode("geo");
            VContext context = Util.runValidator(validator, new SchemaTuple(schema, new RefTuple(node), fullRefResolver));
            assertEquals(new ArrayList<VError>(), context.errors);
        }

        {
            JsonNode node = Util.parse("{ \"type\":\"object\" }");
            VContext context = Util.runValidator(validator, new SchemaTuple(schema, new RefTuple(node), fullRefResolver));
            assertEquals(new ArrayList<VError>(), context.errors);
        }

        {
            JsonNode node = Util.parse("{ \"x\":\"object\" }");
            VContext context = Util.runValidator(validator, new SchemaTuple(schema, new RefTuple(node), fullRefResolver));
            assertEquals(1, context.errors.size());
        }

        {
            JsonNode node = Util.parse("[1, 2]");
            VContext context = Util.runValidator(validator, new SchemaTuple(schema, new RefTuple(node), fullRefResolver));
            assertEquals(3, context.errors.size());
        }
    }

    @Test
    public void testValidTypes2() throws IOException, TypeException {
        Session session = Session.loadDefaultSession();
        SchemaLike schema = session.getSchema("http://exathunk.net/schemas/geo");
        assertNotNull("schema");

        FullRefResolver fullRefResolver = new MetaResolver(new SessionResolver(session), new SelfResolver(schema));
        Validator validator = new TypeValidator();

        {
            JsonNode node = Util.parse("{ \"latitude\": 3.14, \"longitude\": 5 }");
            VContext context = Util.runValidator(validator, new SchemaTuple(schema, new RefTuple(node), fullRefResolver));
            assertEquals(new ArrayList<VError>(), context.errors);
        }

        {
            JsonNode node = Util.parse("{ \"latitude\": 3.14, \"longitude\": \"derp\" }");
            VContext context = Util.runValidator(validator, new SchemaTuple(schema, new RefTuple(node), fullRefResolver));
            assertEquals(1, context.errors.size());
        }

        {
            JsonNode node = Util.parse("{ \"x\": [1,2,3] }");
            VContext context = Util.runValidator(validator, new SchemaTuple(schema, new RefTuple(node), fullRefResolver));
            assertEquals(1, context.errors.size());
        }
    }

    @Test
    public void testRequired() throws IOException, TypeException {
        Session session = Session.loadDefaultSession();
        SchemaLike schema = session.getSchema("http://exathunk.net/schemas/schema");
        assertNotNull(schema);

        FullRefResolver fullRefResolver = new MetaResolver(new SessionResolver(session), new SelfResolver(schema));
        Validator validator = new RequiredValidator();

        {
            JsonNode node = Loader.loadSchemaNode("geo");
            VContext context = Util.runValidator(validator, new SchemaTuple(schema, new RefTuple(node), fullRefResolver));
            assertEquals(new ArrayList<VError>(), context.errors);
        }

        {
            JsonNode node = Util.parse("{ \"type\": \"string\", \"id\":\"foo\" }");
            VContext context = Util.runValidator(validator, new SchemaTuple(schema, new RefTuple(node), fullRefResolver));
            assertEquals(new ArrayList<VError>(), context.errors);
        }

        {
            JsonNode node = Util.parse("{ \"id\":\"foo\" }");
            VContext context = Util.runValidator(validator, new SchemaTuple(schema, new RefTuple(node), fullRefResolver));
            assertEquals(2, context.errors.size());  // one for type and one for ref
        }

        {
            JsonNode node = Util.parse("{ \"type\": \"string\" }");
            VContext context = Util.runValidator(validator, new SchemaTuple(schema, new RefTuple(node), fullRefResolver));
            assertEquals(new ArrayList<VError>(), context.errors);
        }

        {
            JsonNode node = Util.parse("{ \"$ref\": \"bar\" }");
            VContext context = Util.runValidator(validator, new SchemaTuple(schema, new RefTuple(node), fullRefResolver));
            assertEquals(new ArrayList<VError>(), context.errors);
        }
    }

    @Test
    public void testForbidden() throws IOException, TypeException {
        Session session = Session.loadDefaultSession();
        SchemaLike schema = session.getSchema("http://exathunk.net/schemas/schema");
        assertNotNull(schema);

        FullRefResolver fullRefResolver = new MetaResolver(new SessionResolver(session), new SelfResolver(schema));
        Validator validator = new ForbidsValidator();

        {
            JsonNode node = Util.parse("{ \"type\": \"object\", \"id\":\"foo\" }");
            VContext context = Util.runValidator(validator, new SchemaTuple(schema, new RefTuple(node), fullRefResolver));
            assertEquals(new ArrayList<VError>(), context.errors);
        }

        {
            JsonNode node = Util.parse("{ \"$ref\":\"bar\" }");
            VContext context = Util.runValidator(validator, new SchemaTuple(schema, new RefTuple(node), fullRefResolver));
            assertEquals(new ArrayList<VError>(), context.errors);
        }

        {
            JsonNode node = Util.parse("{ \"id\":\"foo\", \"$ref\":\"bar\" }");
            VContext context = Util.runValidator(validator, new SchemaTuple(schema, new RefTuple(node), fullRefResolver));
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
            VContext context = Util.runValidator(validator, new SchemaTuple(schema, new RefTuple(node), fullRefResolver));
            assertEquals(1, context.errors.size());
        }

        {
            JsonNode node = Util.parse("{ \"b\":\"2\" }");
            VContext context = Util.runValidator(validator, new SchemaTuple(schema, new RefTuple(node), fullRefResolver));
            assertEquals(0, context.errors.size());
        }

        {
            JsonNode node = Util.parse("{ \"a\":\"1\", \"b\":\"2\" }");
            VContext context = Util.runValidator(validator, new SchemaTuple(schema, new RefTuple(node), fullRefResolver));
            assertEquals(0, context.errors.size());
        }
    }
}
