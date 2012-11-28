package net.exathunk.jsubschema.validation;

import java.util.Arrays;

/**
 * charolastra 11/27/12 6:53 PM
 */
public class SchemaValidator extends MetaValidator {
    public SchemaValidator() {
        super(Arrays.asList(
                new PropAttrsValidator(),
                new ExtensionsValidator(),
                new RefValidator(),
                new StringTypeValidator(),
                new InstanceValidator()
        ));
    }
}
