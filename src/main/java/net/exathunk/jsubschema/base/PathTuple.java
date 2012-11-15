package net.exathunk.jsubschema.base;

import net.exathunk.jsubschema.genschema.Schema;
import org.codehaus.jackson.JsonNode;

/**
 * charolastra 11/15/12 12:48 PM
 */
public class PathTuple {
    public final Schema schema;
    public final JsonNode node;
    public final Path path;

    public PathTuple(Schema schema, JsonNode node, Path path) {
        this.schema = schema;
        this.node = node;
        this.path = path;
    }
}
