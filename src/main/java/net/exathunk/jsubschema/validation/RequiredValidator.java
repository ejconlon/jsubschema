package net.exathunk.jsubschema.validation;

import net.exathunk.jsubschema.base.SchemaTuple;
import net.exathunk.jsubschema.genschema.SchemaLike;

import java.util.Map;

/**
 * charolastra 11/16/12 12:51 PM
 */
public class RequiredValidator implements Validator {
    @Override
    public void validate(SchemaTuple tuple, VContext context) {
        if (tuple.getRefTuple().getNode().isObject()) {
            final SchemaLike schema = tuple.getEitherSchema().getFirst().getSchema();
            if (schema.getProperties() != null) {
                for (Map.Entry<String, SchemaLike> entry : schema.getProperties().entrySet()) {
                    if (Boolean.TRUE.equals(entry.getValue().getRequired())) {
                        if (!tuple.getRefTuple().getNode().has(entry.getKey())) {
                            context.errors.add(new VError(tuple.getRefTuple().getReference(), "Missing required key: "+entry.getKey()));
                        }
                    }
                }
            }
        }
    }
}
