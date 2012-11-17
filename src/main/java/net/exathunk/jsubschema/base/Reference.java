package net.exathunk.jsubschema.base;

/**
 * charolastra 11/16/12 8:42 PM
 */
public class Reference {
    private final String url;
    private final Pointer pointer;

    public Reference(String url, Pointer pointer) {
        this.url = url;
        this.pointer = pointer;
    }

    public Reference cons(Part part) {
        return new Reference(url, pointer.cons(part));
    }

    public String getUrl() {
        return url;
    }

    public boolean hasUrl() {
        return url != null;
    }

    public Pointer getPointer() {
        return pointer;
    }

    public static Either<Reference, String> fromReferenceString(String ref) {
        final String url;
        final String pathString;
        if (ref == null)  {
            url = null;
            pathString = "";
        } else {
            final int poundIndex = ref.indexOf('#');
            if (poundIndex < 0) {
                return Either.makeSecond("Missing #: "+ref);
            } else if (poundIndex == 0) {
                url = null;
                pathString = ref;
            } else {
                String[] parts = ref.split("#");
                if (parts.length != 2) {
                    return Either.makeSecond("Too many #s: "+ref);
                } else {
                    url = parts[0];
                    pathString = parts[1];
                }
            }
        }
        Either<Pointer, String> eitherPath = Pointer.fromPointerString(pathString);
        if (eitherPath.isSecond()) {
            return Either.makeSecond(eitherPath.getSecond());
        } else {
            return Either.makeFirst(new Reference(url, eitherPath.getFirst()));
        }
    }

    @Override
    public String toString() {
        return "Reference{ referenceString="+toReferenceString()+" }";
    }

    public String toReferenceString() {
        return (url == null ? "" : url) + "#" + pointer.toPointerString();
    }

    public static Reference fromId(String id, String def) {
        Either<Reference, String> eitherRef = fromReferenceString(id);
        if (eitherRef.isFirst() && eitherRef.getFirst().getPointer().isEmpty()) {
            return eitherRef.getFirst();
        } else {
            return new Reference(def, new Pointer());
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Reference)) return false;

        Reference reference = (Reference) o;

        if (!pointer.equals(reference.pointer)) return false;
        if (url != null ? !url.equals(reference.url) : reference.url != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = url != null ? url.hashCode() : 0;
        result = 31 * result + pointer.hashCode();
        return result;
    }
}
