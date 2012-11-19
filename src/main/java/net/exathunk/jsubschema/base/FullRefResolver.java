package net.exathunk.jsubschema.base;

import net.exathunk.jsubschema.functional.Either;
import net.exathunk.jsubschema.functional.Either3;
import net.exathunk.jsubschema.pointers.Reference;

/**
 * charolastra 11/17/12 1:44 AM
 */
public interface FullRefResolver {
    Either<SchemaRef, String> fullyResolveRef(Either3<SchemaRef, String, Reference> either3);
}
