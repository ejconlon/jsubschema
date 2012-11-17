package net.exathunk.jsubschema.base;

import net.exathunk.jsubschema.genschema.Schema;
import org.codehaus.jackson.JsonNode;

/**
 * charolastra 11/15/12 12:44 PM
 */
public class Pather {
    public static Either<Schema, String> pathSchema(Schema schema, Path path, RefResolver resolver) throws PathException {
        return pathSchemaInner(schema, schema, path, false, resolver);
    }

    private static Either<Schema, String> pathSchemaInner(Schema schema, Schema root, Path path, boolean inProperties, RefResolver resolver) throws PathException {
        if (path.isEmpty()) {
            if (schema.get__dollar__ref() != null) {
                return resolver.resolveRef(schema.get__dollar__ref());
            } else {
                return Either.makeFirst(schema);
            }
        } else {
            Part part = path.getHead();
            if (part.hasKey()) {
                if (inProperties) {
                    return pathSchemaInner(root, root, path.getTail(), false, resolver);
                } else if (root.getProperties().containsKey(part.getKey())) {
                    return pathSchemaInner(root.getProperties().get(part.getKey()), root, path.getTail(), !inProperties && "properties".equals(part.getKey()), resolver);
                } else if (schema.get__dollar__ref() != null) {
                    Either<Schema, String> eitherSchema = resolver.resolveRef(schema.get__dollar__ref());
                    if (eitherSchema.isFirst()) {
                        return pathSchemaInner(eitherSchema.getFirst(), eitherSchema.getFirst(), path, false, resolver);
                    } else {
                        return eitherSchema;
                    }
                } else  {
                    return Either.makeSecond("Expected object: "+path+" "+schema);
                }
            } else {
                if (!schema.getType().equals("array") || schema.getItems() == null) {
                    return Either.makeSecond("Expected array: "+path+" "+schema);
                } else {
                    return pathSchemaInner(schema.getItems(), root, path.getTail(), false, resolver);
                }
            }
        }
    }

    public static JsonNode pathNode(JsonNode node, Path path) throws PathException {
        if (path.isEmpty()) return node;
        else {
            Part part = path.getHead();
            if (part.hasKey()) {
                if (!node.isObject() || !node.has(part.getKey())) {
                    throw new PathException("Expected object: "+path+" "+node);
                } else {
                    return pathNode(node.get(part.getKey()), path.getTail());
                }
            } else {
                if (!node.isArray() || !node.has(part.getIndex())) {
                    throw new PathException("Expected array: "+path+" "+node);
                } else {
                    return pathNode(node.get(part.getIndex()), path.getTail());
                }
            }
        }
    }
}
