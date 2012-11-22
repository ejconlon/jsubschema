package net.exathunk.jsubschema.validation;

import net.exathunk.jsubschema.base.SchemaNode;
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
    public void validate(SchemaNode node, VContext context) {
        if (node.getPointedNode().getNode().isObject()) {
            final SchemaLike schema = node.getEitherSchema().getFirst().getSchema();
            final List<String> requiredKeys = schema.getRequired();
            if (requiredKeys != null) {
                if (schema.getProperties() == null) {
                    context.errors.add(new VError(node, "No properties"));
                } else {
                    final Set<String> missingKeys = new TreeSet<String>();
                    for (String requiredKey : requiredKeys) {
                        if (!node.getPointedNode().getNode().has(requiredKey)) {
                            missingKeys.add(requiredKey);
                        }
                    }
                    if (!missingKeys.isEmpty() && schema.hasForbids()) {
                        for (String missingKey : missingKeys) {
                            final KeyListLike forbids = schema.getForbids().get(missingKey);
                            boolean skip = false;
                            if (forbids != null) {
                                for (String forbidden : forbids) {
                                    if (node.getPointedNode().getNode().has(forbidden) && requiredKeys.contains(forbidden)) {
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
                                context.errors.add(new VError(node, "Missing required key: "+missingKey));
                        }
                    }
                }
            }
        }
    }
}
