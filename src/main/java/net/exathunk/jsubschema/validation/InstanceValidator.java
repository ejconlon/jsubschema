package net.exathunk.jsubschema.validation;

import java.util.Arrays;

/**
 * charolastra 11/16/12 1:24 PM
 */
public class InstanceValidator extends MetaValidator {
    public InstanceValidator() {
        super(Arrays.asList(
                new TypeValidator(),
                new RequiredValidator(),
                new DependenciesValidator(),
                new ForbidsValidator(),
                new StringEnumValueValidator()
        ));
    }
}
