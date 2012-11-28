package net.exathunk.jsubschema.validation;

import java.util.ArrayList;
import java.util.List;

/**
 * charolastra 11/15/12 11:41 AM
 */
public class VContext {
    public List<VError> errors = new ArrayList<VError>();

    public void subsume(String prefix, VContext other) {
        for (VError error : other.errors) {
            errors.add(new VError(prefix+error.path, error.message));
        }
    }
}
