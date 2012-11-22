package net.exathunk.jsubschema.validation;

import net.exathunk.jsubschema.base.SchemaNode;

/**
 * charolastra 11/15/12 11:45 AM
 */
public interface Validator {
    void validate(SchemaNode node, VContext context);
}
