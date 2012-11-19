package net.exathunk.jsubschema.base;

import net.exathunk.jsubschema.genschema.schema.SchemaLike;
import net.exathunk.jsubschema.pointers.Reference;

/**
 * charolastra 11/19/12 11:41 AM
 */
public class SchemaRef {
    private final SchemaLike schema;
    private final Reference reference;

    public SchemaRef(SchemaLike schema, Reference reference) {
        this.schema = schema;
        this.reference = reference;
    }

    public Reference getReference() {
        return reference;
    }

    public SchemaLike getSchema() {
        return schema;
    }
}
