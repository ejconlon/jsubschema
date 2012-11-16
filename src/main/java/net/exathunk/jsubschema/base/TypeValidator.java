package net.exathunk.jsubschema.base;

/**
 * charolastra 11/15/12 11:47 AM
 */
public class TypeValidator implements Validator {
    @Override
    public void validate(PathTuple tuple, VContext context) {
        final String schemaType = tuple.eitherSchema.getFirst().type;
        if (schemaType == null) {
            context.errors.add(new VError(tuple.path, "no schema type"));
        }
        if (!Util.matchesType(tuple.node, schemaType)) {
            context.errors.add(new VError(tuple.path, "node/schema mismatch: "+tuple.node+" "+schemaType));
        }
    }
}
