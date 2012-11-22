package net.exathunk.jsubschema.validation;

import net.exathunk.jsubschema.base.SchemaNode;

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
    public void validate(SchemaNode node, VContext context) {
        for (Validator v : validators) {
            v.validate(node, context);
        }
    }
}
