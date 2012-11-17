package net.exathunk.jsubschema.pointers;

import net.exathunk.jsubschema.functional.Either;

/**
 * charolastra 11/16/12 8:42 PM
 */
public class Reference implements Comparable<Reference>, Consable<Part, Reference> {
    private final String url;
    private final Pointer pointer;

    public Reference() {
        this("", new Pointer());
    }

    public Reference(String url, Pointer pointer) {
        this.url = url;
        this.pointer = pointer;
        assert url != null;
        assert pointer != null;
        assert Direction.DOWN.equals(pointer.getDirection());
    }

    public Reference withoutPointer() {
        return new Reference(url, new Pointer());
    }

    @Override
    public Reference cons(Part part) {
        return new Reference(url, pointer.cons(part));
    }

    @Override
    public Reference getTail() {
        return new Reference(url, pointer.getTail());
    }

    @Override
    public boolean isEmpty() {
        return pointer.isEmpty();
    }


    public String getUrl() {
        return url;
    }

    public Pointer getPointer() {
        return pointer;
    }

    public static Either<Reference, String> fromReferenceString(String ref) {
        if (ref == null)  {
            return Either.makeSecond("Null reference");
        } else {
            final String url;
            final String pathString;
            final int poundIndex = ref.indexOf('#');
            final int slashIndex = ref.indexOf('/');
            if (poundIndex < 0) {
                if (slashIndex == 0) {
                    url = "";
                    pathString = ref;
                } else {
                    url = ref;
                    pathString = "";
                }
            } else if (poundIndex == 0) {
                url = "";
                pathString = ref.substring(1);
            } else {
                String[] parts = ref.split("#");
                if (parts.length != 2) {
                    return Either.makeSecond("Too many #s: "+ref);
                } else {
                    url = parts[0];
                    pathString = parts[1];
                }
            }
            Either<Pointer, String> eitherPath = Pointer.fromPointerString(pathString);
            if (eitherPath.isSecond()) {
                return Either.makeSecond(eitherPath.getSecond());
            } else {
                return Either.makeFirst(new Reference(url, eitherPath.getFirst()));
            }
        }
    }

    public String toReferenceString() {
        return url + "#" + pointer.toPointerString();
    }

    public static Reference fromId(String id) {
        if (id == null) id = "";
        return new Reference(id, new Pointer());
    }

    @Override
    public String toString() {
        return "Reference{" +
                "url='" + url + '\'' +
                ", pointer=" + pointer +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Reference)) return false;

        Reference reference = (Reference) o;

        if (!pointer.equals(reference.pointer)) return false;
        if (!url.equals(reference.url)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = url.hashCode();
        result = 31 * result + pointer.hashCode();
        return result;
    }

    @Override
    public int compareTo(Reference reference) {
        int v = url.compareTo(reference.url);
        if (v != 0) {
            return v;
        } else {
            return pointer.compareTo(reference.pointer);
        }
    }
}
