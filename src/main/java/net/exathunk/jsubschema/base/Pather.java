package net.exathunk.jsubschema.base;

import net.exathunk.jsubschema.functional.Either;
import net.exathunk.jsubschema.functional.Either3;
import net.exathunk.jsubschema.genschema.schema.SchemaLike;
import net.exathunk.jsubschema.pointers.Direction;
import net.exathunk.jsubschema.pointers.Part;
import net.exathunk.jsubschema.pointers.Pointer;
import net.exathunk.jsubschema.pointers.Reference;
import org.codehaus.jackson.JsonNode;

/**
 * charolastra 11/15/12 12:44 PM
 */
public class Pather {
    public static Either3<SchemaRef, String, Reference> pathSchema(SchemaLike schema, Reference reference) {
        final Pointer pointer = reference.getPointer();
        assert (Direction.DOWN.equals(pointer.getDirection()));
        assert (reference.getUrl().isEmpty() || reference.getUrl().equals(reference.getUrl()));
        return unDollarRef(schema, schema, pointer.reversed(), Reference.fromId(schema.getId()), new Pointer());
    }

    private static Either3<SchemaRef, String, Reference> unDollarRef(SchemaLike schema, SchemaLike root, Pointer pointer, Reference usedReference, Pointer seen) {
        assert Direction.UP.equals(pointer.getDirection());
        assert Direction.DOWN.equals(seen.getDirection());
        if (schema.has__dollar__ref()) {
            Either<Reference, String> eitherReference = Reference.fromReferenceString(schema.get__dollar__ref());
            if (eitherReference.isSecond()) return Either3.makeSecond(eitherReference.getSecond());
            Reference reference = eitherReference.getFirst();
            // Add the rest of the parts we havent reached, then return the ref
            if (reference.getUrl().isEmpty() || reference.getUrl().equals(usedReference.getUrl())) {
                if (schema == root) return Either3.makeSecond("Circular ref from: "+usedReference.toReferenceString());
                Pointer localRef = reference.getPointer().reversed();
                if (localRef.size() == 0) {
                    return unDollarRef(root, root, pointer, Reference.fromId(root.getId()), new Pointer());
                } if (localRef.size() != 2 || !localRef.getHead().equals(Part.asKey("declarations")) || !localRef.getTail().getHead().hasKey() ||
                    !root.hasDeclarations() || !root.getDeclarations().containsKey(localRef.getTail().getHead().getKey())) {
                    return Either3.makeSecond("Bad local reference: "+reference.toReferenceString());
                } else {
                    final SchemaLike nextSchema = root.getDeclarations().get(localRef.getTail().getHead().getKey());
                    return unDollarRef(nextSchema, root, pointer, usedReference.consAll(localRef), new Pointer());
                }
            } else {
                // non-local ref - return so resolvers can pick up
                reference = reference.consAll(pointer);
                return Either3.makeThird(reference);
            }
        } else if (pointer.isEmpty()) {
            return Either3.makeFirst(new SchemaRef(schema, usedReference.consAll(seen.reversed())));
        } else {
            final Part part = pointer.getHead();
            if (part.hasKey()) {
                if (schema.hasItems()) {
                    final SchemaLike nextSchema = schema.getItems();
                    return unDollarRef(nextSchema, root, pointer.getTail(), usedReference, seen.cons(Part.asKey("items")));
                } else if (schema.hasProperties() && schema.getProperties().containsKey(part.getKey())) {
                    final SchemaLike nextSchema = schema.getProperties().get(part.getKey());
                    return unDollarRef(nextSchema, root, pointer.getTail(), usedReference, seen.cons(Part.asKey("properties")).cons(part));
                } else {
                    return Either3.makeSecond("Expected object: "+ pointer +" "+schema);
                }
            } else {
                if (!schema.getType().equals("array") || schema.getItems() == null) {
                    return Either3.makeSecond("Expected array: "+ pointer +" "+schema);
                } else {
                    final SchemaLike nextSchema = schema.getItems();
                    return unDollarRef(nextSchema, root, pointer.getTail(), usedReference, seen.cons(Part.asKey("items")));
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
