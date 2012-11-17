package net.exathunk.jsubschema.validation;

import net.exathunk.jsubschema.base.SchemaTuple;
import net.exathunk.jsubschema.Util;

/**
 * charolastra 11/15/12 11:47 AM
 */
public class TypeValidator implements Validator {
    @Override
    public void validate(SchemaTuple tuple, VContext context) {
        final String schemaType = tuple.getEitherSchema().getFirst().getType();
        if (schemaType == null) {
            context.errors.add(new VError(tuple.getRefTuple().getReference(), "no schema type"));
        }
        if (!Util.matchesType(tuple.getRefTuple().getNode(), schemaType)) {
            context.errors.add(new VError(tuple.getRefTuple().getReference(), "node/schema mismatch: "+tuple+" "+schemaType));
        }
    }
}
