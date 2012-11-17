package net.exathunk.jsubschema.base;

import net.exathunk.jsubschema.functional.Either;
import net.exathunk.jsubschema.functional.Either3;
import net.exathunk.jsubschema.genschema.SchemaLike;
import net.exathunk.jsubschema.pointers.Direction;
import net.exathunk.jsubschema.pointers.Part;
import net.exathunk.jsubschema.pointers.Pointer;
import net.exathunk.jsubschema.pointers.Reference;
import org.codehaus.jackson.JsonNode;

/**
 * charolastra 11/15/12 12:44 PM
 */
public class Pather {
    public static Either3<SchemaLike, String, Reference> pathSchema(SchemaLike schema, Reference reference) {
        final String url = reference.getUrl();
        final Pointer pointer = reference.getPointer();
        assert (Direction.DOWN.equals(pointer.getDirection()));
        return pathSchemaInner(schema, schema, url, pointer.reversed(), false);
    }

    private static Either3<SchemaLike, String, Reference> unDollarRef(SchemaLike schema, Pointer pointer) {
        assert Direction.UP.equals(pointer.getDirection());
        if (schema.has__dollar__ref()) {
            Either<Reference, String> eitherReference = Reference.fromReferenceString(schema.get__dollar__ref());
            if (eitherReference.isSecond()) return Either3.makeSecond(eitherReference.getSecond());
            Reference reference = eitherReference.getFirst();
            for (Part part : pointer.reversed()) {
                reference = reference.cons(part);
            }
            return Either3.makeThird(reference);
        } else {
            return Either3.makeFirst(schema);
        }
    }

    private static Either3<SchemaLike, String, Reference> pathSchemaInner(SchemaLike schema, SchemaLike root, String url, Pointer pointer, boolean inProperties) {
        assert (Direction.UP.equals(pointer.getDirection()));
        if (pointer.isEmpty()) {
           return unDollarRef(schema, pointer);
        } else {
           final Part part = pointer.getHead();
            if (part.hasKey()) {
                if (inProperties) {
                    return pathSchemaInner(root, root, url, pointer.getTail(), false);
                } else if (root.getProperties().containsKey(part.getKey())) {
                    return pathSchemaInner(root.getProperties().get(part.getKey()), root, url, pointer.getTail(), !inProperties && "properties".equals(part.getKey()));
                } else if (schema.has__dollar__ref()) {
                    return unDollarRef(schema, pointer);
                } else  {
                    return Either3.makeSecond("Expected object: "+ pointer +" "+schema);
                }
            } else {
                if (!schema.getType().equals("array") || schema.getItems() == null) {
                    return Either3.makeSecond("Expected array: "+ pointer +" "+schema);
                } else {
                    return pathSchemaInner(schema.getItems(), root, url, pointer.getTail(), false);
                }
            }
        }
    }

    public static JsonNode pathNode(JsonNode node, Pointer pointer) throws PathException {
        assert (Direction.DOWN.equals(pointer.getDirection()));
        return pathNodeInner(node, pointer.reversed());
    }

    private static JsonNode pathNodeInner(JsonNode node, Pointer pointer) throws PathException {
        assert (Direction.UP.equals(pointer.getDirection()));
        if (pointer.isEmpty()) return node;
        else {
            Part part = pointer.getHead();
            if (part.hasKey()) {
                if (!node.isObject() || !node.has(part.getKey())) {
                    // TODO move to EITHER
                    throw new PathException("Expected object: "+ pointer +" "+node);
                } else {
                    return pathNodeInner(node.get(part.getKey()), pointer.getTail());
                }
            } else {
                if (!node.isArray() || !node.has(part.getIndex())) {
                    throw new PathException("Expected array: "+ pointer +" "+node);
                } else {
                    return pathNodeInner(node.get(part.getIndex()), pointer.getTail());
                }
            }
        }
    }
}
