package net.exathunk.jsubschema.base;

import net.exathunk.jsubschema.genschema.SchemaLike;

/**
 * charolastra 11/16/12 1:51 PM
 */
public interface RefResolver {
    Either<SchemaLike, String> resolveRef(Reference reference);
}
