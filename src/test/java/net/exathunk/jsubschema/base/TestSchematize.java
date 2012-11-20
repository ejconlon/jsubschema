package net.exathunk.jsubschema.base;

import net.exathunk.jsubschema.Util;
import net.exathunk.jsubschema.genschema.schema.SchemaFactory;
import net.exathunk.jsubschema.genschema.schema.SchemaLike;
import net.exathunk.jsubschema.validation.DefaultValidator;
import net.exathunk.jsubschema.validation.VContext;
import net.exathunk.jsubschema.validation.VError;
import net.exathunk.jsubschema.validation.Validator;
import org.codehaus.jackson.JsonNode;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * charolastra 11/16/12 6:24 PM
 */
public class TestSchematize {

    private static List<VError> validate(SchemaLike schema, JsonNode node) {
        Validator validator = new DefaultValidator();
        FullRefResolver fullRefResolver = new MetaResolver(new SelfResolver(schema));
        VContext context = Util.runValidator(validator, new SchemaTuple(schema, new RefTuple(node), fullRefResolver));
        return context.errors;
    }

    @Test
    public void testSchematize() throws IOException, TypeException {
        final String instanceStr = "{\"b\":false, \"i\":4, \"n\":3.14, \"s\":\"foo\"}";
        String schemaStr = "{ \"id\":\"http://example.com/foo\", \"type\":\"object\", \"properties\": {";
        schemaStr += "\"b\": {\"type\":\"boolean\"}, ";
        schemaStr += "\"i\": {\"type\":\"integer\"}, ";
        schemaStr += "\"n\": {\"type\":\"number\"}, ";
        schemaStr += "\"s\": {\"type\":\"string\"} ";
        schemaStr += "} }";

        JsonNode instanceNode = Util.parse(instanceStr);
        JsonNode goldSchemaNode = Util.parse(schemaStr);

        Schematizer schematizer = new Schematizer();
        VContext context = new VContext();

        SchemaLike schema = schematizer.schematize("http://example.com/foo", instanceNode, context);

        assertEquals(new ArrayList<VError>(), context.errors);

        JsonNode testSchemaNode = Util.quickUnbind(schema);

        assertEquals(goldSchemaNode, testSchemaNode);

        assertEquals(new ArrayList<VError>(), validate(schema, instanceNode));
    }

    @Test
    public void testNotNormalized() throws IOException, TypeException {
        final String instanceStr = "{\"a\": {\"b\" : {\"c\": 2}}}";
        String schemaStr = "{ \"id\":\"http://example.com/foo\", \"type\":\"object\", ";
        schemaStr += "\"properties\": {\"a\": {\"type\":\"object\", ";
        schemaStr += "\"properties\": {\"b\": {\"type\":\"object\", ";
        schemaStr += "\"properties\": {\"c\": {\"type\":\"integer\"} ";
        schemaStr += "} } } } } }";

        String normalizedStr = "{\"type\":\"object\",\"properties\":{\"a\":{\"$ref\":\"#/declarations/~1properties~1a\"}},\"declarations\":{\"/properties/a\":{\"type\":\"object\",\"properties\":{\"b\":{\"$ref\":\"#/declarations/~1properties~1a~1properties~1b\"}}},\"/properties/a/properties/b\":{\"type\":\"object\",\"properties\":{\"c\":{\"type\":\"integer\"}}}},\"id\":\"http://example.com/foo\"}";

        JsonNode instanceNode = Util.parse(instanceStr);
        JsonNode goldSchemaNode = Util.parse(schemaStr);
        JsonNode goldNormalizedNode = Util.parse(normalizedStr);

        Schematizer schematizer = new Schematizer();
        VContext context = new VContext();

        SchemaLike schema = schematizer.schematize("http://example.com/foo", instanceNode, context);

        assertEquals(new ArrayList<VError>(), context.errors);

        assertEquals(new ArrayList<VError>(), validate(schema, instanceNode));

        JsonNode testSchemaNode = Util.quickUnbind(schema);

        assertEquals(goldSchemaNode, testSchemaNode);

        Normalizer n = new Normalizer(schema, context);
        SchemaLike normalized = n.call();

        assertEquals(new ArrayList<VError>(), context.errors);

        JsonNode testNormalizedNode = Util.quickUnbind(normalized);

        assertEquals(goldNormalizedNode, testNormalizedNode);

        SchemaLike schema2 = Util.quickBind(testNormalizedNode, new SchemaFactory());

        assertEquals(new ArrayList<VError>(), validate(schema2, instanceNode));
    }
}
