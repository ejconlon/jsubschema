package net.exathunk.jsubschema.base;

import net.exathunk.jsubschema.functional.Either3;
import net.exathunk.jsubschema.genschema.SchemaLike;
import net.exathunk.jsubschema.pointers.Reference;

/**
 * charolastra 11/16/12 1:52 PM
 */
public class SessionResolver implements RefResolver {
    private final Session session;

    public SessionResolver(Session session) {
        this.session = session;
    }

    @Override
    public Either3<SchemaRef, String, Reference> resolveRef(Reference reference) {
        SchemaLike maybeSchema = session.schemas.get(reference);
        if (maybeSchema != null) {
            return Either3.makeFirst(new SchemaRef(maybeSchema, reference));
        } else {
            maybeSchema = session.schemas.get(reference.withoutPointer());
            if (maybeSchema != null) {
                return Pather.pathSchema(maybeSchema, reference);
            } else {
                return Either3.makeThird(reference);
            }
        }
    }
}
