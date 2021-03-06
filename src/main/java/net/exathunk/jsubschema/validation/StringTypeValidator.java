package net.exathunk.jsubschema.validation;

import net.exathunk.jsubschema.Util;
import net.exathunk.jsubschema.base.SchemaNode;
import net.exathunk.jsubschema.pointers.Part;
import org.codehaus.jackson.JsonNode;

import java.util.Set;

/**
 * charolastra 11/27/12 6:50 PM
 */
public class StringTypeValidator implements Validator {
    private boolean shouldIgnore(SchemaNode node) {
        Set<Part> ignoreParts = Util.asSet(Part.asKey("properties"), Part.asKey("type"));
        return !node.getPointedNode().getPointer().isEmpty() && ignoreParts.contains(node.getPointedNode().getPointer().getHead());
    }

    @Override
    public void validate(SchemaNode node, VContext context) {
        if (shouldIgnore(node)) return;
        final JsonNode n = node.getPointedNode().getNode();
        if (n.has("stringEnum") || n.has("$instance")) {
            if (!n.has("type") || !"string".equals(n.get("type").asText())) {
                context.errors.add(new VError(node, "Must have string type, found: "+n));
            }
        }
    }
}
