package net.exathunk.jsubschema.validation;

import net.exathunk.jsubschema.base.SchemaNode;
import net.exathunk.jsubschema.genschema.schema.SchemaLike;
import org.codehaus.jackson.JsonNode;

/**
 * charolastra 11/27/12 7:11 PM
 */
public class StringEnumValueValidator implements Validator {
    @Override
    public void validate(SchemaNode node, VContext context) {
        final SchemaLike schema = node.getEitherSchema().getFirst().getSchema();
        if (schema.hasStringEnum()) {
            final JsonNode n = node.getPointedNode().getNode();
            if (!n.isTextual() || !schema.getStringEnum().contains(n.asText())) {
                context.errors.add(new VError(node, "Invalid stringEnum value: "+n));
            }
        }
    }
}
