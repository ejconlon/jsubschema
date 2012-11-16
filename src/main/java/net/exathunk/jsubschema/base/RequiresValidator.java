package net.exathunk.jsubschema.base;

import net.exathunk.jsubschema.base.PathTuple;
import net.exathunk.jsubschema.base.VContext;
import net.exathunk.jsubschema.base.VError;
import net.exathunk.jsubschema.base.Validator;
import net.exathunk.jsubschema.genschema.Schema;

import java.util.Map;

/**
 * charolastra 11/16/12 1:06 PM
 */
public class RequiresValidator implements Validator {
    @Override
    public void validate(PathTuple tuple, VContext context) {
        if (tuple.node.isObject()) {
            final Schema schema = tuple.eitherSchema.getFirst();
            if (schema.properties != null) {
                for (Map.Entry<String, Schema> entry : schema.properties.entrySet()) {
                    final String childKey = entry.getKey();
                    final Schema childSchema = entry.getValue();
                    if (childSchema.requires != null && tuple.node.has(childKey)) {
                        for (String requiredKey : childSchema.requires) {
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
