package net.exathunk.jsubschema.base;

import net.exathunk.jsubschema.functional.Either3;
import net.exathunk.jsubschema.genschema.schema.SchemaLike;
import net.exathunk.jsubschema.pointers.PointedRef;
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
    public Either3<SchemaRef, String, PointedRef> resolveRef(PointedRef pointedRef) {
        Reference otherReference = pointedRef.getReference();
        SchemaLike maybeSchema = session.schemas.get(otherReference.getUrl());
        if (maybeSchema != null) {
            return Pather.pathSchema(new SchemaRef(maybeSchema, Reference.fromId(maybeSchema.getId())), pointedRef.getPointer());
        } else {
            return Either3.makeThird(pointedRef);
        }
    }
}
