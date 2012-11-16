package net.exathunk.jsubschema.base;

import net.exathunk.jsubschema.genschema.Schema;
import org.codehaus.jackson.JsonNode;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * charolastra 11/15/12 12:48 PM
 */
public class PathTuple implements Iterable<PathTuple> {
    public final Either<Schema, String> eitherSchema;
    public final Schema rootSchema;
    public final JsonNode node;
    public final Path path;

    public PathTuple(Schema schema, JsonNode node) {
        this(Either.<Schema, String>makeFirst(schema), schema, node, new Path());
    }

    private PathTuple(Either<Schema, String> eitherSchema, Schema rootSchema, JsonNode node, Path path) {
        this.eitherSchema = eitherSchema;
        this.rootSchema = rootSchema;
        this.node = node;
        this.path = path;
        assert eitherSchema != null;
        assert rootSchema != null;
        assert node != null;
        assert path != null;
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
                return pos < size;
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
                    final Either<Schema, String> eitherSchema;
                    if (root.eitherSchema.isFirst()) {
                        eitherSchema = Pather.pathSchemaInner(root.eitherSchema.getFirst(), root.rootSchema, path.reversed());
                    } else {
                        eitherSchema = Either.makeSecond("[recursive failure]");
                    }
                    return new PathTuple(eitherSchema, root.rootSchema, node, path);
                } else if (size >= 0) {
                    final JsonNode node = root.node.get(pos);
                    final Path path = root.path.cons(Part.asIndex(pos));
                    final Either<Schema, String> eitherSchema;
                    if (root.eitherSchema.isFirst()) {
                        eitherSchema = Pather.pathSchemaInner(root.eitherSchema.getFirst(), root.rootSchema, path.reversed());
                    } else {
                        eitherSchema = Either.makeSecond("[recursive failure]");
                    }
                    ++pos;
                    return new PathTuple(eitherSchema, root.rootSchema, node, path);
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
}
