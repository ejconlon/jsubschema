package net.exathunk.jsubschema.validation;

import net.exathunk.jsubschema.Util;
import net.exathunk.jsubschema.base.FullRefResolver;
import net.exathunk.jsubschema.base.SchemaNode;
import net.exathunk.jsubschema.base.SchemaRef;
import net.exathunk.jsubschema.functional.Either;
import net.exathunk.jsubschema.pointers.Part;
import net.exathunk.jsubschema.pointers.PointedRef;
import net.exathunk.jsubschema.pointers.Reference;
import org.codehaus.jackson.JsonNode;

import java.util.Set;

/**
 * charolastra 11/27/12 3:37 PM
 */
public class RefValidator implements Validator {

    private boolean shouldIgnore(SchemaNode node) {
        Set<Part> ignoreParts = Util.asSet(Part.asKey("forbids"), Part.asKey("properties"));
        return !node.getPointedNode().getPointer().isEmpty() && ignoreParts.contains(node.getPointedNode().getPointer().getHead());
    }

    @Override
    public void validate(SchemaNode node, VContext context) {
        if (shouldIgnore(node)) return;
        for (String key : new String[] {"$ref", "$schema", "$instance"}) {
            if (node.getPointedNode().getNode().has(key)) {
                JsonNode refNode = node.getPointedNode().getNode().get(key);
                if (!refNode.isTextual()) {
                    context.errors.add(new VError(node, "Expected "+key+" text, found: "+refNode));
                    return;
                }
                FullRefResolver fullRefResolver = node.getFullRefResolver();
                Either<Reference, String> eitherRef = Reference.fromReferenceString(refNode.asText());
                if (eitherRef.isSecond()) {
                    context.errors.add(new VError(node, "Invalid "+key+" format: "+eitherRef.getSecond()));
                    return;
                }
                PointedRef pointedRef = new PointedRef(eitherRef.getFirst());
                Either<SchemaRef, String> resolved = fullRefResolver.fullyResolveRef(pointedRef);
                if (resolved.isSecond()) {
                    context.errors.add(new VError(node, "Invalid "+key+": "+resolved.getSecond()));
                    return;
                }
            }
        }
    }
}
