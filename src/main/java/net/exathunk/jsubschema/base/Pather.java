package net.exathunk.jsubschema.base;

import net.exathunk.jsubschema.genschema.Schema;
import org.codehaus.jackson.JsonNode;

/**
 * charolastra 11/15/12 12:44 PM
 */
public class Pather {
    public static Schema pathSchema(Schema schema, Path path) throws PathException {
        return pathSchemaInner(schema, schema, path);
    }

    private static Schema pathSchemaInner(Schema schema, Schema root, Path path) throws PathException {
        if (path.isEmpty()) return schema;
        else {
            Part part = path.getHead();
            if (part.hasKey()) {
                if (!root.properties.containsKey(part.getKey())) {
                    throw new PathException("Expected object: "+path+" "+schema);
                } else {
                    return pathSchemaInner(root.properties.get(part.getKey()), root, path.getTail());
                }
            } else {
                if (!schema.type.equals("array") || schema.items == null) {
                    throw new PathException("Expected array: "+path+" "+schema);
                } else {
                    return pathSchemaInner(schema.items, root, path.getTail());
                }
            }
        }
    }

    public static JsonNode pathNode(Schema schema, Path path) throws PathException {
        throw new PathException("NOT IMPLEMENTED");
    }
}
