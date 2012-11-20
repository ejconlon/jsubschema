package net.exathunk.jsubschema.validation;

import net.exathunk.jsubschema.base.SchemaTuple;
import net.exathunk.jsubschema.genschema.schema.SchemaLike;
import net.exathunk.jsubschema.genschema.schema.declarations.stringarray.StringArrayLike;
import org.codehaus.jackson.JsonNode;

import java.util.Map;

/**
 * charolastra 11/16/12 1:06 PM
 */
public class DependenciesValidator implements Validator {
    @Override
    public void validate(SchemaTuple tuple, VContext context) {
        final JsonNode node = tuple.getRefTuple().getNode();
        if (node.isObject()) {
            final SchemaLike schema = tuple.getEitherSchema().getFirst().getSchema();
            final Map<String, StringArrayLike> dependencies = schema.getDependencies();
            if (dependencies != null) {
                for (Map.Entry<String, StringArrayLike> entry : dependencies.entrySet()) {
                    if (node.has(entry.getKey())) {
                        for (String dep : entry.getValue()) {
                            if (!node.has(dep)) {
                                context.errors.add(new VError(tuple.getRefTuple().getReference(), "Missing dependency: "+entry.getKey()+" "+dep));
                            }
                        }
                    }
                }
            }
        }
    }
}