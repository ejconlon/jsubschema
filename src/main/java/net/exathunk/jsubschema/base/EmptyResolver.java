package net.exathunk.jsubschema.base;

import net.exathunk.jsubschema.functional.Either;
import net.exathunk.jsubschema.functional.Either3;
import net.exathunk.jsubschema.genschema.SchemaLike;
import net.exathunk.jsubschema.pointers.Reference;

/**
 * charolastra 11/16/12 2:04 PM
 */
public class EmptyResolver implements RefResolver, FullRefResolver {
    @Override
    public Either3<SchemaRef, String, Reference> resolveRef(Reference reference) {
        return Either3.makeSecond("Empty resolver will never resolve: "+reference.toReferenceString());
    }

    @Override
    public Either<SchemaRef, String> fullyResolveRef(Either3<SchemaRef, String, Reference> either3) {
        return Either.makeSecond("Empty resolver will never resolve: "+either3);
    }
}
