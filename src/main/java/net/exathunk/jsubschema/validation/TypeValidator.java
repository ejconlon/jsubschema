package net.exathunk.jsubschema.validation;

import net.exathunk.jsubschema.base.SchemaNode;
import net.exathunk.jsubschema.Util;

/**
 * charolastra 11/15/12 11:47 AM
 */
public class TypeValidator implements Validator {
    @Override
    public void validate(SchemaNode node, VContext context) {
        final String schemaType = node.getEitherSchema().getFirst().getSchema().getType();
        if (schemaType == null) {
            context.errors.add(new VError(node, "no schema type"));
        }
        if (!Util.matchesType(node.getPointedNode().getNode(), schemaType)) {
            context.errors.add(new VError(node, "node/schema mismatch: "+ node +" "+schemaType));
        }
    }
}
