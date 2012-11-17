package net.exathunk.jsubschema.validation;

import net.exathunk.jsubschema.base.SchemaTuple;

import java.util.List;

/**
 * charolastra 11/15/12 11:36 AM
 */
public class MetaValidator implements Validator {

    private final List<Validator> validators;

    public MetaValidator(List<Validator> validators) {
        this.validators = validators;
    }

    @Override
    public void validate(SchemaTuple tuple, VContext context) {
        for (Validator v : validators) {
            v.validate(tuple, context);
        }
    }
}
