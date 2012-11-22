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
            if (poundIndex < 0) {
                url = ref;
                pathString = "";
            } else if (poundIndex == 0) {
                url = "";
                pathString = ref.substring(1);
            } else if (poundIndex == ref.length()-1) {
                url = ref.substring(0, ref.length()-1);
                pathString = "";
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
        assert !id.contains("#");
        return new Reference(id, new Pointer());
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

    public Reference consAll(Pointer nextParts) {
        assert nextParts.getDirection().equals(Direction.UP);
        Reference r = this;
        for (Part part : nextParts) {
            r = r.cons(part);
        }
        return r;
    }

    public Reference withDefaultId(String id) {
        if (id == null || url.length() > 0) return this;
        else return new Reference(id, getPointer());
    }

    @Override
    public String toString() {
        return "Reference{" +
                " url='" + url + '\'' +
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
        int result = 0;
        result = 31 * result + url.hashCode();
        result = 31 * result + pointer.hashCode();
        return result;
    }
}
