package net.exathunk.jsubschema.validation;

import net.exathunk.jsubschema.base.SchemaTuple;
import net.exathunk.jsubschema.genschema.SchemaLike;

import java.util.Map;

/**
 * charolastra 11/16/12 1:06 PM
 */
public class RequiresValidator implements Validator {
    @Override
    public void validate(SchemaTuple tuple, VContext context) {
        if (tuple.getRefTuple().getNode().isObject()) {
            final SchemaLike schema = tuple.getEitherSchema().getFirst();
            if (schema.getProperties() != null) {
                for (Map.Entry<String, SchemaLike> entry : schema.getProperties().entrySet()) {
                    final String childKey = entry.getKey();
                    final SchemaLike childSchema = entry.getValue();
                    if (childSchema.getRequires() != null && tuple.getRefTuple().getNode().has(childKey)) {
                        for (String requiredKey : childSchema.getRequires()) {
                            if (!tuple.getRefTuple().getNode().has(requiredKey)) {
                                context.errors.add(new VError(tuple.getRefTuple().getReference(), "Missing required key pair: "+childKey+" "+requiredKey));
                            }
                        }
                    }
                }
            }
        }
    }
}
