package net.exathunk.jsubschema.base;

import java.util.List;

/**
 * charolastra 11/15/12 11:52 AM
 */
public class Path extends ConsList<Part> {

    public Path() {
        this(null, null);
    }

    protected Path(Part head, Path tail) {
        super(head, tail);
    }

    @Override
    public Path cons(Part part) {
        return new Path(part, this);
    }

    @Override
    public Path getTail() {
        return (Path)super.getTail();
    }

    private void toPointer(StringBuilder sb) {
        if (!isEmpty()) {
            getTail().toPointer(sb);
            sb.append("/").append(getHead().toPointerString());
        }
    }

    public String toPointer() {
        StringBuilder sb = new StringBuilder();
        toPointer(sb);
        return sb.toString();
    }

    public static Path fromPointer(String pointer) {
        List<String> parts = Util.split(pointer, "/");
        Path path = new Path();
        for (int i = 0; i < parts.size(); ++i){
            String raw = parts.get(i);
            Part part = Part.asSomething(raw);
            path = path.cons(part);
        }
        return path;
    }

    private Path reversed(Path path) {
        if (!isEmpty()) {
            path = path.cons(getHead());
            path = getTail().reversed(path);
        }
        return path;
    }

    public Path reversed() {
        Path path = new Path();
        return reversed(path);
    }
}
