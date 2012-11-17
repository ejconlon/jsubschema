package net.exathunk.jsubschema.base;

import net.exathunk.jsubschema.genschema.SchemaLike;

import java.util.Map;

/**
 * charolastra 11/16/12 1:06 PM
 */
public class RequiresValidator implements Validator {
    @Override
    public void validate(PathTuple tuple, VContext context) {
        if (tuple.node.isObject()) {
            final SchemaLike schema = tuple.eitherSchema.getFirst();
            if (schema.getProperties() != null) {
                for (Map.Entry<String, SchemaLike> entry : schema.getProperties().entrySet()) {
                    final String childKey = entry.getKey();
                    final SchemaLike childSchema = entry.getValue();
                    if (childSchema.getRequires() != null && tuple.node.has(childKey)) {
                        for (String requiredKey : childSchema.getRequires()) {
                            if (!tuple.node.has(requiredKey)) {
                                context.errors.add(new VError(tuple.path, "Missing required key pair: "+childKey+" "+requiredKey));
                            }
                        }
                    }
                }
            }
        }
    }
}
