package net.exathunk.jsubschema.base;

import org.codehaus.jackson.JsonNode;

/**
 * charolastra 11/16/12 7:21 PM
 */
public class Pointers {
    public static Either<JsonNode, String> point(String pointerString, JsonNode node) {
        final Either<Pointer, String> eitherPath = Pointer.fromPointerString(pointerString);
        if (eitherPath.isSecond()) {
            return Either.makeSecond(eitherPath.getSecond());
        } else {
            return point(eitherPath.getFirst().reversed(), node);
        }
    }

    public static Either<JsonNode, String> point(Pointer pointer, JsonNode node) {
        if (!Pointer.Direction.UP.equals(pointer.getDirection())) {
            return Either.makeSecond("DOWN pointer, probable bug: "+pointer);
        }
        if (pointer.isEmpty()) return Either.makeFirst(node);
        final Part part = pointer.getHead();
        final JsonNode child;
        if (part.hasKey()) {
            child = node.get(part.getKey());
        } else {
            child = node.get(part.getIndex());
        }
        if (child == null) return Either.makeSecond("Part not found: "+ pointer);
        else return point(pointer.getTail(), child);
    }
}
