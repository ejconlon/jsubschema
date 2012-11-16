package net.exathunk.jsubschema.base;

import net.exathunk.jsubschema.genschema.Schema;
import org.codehaus.jackson.JsonNode;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * charolastra 11/15/12 12:48 PM
 */
public class PathTuple implements Iterable<PathTuple> {
    public final Schema schema;
    public final Schema rootSchema;
    public final JsonNode node;
    public final Path path;

    public PathTuple(Schema schema, JsonNode node) {
        this(schema, schema, node, new Path());
    }

    public PathTuple(Schema schema, Schema rootSchema, JsonNode node, Path path) {
        this.schema = schema;
        this.rootSchema = rootSchema;
        this.node = node;
        this.path = path;
    }

    @Override
    public Iterator<PathTuple> iterator() {
        return new PathTupleIterator(this);
    }

    private static class PathTupleIterator implements Iterator<PathTuple> {
        private final PathTuple root;
        private final Iterator<String> nextFields;
        private final int size;
        private int pos;

        public PathTupleIterator(PathTuple root) {
            this.root = root;
            pos = 0;
            if (root.node.isObject()) {
                nextFields = root.node.getFieldNames();
                size = -1;
            } else if (root.node.isArray()) {
                nextFields = null;
                size = root.node.size();
            } else {
                nextFields = null;
                size = -1;
            }
        }

        @Override
        public boolean hasNext() {
            if (nextFields != null) {
                return nextFields.hasNext();
            } else if (size >= 0) {
                return pos <= size;
            } else {
                return false;
            }
        }

        @Override
        public PathTuple next() {
            try {
                if (nextFields != null) {
                    final String nextField = nextFields.next();
                    final JsonNode node = root.node.get(nextField);
                    final Path path = root.path.cons(Part.asKey(nextField));
                    final Schema schema = Pather.pathSchemaInner(root.schema, root.rootSchema, path.reversed());
                    return new PathTuple(schema, root.rootSchema, node, path);
                } else if (size >= 0) {
                    final JsonNode node = root.node.get(pos);
                    final Path path = root.path.cons(Part.asIndex(pos));
                    final Schema schema = Pather.pathSchemaInner(root.schema, root.rootSchema, path.reversed());
                    ++pos;
                    return new PathTuple(schema, root.rootSchema, node, path);
                } else {
                    throw new NoSuchElementException();
                }
            } catch (PathException e) {
                throw new NoSuchElementException(e.toString());
            }
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    private void flattened(List<PathTuple> list) {
        list.add(this);
        for (PathTuple child : this) {
            child.flattened(list);
        }
    }

    public List<PathTuple> flattened() {
        List<PathTuple> list = new ArrayList<PathTuple>();
        flattened(list);
        return list;
    }
}
