package net.exathunk.jsubschema.validation;

import net.exathunk.jsubschema.base.SchemaTuple;
import net.exathunk.jsubschema.genschema.schema.SchemaLike;

/**
 * charolastra 11/21/12 4:57 PM
 */
public class ExtensionsValidator implements Validator {
    @Override
    public void validate(SchemaTuple tuple, VContext context) {
        final SchemaLike schema = tuple.getEitherSchema().getFirst().getSchema();
        if (schema.hasExtensions() && !schema.getExtensions().isEmpty()) {
            context.errors.add(new VError(tuple.getRefTuple().getReference(), "TODO Extensions not yet supported."));
        }
    }
}
