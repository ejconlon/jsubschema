package net.exathunk.jsubschema.validation;

import net.exathunk.jsubschema.base.SchemaNode;
import net.exathunk.jsubschema.genschema.schema.SchemaLike;

/**
 * charolastra 11/21/12 4:57 PM
 */
public class ExtensionsValidator implements Validator {
    @Override
    public void validate(SchemaNode node, VContext context) {
        final SchemaLike schema = node.getEitherSchema().getFirst().getSchema();
        if (schema.hasExtensions() && !schema.getExtensions().isEmpty()) {
            context.errors.add(new VError(node, "TODO Extensions not yet supported."));
        }
    }
}
