package net.exathunk.jsubschema.base;

import net.exathunk.jsubschema.functional.Either;
import net.exathunk.jsubschema.functional.Either3;
import net.exathunk.jsubschema.genschema.schema.SchemaLike;
import net.exathunk.jsubschema.pointers.*;
import org.codehaus.jackson.JsonNode;

import java.util.Map;

/**
 * charolastra 11/15/12 12:44 PM
 */
public class Pather {

    public static Either3<SchemaRef, String, PointedRef> pathSchema(SchemaRef schemaRef, Pointer pointer) {
        return pathSchemaInner(schemaRef, schemaRef, pointer);
    }

    private static Either3<SchemaRef, String, PointedRef> pathSchemaInner(SchemaRef schemaRef, SchemaRef rootRef, Pointer pointer) {
        assert Direction.UP.equals(pointer.getDirection());
        if (schemaRef.getSchema().has__dollar__ref()) {
            Either<Reference, String> eitherReference = Reference.fromReferenceString(schemaRef.getSchema().get__dollar__ref());
            if (eitherReference.isSecond()) return Either3.makeSecond(eitherReference.getSecond());
            Reference reference = eitherReference.getFirst();
            if (reference.getUrl().isEmpty()) {
                // If this is a relative ref and we have an abs path we're following, set the abs path in the rel ref
                reference = new Reference(schemaRef.getReference().getUrl(), reference.getPointer());
            }
            if (reference.getUrl().equals(schemaRef.getReference().getUrl())) {
                return pathSchemaInner(rootRef, rootRef, pointer);
            } else {
                return Either3.makeThird(new PointedRef(reference, pointer));
            }
        } else if (pointer.isEmpty()) {
            return Either3.makeFirst(schemaRef);
        } else {
            final Part part = pointer.getHead();
            final SchemaLike schema = schemaRef.getSchema();
            if (part.hasKey()) {
                final String key = part.getKey();
                if (key.equals("declarations") && !pointer.getTail().isEmpty()) {
                    if (schema.hasDeclarations()) {
                        return pathSchemaInnerSub(schemaRef, rootRef, pointer.getTail(), "declarations", schema.getDeclarations());
                    }
                } else if (key.equals("items")) {
                    if (schema.hasItems()) {
                        return pathSchemaInner(new SchemaRef(schema.getItems(), schemaRef.getReference().cons(Part.asKey("items"))), rootRef, pointer.getTail());
                    }
                } else if (key.equals("properties") && !pointer.getTail().isEmpty()) {
                    if (schema.hasProperties()) {
                        return pathSchemaInnerSub(schemaRef, rootRef, pointer.getTail(), "properties", schema.getProperties());
                    }
                }
            }
            return Either3.makeSecond("Bad path: "+schemaRef.getReference().toReferenceString()+";"+pointer.reversed().toPointerString());
        }
    }

    public static Either3<SchemaRef, String, PointedRef> pathSchemaInnerSub(SchemaRef schemaRef, SchemaRef rootRef, Pointer pointer, String contextName, Map<String, SchemaLike> schemaMap) {
        assert !pointer.isEmpty();
        final Part part = pointer.getHead();
        if (!part.hasKey() || !schemaMap.containsKey(part.getKey())) return Either3.makeSecond("Bad path (need key): "+schemaRef.getReference().toReferenceString()+";"+pointer.reversed().toPointerString());
        return pathSchemaInner(new SchemaRef(schemaMap.get(part.getKey()), schemaRef.getReference().cons(Part.asKey(contextName)).cons(part)), rootRef, pointer.getTail());
    }

    public static Either3<SchemaRef, String, PointedRef> pathDomain(SchemaRef schemaRef, Pointer pointer) {
        return pathDomainInner(schemaRef, schemaRef, pointer);
    }

    private static Either3<SchemaRef, String, PointedRef> pathDomainInner(SchemaRef schemaRef, SchemaRef rootRef, Pointer pointer) {
        assert Direction.UP.equals(pointer.getDirection());
        if (schemaRef.getSchema().has__dollar__ref()) {
            Either<Reference, String> eitherReference = Reference.fromReferenceString(schemaRef.getSchema().get__dollar__ref());
            if (eitherReference.isSecond()) return Either3.makeSecond(eitherReference.getSecond());
            Reference reference = eitherReference.getFirst();
            if (reference.getUrl().isEmpty()) {
                // If this is a relative ref and we have an abs path we're following, set the abs path in the rel ref
                reference = new Reference(schemaRef.getReference().getUrl(), reference.getPointer());
            }
            if (reference.getUrl().equals(schemaRef.getReference().getUrl())) {
                Either3<SchemaRef, String, PointedRef> x = pathSchemaInner(schemaRef, rootRef, reference.getPointer().reversed());
                if (x.isFirst()) return pathDomainInner(x.getFirst(), rootRef, pointer);
                else return x;

            } else {
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
                    return pathDomainInner(new SchemaRef(nextSchema, nextRef), rootRef, pointer.getTail());
                } else if (schema.hasProperties() && schema.getProperties().containsKey(part.getKey())) {
                    final SchemaLike nextSchema = schema.getProperties().get(part.getKey());
                    final Reference nextRef = schemaRef.getReference().cons(Part.asKey("properties")).cons(part);
                    return pathDomainInner(new SchemaRef(nextSchema, nextRef), rootRef, pointer.getTail());
                } else {
                    return Either3.makeSecond("pathDomain expected object: "+schemaRef.getReference().toReferenceString()+";"+pointer.toPointerString());
                }
            } else {
                if (!schema.getType().equals("array") || schema.getItems() == null) {
                    return Either3.makeSecond("pathDomain expected array: "+schemaRef.getReference().toReferenceString()+";"+pointer.toPointerString());
                } else {
                    final SchemaLike nextSchema = schema.getItems();
                    final Reference nextRef = schemaRef.getReference().cons(Part.asKey("items"));
                    return pathDomainInner(new SchemaRef(nextSchema, nextRef), rootRef, pointer.getTail());
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
