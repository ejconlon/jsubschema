package net.exathunk.jsubschema.base;

import net.exathunk.jsubschema.genschema.Schema;

/**
 * charolastra 11/16/12 1:51 PM
 */
public interface RefResolver {
    Either<Schema, String> resolveRef(String ref);
}
