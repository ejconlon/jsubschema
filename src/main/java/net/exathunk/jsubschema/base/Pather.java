package net.exathunk.jsubschema.base;

import net.exathunk.jsubschema.genschema.SchemaLike;
import org.codehaus.jackson.JsonNode;

/**
 * charolastra 11/15/12 12:44 PM
 */
public class Pather {
    public static Either<SchemaLike, String> pathSchema(SchemaLike schema, Pointer pointer, RefResolver resolver) throws PathException {
        return pathSchemaInner(schema, schema, pointer, false, resolver);
    }

    private static Either<SchemaLike, String> pathSchemaInner(SchemaLike schema, SchemaLike root, Pointer pointer, boolean inProperties, RefResolver resolver) throws PathException {
        if (pointer.isEmpty()) {
            if (schema.get__dollar__ref() != null) {
                return Resolvers.resolveRefString(schema.get__dollar__ref(), resolver);
            } else {
                return Either.makeFirst(schema);
            }
        } else {
            Part part = pointer.getHead();
            if (part.hasKey()) {
                if (inProperties) {
                    return pathSchemaInner(root, root, pointer.getTail(), false, resolver);
                } else if (root.getProperties().containsKey(part.getKey())) {
                    return pathSchemaInner(root.getProperties().get(part.getKey()), root, pointer.getTail(), !inProperties && "properties".equals(part.getKey()), resolver);
                } else if (schema.get__dollar__ref() != null) {
                    Either<SchemaLike, String> eitherSchema = Resolvers.resolveRefString(schema.get__dollar__ref(), resolver);
                    if (eitherSchema.isFirst()) {
                        return pathSchemaInner(eitherSchema.getFirst(), eitherSchema.getFirst(), pointer, false, resolver);
                    } else {
                        return eitherSchema;
                    }
                } else  {
                    return Either.makeSecond("Expected object: "+ pointer +" "+schema);
                }
            } else {
                if (!schema.getType().equals("array") || schema.getItems() == null) {
                    return Either.makeSecond("Expected array: "+ pointer +" "+schema);
                } else {
                    return pathSchemaInner(schema.getItems(), root, pointer.getTail(), false, resolver);
                }
            }
        }
    }

    public static JsonNode pathNode(JsonNode node, Pointer pointer) throws PathException {
        if (pointer.isEmpty()) return node;
        else {
            Part part = pointer.getHead();
            if (part.hasKey()) {
                if (!node.isObject() || !node.has(part.getKey())) {
                    throw new PathException("Expected object: "+ pointer +" "+node);
                } else {
                    return pathNode(node.get(part.getKey()), pointer.getTail());
                }
            } else {
                if (!node.isArray() || !node.has(part.getIndex())) {
                    throw new PathException("Expected array: "+ pointer +" "+node);
                } else {
                    return pathNode(node.get(part.getIndex()), pointer.getTail());
                }
            }
        }
    }
}
