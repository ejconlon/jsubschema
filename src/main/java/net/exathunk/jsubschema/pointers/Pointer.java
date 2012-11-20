package net.exathunk.jsubschema.pointers;

import net.exathunk.jsubschema.functional.ConsList;
import net.exathunk.jsubschema.functional.Either;
import net.exathunk.jsubschema.Util;

import java.util.Iterator;
import java.util.List;

/**
 * charolastra 11/15/12 11:52 AM
 */
public class Pointer implements Comparable<Pointer>, Consable<Part, Pointer>, Reversible<Pointer>, Iterable<Part> {

    private final Direction direction;
    private final ConsList<Part> parts;

    public Pointer() {
        this(Direction.DOWN, ConsList.<Part>nil());
    }

    private Pointer(Direction direction, ConsList<Part> parts) {
        this.direction = direction;
        this.parts = parts;
    }

    public Pointer(Direction direction) {
        this(direction, ConsList.<Part>nil());
    }

    @Override
    public Pointer cons(Part part) {
        return new Pointer(direction, parts.cons(part));
    }

    @Override
    public Pointer getTail() {
        assert !isEmpty();
        return new Pointer(direction, parts.getTail());
    }

    @Override
    public boolean isEmpty() {
        return parts.isEmpty();
    }

    public Part getHead() {
        return parts.getHead();
    }

    @Override
    public Direction getDirection() {
        return direction;
    }

    private void toPointerString(StringBuilder sb) {
        if (!isEmpty()) {
            getTail().toPointerString(sb);
            sb.append("/").append(getHead().toPointerString());
        }
    }

    public String toPointerString() {
        StringBuilder sb = new StringBuilder();
        toPointerString(sb);
        return sb.toString();
    }

    public static Either<Pointer, String> fromPointerString(String pointer) {
        if (pointer == null) return Either.makeSecond("Null pointer");
        Pointer path = new Pointer();
        if (pointer.length() > 0) {
            if (pointer.contains("#") || pointer.charAt(0) != '/') {
                return Either.makeSecond("Invalid pointer: "+pointer);
            } else {
                List<String> parts = Util.asList(pointer.substring(1).split("/"));
                for (int i = 0; i < parts.size(); ++i){
                    String raw = parts.get(i);
                    Part part = Part.fromPointerString(raw);
                    path = path.cons(part);
                }
            }
        }
        return Either.makeFirst(path);
    }

    @Override
    public String toString() {
        return "Path{ direction='"+direction+"', pointerString='"+toPointerString()+"' }";
    }

    @Override
    public int compareTo(Pointer pointer) {
        return toPointerString().compareTo(pointer.toPointerString());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Pointer)) return false;

        Pointer pointer = (Pointer) o;

        if (direction != pointer.direction) return false;
        if (!parts.equals(pointer.parts)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = direction.hashCode();
        result = 31 * result + parts.hashCode();
        return result;
    }

    @Override
    public Pointer reversed() {
        return reversed(this, new Pointer(direction.reversed(), ConsList.<Part>nil()));
    }

    private static Pointer reversed(Pointer source, Pointer sink) {
        if (source.isEmpty()) return sink;
        else return reversed(source.getTail(), sink.cons(source.getHead()));
    }

    @Override
    public Iterator<Part> iterator() {
        return parts.iterator();
    }

    public int size() {
        if (isEmpty()) return 0;
        else return 1 + getTail().size();
    }

    public Pointer consAll(Pointer pointer) {
        assert !direction.equals(pointer.getDirection());
        Pointer p = this;
        for (Part part : pointer) {
            p = p.cons(part);
        }
        return p;
    }
}
