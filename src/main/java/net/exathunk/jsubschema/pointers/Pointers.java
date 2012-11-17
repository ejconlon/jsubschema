package net.exathunk.jsubschema.pointers;

import net.exathunk.jsubschema.functional.Either;
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
        assert pointer.getDirection().equals(Direction.UP);
        if (pointer.isEmpty()) return Either.makeFirst(node);
        final Part part = pointer.getHead();
        final JsonNode child;
        if (part.hasKey()) {
            child = node.get(part.getKey());
        } else {
            child = node.get(part.getIndex());
        }
        if (child == null) return Either.makeSecond("Part not found: "+ pointer.toPointerString());
        else return point(pointer.getTail(), child);
    }
}
