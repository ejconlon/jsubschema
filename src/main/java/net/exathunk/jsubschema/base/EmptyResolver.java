package net.exathunk.jsubschema.base;

import net.exathunk.jsubschema.genschema.Schema;

/**
 * charolastra 11/16/12 2:04 PM
 */
public class EmptyResolver implements RefResolver {
    @Override
    public Either<Schema, String> resolveRef(String ref) {
        return Either.makeSecond("Empty resolver cannot resolve: "+ref);
    }
}
