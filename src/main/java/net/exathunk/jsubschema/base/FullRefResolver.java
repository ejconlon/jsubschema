package net.exathunk.jsubschema.base;

import net.exathunk.jsubschema.functional.Either;
import net.exathunk.jsubschema.pointers.PointedRef;

/**
 * charolastra 11/17/12 1:44 AM
 */
public interface FullRefResolver {
    Either<SchemaRef, String> fullyResolveRef(PointedRef pointedRef);
}
