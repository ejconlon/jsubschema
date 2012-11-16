package net.exathunk.jsubschema.base;

import java.util.Arrays;

/**
 * charolastra 11/16/12 1:24 PM
 */
public class DefaultValidator extends MetaValidator {
    public DefaultValidator() {
        super(Arrays.asList(
                new TypeValidator(),
                new RequiredValidator(),
                new RequiresValidator(),
                new ForbidsValidator()
        ));
    }
}
