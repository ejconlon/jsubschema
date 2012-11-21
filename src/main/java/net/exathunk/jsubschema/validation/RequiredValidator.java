package net.exathunk.jsubschema.validation;

import net.exathunk.jsubschema.base.SchemaTuple;
import net.exathunk.jsubschema.genschema.schema.SchemaLike;
import net.exathunk.jsubschema.genschema.schema.declarations.keylist.KeyListLike;

import java.util.List;
import java.util.Set;
import java.util.TreeSet;

/**
 * charolastra 11/16/12 12:51 PM
 */
public class RequiredValidator implements Validator {
    @Override
    public void validate(SchemaTuple tuple, VContext context) {
        if (tuple.getRefTuple().getNode().isObject()) {
            final SchemaLike schema = tuple.getEitherSchema().getFirst().getSchema();
            final List<String> requiredKeys = schema.getRequired();
            if (requiredKeys != null) {
                if (schema.getProperties() == null) {
                    context.errors.add(new VError(tuple.getRefTuple().getReference(), "No properties"));
                } else {
                    final Set<String> missingKeys = new TreeSet<String>();
                    for (String requiredKey : requiredKeys) {
                        if (!tuple.getRefTuple().getNode().has(requiredKey)) {
                            missingKeys.add(requiredKey);
                        }
                    }
                    if (!missingKeys.isEmpty() && schema.hasForbids()) {
                        for (String missingKey : missingKeys) {
                            final KeyListLike forbids = schema.getForbids().get(missingKey);
                            boolean skip = false;
                            if (forbids != null) {
                                for (String forbidden : forbids) {
                                    if (tuple.getRefTuple().getNode().has(forbidden) && requiredKeys.contains(forbidden)) {
                                        final KeyListLike forbids2 = schema.getForbids().get(forbidden);
                                        if (forbids2 != null) {
                                            if (forbids2.contains(missingKey)) {
                                                skip = true;
                                                break;
                                            }
                                        }
                                    }
                                }
                            }
                            if (!skip)
                                context.errors.add(
                                        new VError(tuple.getRefTuple().getReference(), "Missing required key: "+missingKey));
                        }
                    }
                }
            }
        }
    }
}
