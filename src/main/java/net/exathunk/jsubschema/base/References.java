package net.exathunk.jsubschema.base;

import org.codehaus.jackson.JsonNode;

/**
 * charolastra 11/16/12 7:21 PM
 */
public class References {
    public static Either<JsonNode, String> evalRef(String ref, JsonNode node) {
        final Path path = Path.fromPointer(ref).reversed();
        return evalRef(path, node);
    }

    public static Either<JsonNode, String> evalRef(Path path, JsonNode node) {
        if (path.isEmpty()) return Either.makeFirst(node);
        final Part part = path.getHead();
        final JsonNode child;
        if (part.hasKey()) {
            child = node.get(part.getKey());
        } else {
            child = node.get(part.getIndex());
        }
        if (child == null) return Either.makeSecond("Bad ref: "+path);
        else return evalRef(path.getTail(), child);
    }
}
