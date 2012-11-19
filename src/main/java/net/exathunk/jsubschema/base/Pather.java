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
        assert (reference.getUrl().isEmpty() || reference.toReferenceString().startsWith(schema.getId()));
        return pathSchemaInner(schema, schema, pointer.reversed(), false, Reference.fromId(schema.getId()));
    }

    private static Either3<SchemaRef, String, Reference> unDollarRef(SchemaLike schema, Pointer pointer, Reference usedReference) {
        assert Direction.UP.equals(pointer.getDirection());
        assert (Direction.DOWN.equals(usedReference.getPointer().getDirection()));
        if (schema.has__dollar__ref()) {
            Either<Reference, String> eitherReference = Reference.fromReferenceString(schema.get__dollar__ref());
            if (eitherReference.isSecond()) return Either3.makeSecond(eitherReference.getSecond());
            Reference reference = eitherReference.getFirst();
            // Add the rest of the parts we havent reached
            reference = reference.consAll(pointer);
            reference = reference.withDefaultId(schema.getId()).withDefaultId(usedReference.getUrl());
            return Either3.makeThird(reference);
        } else {
            assert pointer.isEmpty();
            return Either3.makeFirst(new SchemaRef(schema, usedReference));
        }
    }

    private static Either<Reference, String> rejigger(final SchemaLike schemaLike, final Reference usedReference, final Pointer nextParts) {
        assert nextParts.getDirection().equals(Direction.DOWN);
         if (schemaLike.hasId() || schemaLike.has__dollar__ref()) {
             final Reference sRef;
             if (schemaLike.hasId()) {
                 sRef = Reference.fromId(schemaLike.getId());
             } else {
                 Either<Reference, String> parsed = Reference.fromReferenceString(schemaLike.get__dollar__ref());
                 if (parsed.isSecond()) return Either.makeSecond(parsed.getSecond());
                 else sRef = parsed.getFirst().withDefaultId(schemaLike.getId()).withDefaultId(usedReference.getUrl());
             }
             Pointer p = sRef.getPointer();
             final int len = p.size();
             final int usedLen = usedReference.getPointer().size();
             int i = 0;
             for (Part part : usedReference.getPointer().reversed()) {
                 if (i >= len - usedLen) break;
                 else {
                     p = p.cons(part);
                     ++i;
                 }
             }
             return Either.makeFirst(new Reference(sRef.getUrl(), p));
         } else {
             return Either.makeFirst(usedReference.consAll(nextParts.reversed()));
         }
    }

    private static Either3<SchemaRef, String, Reference> pathSchemaInner(SchemaLike schema, SchemaLike root, Pointer pointer, boolean inProperties, Reference usedReference) {
        assert (Direction.UP.equals(pointer.getDirection()));
        assert (Direction.DOWN.equals(usedReference.getPointer().getDirection()));
        if (pointer.isEmpty()) {
           return unDollarRef(schema, pointer, usedReference);
        } else {
           final Part part = pointer.getHead();
            if (part.hasKey()) {
                if (inProperties) {
                    final SchemaLike nextSchema = root;
                    final Either<Reference, String> eitherReference = rejigger(nextSchema, usedReference, new Pointer().cons(part));
                    if (eitherReference.isSecond()) return Either3.makeSecond(eitherReference.getSecond());
                    return pathSchemaInner(nextSchema, root, pointer.getTail(), false, eitherReference.getFirst());
                } else if (root.getProperties().containsKey(part.getKey())) {
                    final SchemaLike nextSchema = root.getProperties().get(part.getKey());
                    final Either<Reference, String> eitherReference = rejigger(nextSchema, usedReference, new Pointer().cons(Part.asKey("properties")).cons(part));
                    if (eitherReference.isSecond()) return Either3.makeSecond(eitherReference.getSecond());
                    return pathSchemaInner(nextSchema, root, pointer.getTail(), !inProperties && "properties".equals(part.getKey()), eitherReference.getFirst());
                } else if (schema.has__dollar__ref()) {
                    return unDollarRef(schema, pointer, usedReference);
                } else if (schema.hasItems()) {
                    final SchemaLike nextSchema = schema.getItems();
                    final Either<Reference, String> eitherReference = rejigger(nextSchema, usedReference, new Pointer().cons(Part.asKey("items")).cons(part));
                    if (eitherReference.isSecond()) return Either3.makeSecond(eitherReference.getSecond());
                    return pathSchemaInner(nextSchema, root, pointer.getTail(), false, eitherReference.getFirst());
                } else {
                    return Either3.makeSecond("Expected object: "+ pointer +" "+schema);
                }
            } else {
                if (!schema.getType().equals("array") || schema.getItems() == null) {
                    return Either3.makeSecond("Expected array: "+ pointer +" "+schema);
                } else {
                    final SchemaLike nextSchema = schema.getItems();
                    final Either<Reference, String> eitherReference = rejigger(nextSchema, usedReference, new Pointer().cons(Part.asKey("items")));
                    if (eitherReference.isSecond()) return Either3.makeSecond(eitherReference.getSecond());
                    return pathSchemaInner(nextSchema, root, pointer.getTail(), false, eitherReference.getFirst());
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
