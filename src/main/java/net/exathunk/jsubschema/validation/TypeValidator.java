package net.exathunk.jsubschema.validation;

import net.exathunk.jsubschema.base.PathTuple;
import net.exathunk.jsubschema.base.Util;

/**
 * charolastra 11/15/12 11:47 AM
 */
public class TypeValidator implements Validator {
    @Override
    public void validate(PathTuple tuple, VContext context) {
        final String schemaType = tuple.eitherSchema.getFirst().getType();
        if (schemaType == null) {
            context.errors.add(new VError(tuple.reference, "no schema type"));
        }
        if (!Util.matchesType(tuple.node, schemaType)) {
            context.errors.add(new VError(tuple.reference, "node/schema mismatch: "+tuple.node+" "+schemaType));
        }
    }
}
