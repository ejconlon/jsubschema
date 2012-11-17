package net.exathunk.jsubschema.base;

import java.util.List;

/**
 * charolastra 11/15/12 11:52 AM
 */
public class Pointer extends ConsList<Part> implements Comparable<Pointer> {

    // DOWN <==> /foo/0 <==> (cons 0 (cons "foo" nil)) <==> points to 1 in { "foo": [1,2,3] }
    // UP <==> /0/foo <==> (cons "foo" (cons 0 nil)) <==> points to [1,2,3] in [{"foo": [1,2,3]}, ...]
    public static enum Direction { DOWN, UP;
        public Direction reversed() {
            if (UP.equals(this)) {
                return DOWN;
            } else {
                return UP;
            }
        }
    };

    private final Direction direction;

    public Pointer() {
        this(Direction.DOWN);
    }

    private Pointer(Direction direction) {
        this(direction, null, null);
    }

    private Pointer(Direction direction, Part head, Pointer tail) {
        super(head, tail);
        this.direction = direction;
    }

    @Override
    public Pointer cons(Part part) {
        return new Pointer(direction, part, this);
    }

    @Override
    public Pointer getTail() {
        return (Pointer)super.getTail();
    }

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

    private Pointer reversed(Pointer pointer) {
        if (!isEmpty()) {
            pointer = pointer.cons(getHead());
            pointer = getTail().reversed(pointer);
        }
        return pointer;
    }

    public Pointer reversed() {
        Pointer pointer = new Pointer(direction.reversed());
        return reversed(pointer);
    }

    @Override
    public String toString() {
        return "Path{ direction='"+direction+"', pointerString='"+toPointerString()+"' }";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Pointer)) return false;
        if (!super.equals(o)) return false;

        Pointer pointer = (Pointer) o;

        if (direction != pointer.direction) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + direction.hashCode();
        return result;
    }

    @Override
    public int compareTo(Pointer pointer) {
        return toPointerString().compareTo(pointer.toPointerString());
    }

}
