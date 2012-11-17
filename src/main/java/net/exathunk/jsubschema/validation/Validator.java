package net.exathunk.jsubschema.validation;

import net.exathunk.jsubschema.base.PathTuple;

/**
 * charolastra 11/15/12 11:45 AM
 */
public interface Validator {
    void validate(PathTuple tuple, VContext context);
}
