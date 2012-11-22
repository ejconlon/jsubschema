package net.exathunk.jsubschema.pointers;

/**
 * charolastra 11/22/12 1:29 AM
 */
public class PointedRef {
    private final Reference reference;
    private final Pointer pointer;

    public PointedRef(Reference reference) {
        this(reference, new Pointer().reversed());
    }

    public PointedRef(Reference reference, Pointer pointer) {
        this.reference = reference;
        this.pointer = pointer;
        assert reference != null;
        assert pointer != null;
        assert pointer.getDirection().equals(Direction.UP);
    }

    public Reference getReference() {
        return reference;
    }

    public Pointer getPointer() {
        return pointer;
    }

    @Override
    public String toString() {
        return "PointedSchemaRef{" +
                "reference=" + reference +
                ", pointer=" + pointer +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PointedRef)) return false;

        PointedRef that = (PointedRef) o;

        if (!pointer.equals(that.pointer)) return false;
        if (!reference.equals(that.reference)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = reference.hashCode();
        result = 31 * result + pointer.hashCode();
        return result;
    }

    public String toPointedString() {
        return reference.toReferenceString()+";"+pointer.reversed().toPointerString();
    }
}
