package net.exathunk.jsubschema.validation;

import net.exathunk.jsubschema.base.SchemaTuple;
import net.exathunk.jsubschema.genschema.SchemaLike;

import java.util.List;
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
                            // The key is missing, but may be omitted if a mutually-forbidden key is present!
                            boolean skippable = false;
                            final List<String> forbids = entry.getValue().getForbids();
                            if (forbids != null) {
                                for (String f : forbids) {
                                    if (tuple.getRefTuple().getNode().has(f)) {
                                        final List<String> nextForbids = schema.getProperties().get(f).getForbids();
                                        if (nextForbids != null) {
                                            for (String f2 : nextForbids) {
                                                if (f2.equals(entry.getKey())) {
                                                    skippable = true;
                                                    break;
                                                }
                                            }
                                        }
                                    }
                                }
                            }

                            if (!skippable)
                                context.errors.add(
                                        new VError(tuple.getRefTuple().getReference(), "Missing required key: "+entry.getKey()));
                        }
                    }
                }
            }
        }
    }
}
