package net.exathunk.jsubschema.validation;

import net.exathunk.jsubschema.base.SchemaTuple;

/**
 * charolastra 11/15/12 11:45 AM
 */
public interface Validator {
    void validate(SchemaTuple tuple, VContext context);
}
