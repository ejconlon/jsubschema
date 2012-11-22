package net.exathunk.jsubschema.base;

import net.exathunk.jsubschema.functional.Either;
import net.exathunk.jsubschema.functional.Either3;
import net.exathunk.jsubschema.genschema.schema.SchemaLike;
import net.exathunk.jsubschema.pointers.*;
import org.codehaus.jackson.JsonNode;

/**
 * charolastra 11/15/12 12:44 PM
 */
public class Pather {
    public static Either3<SchemaRef, String, PointedRef> pathSchema(SchemaRef schemaRef, Pointer pointer) {
        assert (Direction.UP.equals(pointer.getDirection()));
        return unDollarRef(schemaRef, schemaRef, pointer);
    }

    private static Either3<SchemaRef, String, PointedRef> unDollarRef(SchemaRef schemaRef, SchemaRef rootRef, Pointer pointer) {
        assert Direction.UP.equals(pointer.getDirection());
        if (schemaRef.getSchema().has__dollar__ref()) {
            Either<Reference, String> eitherReference = Reference.fromReferenceString(schemaRef.getSchema().get__dollar__ref());
            if (eitherReference.isSecond()) return Either3.makeSecond(eitherReference.getSecond());
            Reference reference = eitherReference.getFirst();
            if (reference.getUrl().isEmpty()) {
                // If this is a relative ref and we have an abs path we're following, set the abs path in the rel ref
                reference = new Reference(schemaRef.getReference().getUrl(), reference.getPointer());
            }
            // Add the rest of the parts we havent reached, then return the ref
            if (reference.getUrl().equals(schemaRef.getReference().getUrl())) {
                if (schemaRef == rootRef) return Either3.makeSecond("Circular ref from: "+ schemaRef.getReference().toReferenceString()+";"+pointer.toPointerString());
                Pointer localRef = reference.getPointer().reversed();
                if (localRef.size() == 0) {
                    return unDollarRef(rootRef, rootRef, pointer);
                } if (localRef.size() != 2 || !localRef.getHead().equals(Part.asKey("declarations")) || !localRef.getTail().getHead().hasKey() ||
                    !rootRef.getSchema().hasDeclarations() || !rootRef.getSchema().getDeclarations().containsKey(localRef.getTail().getHead().getKey())) {
                    return Either3.makeSecond("Bad local reference: "+reference.toReferenceString());
                } else {
                    final String nextKey = localRef.getTail().getHead().getKey();
                    final SchemaLike nextSchema = rootRef.getSchema().getDeclarations().get(nextKey);
                    final Reference nextRef = rootRef.getReference().cons(Part.asKey("declarations")).cons(Part.asKey(nextKey));
                    return unDollarRef(new SchemaRef(nextSchema, nextRef), rootRef, pointer);
                }
            } else {
                // non-local ref - return so resolvers can pick up
                return Either3.makeThird(new PointedRef(reference, pointer));
            }
        } else if (pointer.isEmpty()) {
            return Either3.makeFirst(schemaRef);
        } else {
            final SchemaLike schema = schemaRef.getSchema();
            final Part part = pointer.getHead();
            if (part.hasKey()) {
                if (schema.hasItems()) {
                    final SchemaLike nextSchema = schema.getItems();
                    final Reference nextRef = schemaRef.getReference().cons(Part.asKey("items"));
                    return unDollarRef(new SchemaRef(nextSchema, nextRef), rootRef, pointer.getTail());
                } else if (schema.hasProperties() && schema.getProperties().containsKey(part.getKey())) {
                    final SchemaLike nextSchema = schema.getProperties().get(part.getKey());
                    final Reference nextRef = schemaRef.getReference().cons(Part.asKey("properties")).cons(part);
                    return unDollarRef(new SchemaRef(nextSchema, nextRef), rootRef, pointer.getTail());
                } else {
                    return Either3.makeSecond("pathSchema expected object: "+schemaRef.getReference().toReferenceString()+";"+pointer.toPointerString());
                }
            } else {
                if (!schema.getType().equals("array") || schema.getItems() == null) {
                    return Either3.makeSecond("pathSchema expected array: " + schemaRef.getReference().toReferenceString() + ";" + pointer.toPointerString());
                } else {
                    final SchemaLike nextSchema = schema.getItems();
                    final Reference nextRef = schemaRef.getReference().cons(Part.asKey("items"));
                    return unDollarRef(new SchemaRef(nextSchema, nextRef), rootRef, pointer.getTail());
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
                    throw new PathException("pathNode expected object: "+ pointer +" "+node);
                } else {
                    return pathNodeInner(node.get(part.getKey()), pointer.getTail());
                }
            } else {
                if (!node.isArray() || !node.has(part.getIndex())) {
                    throw new PathException("pathNode expected array: "+ pointer +" "+node);
                } else {
                    return pathNodeInner(node.get(part.getIndex()), pointer.getTail());
                }
            }
        }
    }
}
