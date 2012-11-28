package net.exathunk.jsubschema.validation;

import net.exathunk.jsubschema.Util;
import net.exathunk.jsubschema.base.SchemaNode;
import net.exathunk.jsubschema.pointers.Part;
import org.codehaus.jackson.JsonNode;

import java.util.Set;

/**
 * charolastra 11/27/12 6:50 PM
 */
public class StringEnumTypeValidator implements Validator {
    private boolean shouldIgnore(SchemaNode node) {
        Set<Part> ignoreParts = Util.asSet(Part.asKey("properties"), Part.asKey("type"));
        return !node.getPointedNode().getPointer().isEmpty() && ignoreParts.contains(node.getPointedNode().getPointer().getHead());
    }

    @Override
    public void validate(SchemaNode node, VContext context) {
        if (shouldIgnore(node)) return;
        final JsonNode n = node.getPointedNode().getNode();
        if (n.has("stringEnum")) {
            if (!n.has("type") || !"array".equals(n.get("type").asText()) || !n.has("items") ||
                !n.get("items").has("type") || !"string".equals(n.get("items").get("type").asText())) {
                context.errors.add(new VError(node, "stringEnum must have string type, found: "+n));
            }
        }
    }
}
