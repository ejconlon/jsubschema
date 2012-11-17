package net.exathunk.jsubschema.validation;

import net.exathunk.jsubschema.base.PathTuple;
import net.exathunk.jsubschema.genschema.SchemaLike;

import java.util.Map;

/**
 * charolastra 11/16/12 1:06 PM
 */
public class ForbidsValidator implements Validator {
    @Override
    public void validate(PathTuple tuple, VContext context) {
        if (tuple.node.isObject()) {
            final SchemaLike schema = tuple.eitherSchema.getFirst();
            if (schema.getProperties() != null) {
                for (Map.Entry<String, SchemaLike> entry : schema.getProperties().entrySet()) {
                    final String childKey = entry.getKey();
                    final SchemaLike childSchema = entry.getValue();
                    if (childSchema.getForbids() != null && tuple.node.has(childKey)) {
                        for (String forbiddenKey : childSchema.getForbids()) {
                            if (tuple.node.has(forbiddenKey)) {
                                context.errors.add(new VError(tuple.reference, "Has forbidden key pair: "+childKey+" "+forbiddenKey));
                            }
                        }
                    }
                }
            }
        }
    }
}
