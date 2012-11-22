package net.exathunk.jsubschema.base;

import net.exathunk.jsubschema.functional.Either;
import net.exathunk.jsubschema.functional.Either3;
import net.exathunk.jsubschema.pointers.PointedSchemaRef;

/**
 * charolastra 11/16/12 2:04 PM
 */
public class EmptyResolver implements RefResolver, FullRefResolver {
    @Override
    public Either3<SchemaRef, String, PointedSchemaRef> resolveRef(PointedSchemaRef pointedSchemaRef) {
        return Either3.makeSecond("Empty resolver will never resolve: "+pointedSchemaRef.toPointedString());
    }

    @Override
    public Either<SchemaRef, String> fullyResolveRef(PointedSchemaRef pointedSchemaRef) {
        return Either.makeSecond("Empty resolver will never resolve: "+pointedSchemaRef.toPointedString());
    }
}
