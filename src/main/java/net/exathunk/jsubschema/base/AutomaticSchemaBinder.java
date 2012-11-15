package net.exathunk.jsubschema.base;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;

/**
 * charolastra 11/14/12 4:10 PM
 */
public class AutomaticSchemaBinder implements Binder<Schema> {
    @Override
    public Schema bind(JsonNode node) throws TypeException {
        ObjectMapper mapper = new ObjectMapper();
        final Schema schema;
        try {
            schema = mapper.readValue(node, Schema.class);
        } catch (IOException e) {
            throw new TypeException(e);
        }
        return schema;
    }

    @Override
    public JsonNode unbind(Schema domain) throws TypeException {
        throw new TypeException("NotImplemented");
    }
}
