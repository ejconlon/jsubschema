package net.exathunk.jsubschema.base;

import net.exathunk.jsubschema.genschema.SchemaLike;

/**
 * charolastra 11/16/12 1:52 PM
 */
public class SessionResolver implements RefResolver {
    private final Session session;

    public SessionResolver(Session session) {
        this.session = session;
    }

    @Override
    public Either<SchemaLike, String> resolveRef(String ref) {
        final SchemaLike maybeSchema = session.schemas.get(ref);
        if (maybeSchema != null) {
            return Either.makeFirst(maybeSchema);
        } else {
            return Either.makeSecond("Invalid ref: "+ref);
        }
    }
}
