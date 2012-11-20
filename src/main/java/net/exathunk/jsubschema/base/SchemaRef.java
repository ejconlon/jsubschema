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

    @Override
    public String toString() {
        return "SchemaRef{" +
                "schema=" + schema +
                ", reference=" + reference +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SchemaRef)) return false;

        SchemaRef schemaRef = (SchemaRef) o;

        if (reference != null ? !reference.equals(schemaRef.reference) : schemaRef.reference != null) return false;
        if (schema != null ? !schema.equals(schemaRef.schema) : schemaRef.schema != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = schema != null ? schema.hashCode() : 0;
        result = 31 * result + (reference != null ? reference.hashCode() : 0);
        return result;
    }
}
