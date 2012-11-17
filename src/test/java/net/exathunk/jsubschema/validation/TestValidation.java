package net.exathunk.jsubschema.validation;

import net.exathunk.jsubschema.base.*;
import net.exathunk.jsubschema.gen.Loader;
import net.exathunk.jsubschema.genschema.Schema;
import net.exathunk.jsubschema.genschema.SchemaFactory;
import net.exathunk.jsubschema.genschema.SchemaLike;
import net.exathunk.jsubschema.validation.*;
import org.codehaus.jackson.JsonNode;
import org.junit.Test;

import java.io.IOException;

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

        Validator validator = new TypeValidator();

        {
            JsonNode node = Loader.loadSchemaNode("geo");
            VContext context = Util.runValidator(validator, new PathTuple(schema, node, new EmptyResolver()));
            assertEquals(0, context.errors.size());
        }

        {
            JsonNode node = Util.parse("{ \"type\":\"object\" }");
            VContext context = Util.runValidator(validator, new PathTuple(schema, node, new EmptyResolver()));
            assertEquals(0, context.errors.size());
        }

        {
            JsonNode node = Util.parse("{ \"x\":\"object\" }");
            VContext context = Util.runValidator(validator, new PathTuple(schema, node, new EmptyResolver()));
            assertEquals(1, context.errors.size());
        }

        {
            JsonNode node = Util.parse("[1, 2]");
            VContext context = Util.runValidator(validator, new PathTuple(schema, node, new EmptyResolver()));
            assertEquals(3, context.errors.size());
        }
    }

    @Test
    public void testValidTypes2() throws IOException, TypeException {
        Session session = Session.loadDefaultSession();
        SchemaLike schema = session.schemas.get("http://exathunk.net/schemas/geo");
        assertNotNull("schema");

        Validator validator = new TypeValidator();

        {
            JsonNode node = Util.parse("{ \"latitude\": 3.14, \"longitude\": 5 }");
            VContext context = Util.runValidator(validator, new PathTuple(schema, node, new EmptyResolver()));
            assertEquals(0, context.errors.size());
        }

        {
            JsonNode node = Util.parse("{ \"latitude\": 3.14, \"longitude\": \"derp\" }");
            VContext context = Util.runValidator(validator, new PathTuple(schema, node, new EmptyResolver()));
            assertEquals(1, context.errors.size());
        }

        {
            JsonNode node = Util.parse("{ \"x\": [1,2,3] }");
            VContext context = Util.runValidator(validator, new PathTuple(schema, node, new EmptyResolver()));
            assertEquals(1, context.errors.size());
        }
    }

    @Test
    public void testRequired() throws IOException, TypeException {
        Session session = Session.loadDefaultSession();
        SchemaLike schema = session.schemas.get("http://exathunk.net/schemas/schema");
        assertNotNull(schema);

        Validator validator = new RequiredValidator();

        {
            JsonNode node = Loader.loadSchemaNode("geo");
            VContext context = Util.runValidator(validator, new PathTuple(schema, node, new EmptyResolver()));
            assertEquals(0, context.errors.size());
        }

        {
            JsonNode node = Util.parse("{ \"type\": \"string\", \"id\":\"foo\" }");
            VContext context = Util.runValidator(validator, new PathTuple(schema, node, new EmptyResolver()));
            assertEquals(0, context.errors.size());
        }

        {
            JsonNode node = Util.parse("{ \"id\":\"foo\" }");
            VContext context = Util.runValidator(validator, new PathTuple(schema, node, new EmptyResolver()));
            assertEquals(1, context.errors.size());
        }
    }

    @Test
    public void testForbidden() throws IOException, TypeException {
        Session session = Session.loadDefaultSession();
        SchemaLike schema = session.schemas.get("http://exathunk.net/schemas/schema");
        assertNotNull(schema);

        Validator validator = new ForbidsValidator();

        {
            JsonNode node = Util.parse("{ \"type\": \"object\", \"id\":\"foo\" }");
            VContext context = Util.runValidator(validator, new PathTuple(schema, node, new EmptyResolver()));
            assertEquals(0, context.errors.size());
        }

        {
            JsonNode node = Util.parse("{ \"type\": \"object\", \"$ref\":\"bar\" }");
            VContext context = Util.runValidator(validator, new PathTuple(schema, node, new EmptyResolver()));
            assertEquals(0, context.errors.size());
        }

        {
            JsonNode node = Util.parse("{ \"type\": \"object\", \"id\":\"foo\", \"$ref\":\"bar\" }");
            VContext context = Util.runValidator(validator, new PathTuple(schema, node, new EmptyResolver()));
            assertEquals(2, context.errors.size());
        }
    }

    @Test
    public void testRequires() throws IOException, TypeException {
        JsonNode schemaNode = Util.parse("{\"type\":\"object\", \"properties\": { \"a\": {\"type\":\"integer\", \"requires\":[\"b\"]}, \"b\": {\"type\":\"integer\", \"requires\":[\"b\"]} }}");
        Schema schema = Util.quickBind(schemaNode, new SchemaFactory());
        assertNotNull(schema);

        Validator validator = new RequiresValidator();

        {
            JsonNode node = Util.parse("{ \"a\":\"1\" }");
            VContext context = Util.runValidator(validator, new PathTuple(schema, node, new EmptyResolver()));
            assertEquals(1, context.errors.size());
        }

        {
            JsonNode node = Util.parse("{ \"b\":\"2\" }");
            VContext context = Util.runValidator(validator, new PathTuple(schema, node, new EmptyResolver()));
            assertEquals(0, context.errors.size());
        }

        {
            JsonNode node = Util.parse("{ \"a\":\"1\", \"b\":\"2\" }");
            VContext context = Util.runValidator(validator, new PathTuple(schema, node, new EmptyResolver()));
            assertEquals(0, context.errors.size());
        }
    }
}
