package net.exathunk.jsubschema.base;

import net.exathunk.jsubschema.genschema.SchemaLike;

/**
 * charolastra 11/16/12 2:04 PM
 */
public class EmptyResolver implements RefResolver {
    @Override
    public Either<SchemaLike, String> resolveRef(String ref) {
        return Either.makeSecond("Empty resolver cannot resolve: "+ref);
    }
}
