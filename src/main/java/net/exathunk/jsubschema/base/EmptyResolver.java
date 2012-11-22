package net.exathunk.jsubschema.base;

import net.exathunk.jsubschema.functional.Either;
import net.exathunk.jsubschema.functional.Either3;
import net.exathunk.jsubschema.pointers.PointedRef;

/**
 * charolastra 11/16/12 2:04 PM
 */
public class EmptyResolver implements RefResolver, FullRefResolver {
    @Override
    public Either3<SchemaRef, String, PointedRef> resolveRef(PointedRef pointedRef) {
        return Either3.makeSecond("Empty resolver will never resolve: "+pointedRef.toPointedString());
    }

    @Override
    public Either<SchemaRef, String> fullyResolveRef(PointedRef pointedRef) {
        return Either.makeSecond("Empty resolver will never resolve: "+pointedRef.toPointedString());
    }
}
