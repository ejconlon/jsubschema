package net.exathunk.jsubschema.base;

import net.exathunk.jsubschema.genschema.Schema;

import java.util.Map;

/**
 * charolastra 11/16/12 12:51 PM
 */
public class RequiredValidator implements Validator {
    @Override
    public void validate(PathTuple tuple, VContext context) {
        if (tuple.node.isObject()) {
            final Schema schema = tuple.eitherSchema.getFirst();
            if (schema.getProperties() != null) {
                for (Map.Entry<String, Schema> entry : schema.getProperties().entrySet()) {
                    if (Boolean.TRUE.equals(entry.getValue().getRequired())) {
                        if (!tuple.node.has(entry.getKey())) {
                            context.errors.add(new VError(tuple.path, "Missing required key: "+entry.getKey()));
                        }
                    }
                }
            }
        }
    }
}
