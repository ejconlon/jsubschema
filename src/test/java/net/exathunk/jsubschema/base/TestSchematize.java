package net.exathunk.jsubschema.base;

import net.exathunk.jsubschema.genschema.SchemaLike;
import org.codehaus.jackson.JsonNode;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

/**
 * charolastra 11/16/12 6:24 PM
 */
public class TestSchematize {
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
    }


}
