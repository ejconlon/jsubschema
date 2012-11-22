package net.exathunk.jsubschema.base;

import net.exathunk.jsubschema.functional.Either3;
import net.exathunk.jsubschema.pointers.PointedSchemaRef;

/**
 * charolastra 11/16/12 1:51 PM
 */
public interface RefResolver {
    Either3<SchemaRef, String, PointedSchemaRef> resolveRef(PointedSchemaRef pointedSchemaRef);
}
