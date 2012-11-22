package net.exathunk.jsubschema.base;

import net.exathunk.jsubschema.functional.Either3;
import net.exathunk.jsubschema.genschema.schema.SchemaLike;
import net.exathunk.jsubschema.pointers.PointedSchemaRef;
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
    public Either3<SchemaRef, String, PointedSchemaRef> resolveRef(PointedSchemaRef pointedSchemaRef) {
        SchemaLike maybeSchema = session.schemas.get(pointedSchemaRef.getSchemaRef().getReference().getUrl());
        if (maybeSchema != null) {
            // TODO actually shuffle shit from schema space to domain space
            return Pather.pathSchema(new PointedSchemaRef(new SchemaRef(maybeSchema, Reference.fromId(maybeSchema.getId())), pointedSchemaRef.getPointer()));
        } else {
            return Either3.makeThird(pointedSchemaRef);
        }
    }
}
