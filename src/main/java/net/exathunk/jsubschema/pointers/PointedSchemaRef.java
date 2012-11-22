package net.exathunk.jsubschema.pointers;

import net.exathunk.jsubschema.base.SchemaRef;

/**
 * charolastra 11/22/12 1:29 AM
 */
public class PointedSchemaRef {
    private final SchemaRef schemaRef;
    private final Pointer pointer;

    public PointedSchemaRef(SchemaRef schemaRef) {
        this(schemaRef, new Pointer().reversed());
    }

    public PointedSchemaRef(SchemaRef schemaRef, Pointer pointer) {
        this.schemaRef = schemaRef;
        this.pointer = pointer;
        assert schemaRef != null;
        assert pointer != null;
        assert pointer.getDirection().equals(Direction.UP);
    }

    public SchemaRef getSchemaRef() {
        return schemaRef;
    }

    public Pointer getPointer() {
        return pointer;
    }

    @Override
    public String toString() {
        return "BiRef{" +
                "schemaRef=" + schemaRef +
                ", pointer=" + pointer +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PointedSchemaRef)) return false;

        PointedSchemaRef pointedSchemaRef = (PointedSchemaRef) o;

        if (!pointer.equals(pointedSchemaRef.pointer)) return false;
        if (!schemaRef.equals(pointedSchemaRef.schemaRef)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = schemaRef.hashCode();
        result = 31 * result + pointer.hashCode();
        return result;
    }

    public String toPointedString() {
        return schemaRef.getReference().toReferenceString()+";"+pointer.reversed().toPointerString();
    }
}
