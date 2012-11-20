package net.exathunk.jsubschema.validation;

import net.exathunk.jsubschema.Util;
import net.exathunk.jsubschema.base.SchemaTuple;
import net.exathunk.jsubschema.genschema.schema.SchemaLike;
import net.exathunk.jsubschema.genschema.schema.declarations.stringarray.StringArrayLike;
import net.exathunk.jsubschema.pointers.Part;
import net.exathunk.jsubschema.pointers.Reference;

import java.util.Map;
import java.util.Set;

/**
 * charolastra 11/19/12 9:50 PM
 */
public class PropAttrsValidator implements Validator {
    @Override
    public void validate(SchemaTuple tuple, VContext context) {
        if (tuple.getRefTuple().getNode().isObject()) {
            final Reference ref = tuple.getRefTuple().getReference();
            final SchemaLike schema = tuple.getEitherSchema().getFirst().getSchema();
            if (schema.hasProperties()) {
                final Set<String> props = schema.getProperties().keySet();
                if (schema.hasRequired()) {
                    final Set<String> x = Util.asSet(schema.getRequired());
                    if (x.size() != schema.getRequired().size()) {
                        context.errors.add(new VError(ref.cons(Part.asKey("required")), "Duplicate key"));
                    }
                    subCheck(props, x, ref.cons(Part.asKey("required")), context);
                }
                if (schema.hasDependencies()) {
                    subCheck(props, schema.getDependencies().keySet(), ref.cons(Part.asKey("dependencies")), context);
                    for (Map.Entry<String, StringArrayLike> entry : schema.getDependencies().entrySet()) {
                        subCheck(props, Util.asSet(entry.getValue()), ref.cons(Part.asKey("dependencies")), context);
                    }
                }
                if (schema.hasForbids()) {
                    subCheck(props, schema.getForbids().keySet(), ref.cons(Part.asKey("forbids")), context);
                    for (Map.Entry<String, StringArrayLike> entry : schema.getForbids().entrySet()) {
                        subCheck(props, Util.asSet(entry.getValue()), ref.cons(Part.asKey("forbids")), context);
                    }
                }
            } else if (schema.hasRequired() || schema.hasDependencies() || schema.hasForbids()) {
                context.errors.add(new VError(ref, "Need properties to specify required/dependencies/forbids"));
            }
        }
    }

    private static void subCheck(Set<String> props, Set<String> xs, Reference ref, VContext context) {
        for (String x : xs) {
            if (!props.contains(x)) {
                context.errors.add(new VError(ref, "Unknown prop: "+x));
            }
        }
    }
}
