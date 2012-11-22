package net.exathunk.jsubschema.validation;

import net.exathunk.jsubschema.Util;
import net.exathunk.jsubschema.base.SchemaNode;
import net.exathunk.jsubschema.genschema.schema.SchemaLike;
import net.exathunk.jsubschema.genschema.schema.declarations.keylist.KeyListLike;
import net.exathunk.jsubschema.pointers.Reference;

import java.util.Map;
import java.util.Set;

/**
 * charolastra 11/19/12 9:50 PM
 */
public class PropAttrsValidator implements Validator {
    @Override
    public void validate(SchemaNode node, VContext context) {
        if (node.getPointedNode().getNode().isObject()) {
            final Reference ref = node.getEitherSchema().getFirst().getReference();
            final SchemaLike schema = node.getEitherSchema().getFirst().getSchema();
            if (schema.hasProperties()) {
                final Set<String> props = schema.getProperties().keySet();
                if (schema.hasRequired()) {
                    final Set<String> x = Util.asSet(schema.getRequired());
                    if (x.size() != schema.getRequired().size()) {
                        context.errors.add(new VError(node, "required: duplicate key"));
                    }
                    subCheck(node, props, x, "required", context);
                }
                if (schema.hasDependencies()) {
                    subCheck(node, props, schema.getDependencies().keySet(), "dependencies", context);
                    for (Map.Entry<String, KeyListLike> entry : schema.getDependencies().entrySet()) {
                        subCheck(node, props, Util.asSet(entry.getValue()), "dependencies", context);
                    }
                }
                if (schema.hasForbids()) {
                    subCheck(node, props, schema.getForbids().keySet(), "forbids", context);
                    for (Map.Entry<String, KeyListLike> entry : schema.getForbids().entrySet()) {
                        subCheck(node, props, Util.asSet(entry.getValue()), "forbids", context);
                    }
                }
            } else if (schema.hasRequired() || schema.hasDependencies() || schema.hasForbids()) {
                context.errors.add(new VError(node, "Need properties to specify required/dependencies/forbids"));
            }
        }
    }

    private static void subCheck(SchemaNode node, Set<String> props, Set<String> xs, String key, VContext context) {
        for (String x : xs) {
            if (!props.contains(x)) {
                context.errors.add(new VError(node, key+": unknown prop: "+x));
            }
        }
    }
}
