package net.exathunk.jsubschema.base;

import net.exathunk.jsubschema.Util;
import net.exathunk.jsubschema.genschema.schema.Schema;
import net.exathunk.jsubschema.genschema.schema.SchemaLike;
import net.exathunk.jsubschema.pointers.Reference;
import net.exathunk.jsubschema.validation.VContext;
import net.exathunk.jsubschema.validation.VError;
import org.codehaus.jackson.JsonNode;

import java.util.TreeMap;

/**
 * charolastra 11/16/12 6:34 PM
 */
public class Schematizer {
    public SchemaLike schematize(String id, JsonNode node, VContext context) {
        SchemaLike schema = schematizeInner(id + "#", node, context);
        schema.setId(id);
        return schema;
    }

    public SchemaLike normalize(SchemaLike schema, VContext context) {
        return schema;
    }

    private SchemaLike schematizeInner(String id, JsonNode node, VContext context) {
        SchemaLike schema = new Schema();
        if (node.isObject()) {
            schema.setType("object");
            for (final String key : Util.onceIterable(node.getFieldNames())) {
                final JsonNode child = node.get(key);
                final SchemaLike childSchema = schematizeInner(id + "/" + key, child, context);
                if (!schema.hasProperties()) schema.setProperties(new TreeMap<String, SchemaLike>());
                schema.getProperties().put(key, childSchema);
            }
        } else if (node.isArray()) {
            schema.setType("array");
            if (node.size() > 0) {
                final JsonNode child = node.get(0);
                final SchemaLike childSchema = schematizeInner(id + "/" + 0, child, context);
                schema.setItems(childSchema);
            }
        } else if (node.isTextual()) {
            schema.setType("string");
        } else if (node.isBoolean()) {
            schema.setType("boolean");
        } else if (node.isIntegralNumber()) {
            schema.setType("integer");
        } else if (node.isNumber()) {
            schema.setType("number");
        } else {
            context.errors.add(new VError(Reference.fromReferenceString(id), "Cannot type: "+node));
        }
        return schema;
    }
}
