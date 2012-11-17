package net.exathunk.jsubschema.base;

import net.exathunk.jsubschema.genschema.SchemaLike;
import org.codehaus.jackson.JsonNode;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * charolastra 11/15/12 12:48 PM
 */
public class PathTuple implements Iterable<PathTuple> {
    public final Either<SchemaLike, String> eitherSchema;
    public final SchemaLike rootSchema;
    public final JsonNode node;
    public final Reference reference;
    public final RefResolver resolver;

    public PathTuple(SchemaLike schema, JsonNode node, RefResolver resolver) {
        this(Either.<SchemaLike, String>makeFirst(schema), schema, node, Reference.fromId(schema.getId()), resolver);
    }

    private PathTuple(Either<SchemaLike, String> eitherSchema, SchemaLike rootSchema, JsonNode node, Reference reference, RefResolver resolver) {
        this.eitherSchema = eitherSchema;
        this.rootSchema = rootSchema;
        this.node = node;
        this.reference = reference;
        this.resolver = resolver;
        assert eitherSchema != null;
        assert rootSchema != null;
        assert node != null;
        assert reference != null;
        assert resolver != null;
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
            if (nextFields != null) {
                final String nextField = nextFields.next();
                final JsonNode node = root.node.get(nextField);
                final Reference reference = root.reference.cons(Part.asKey(nextField));
                final Either<SchemaLike, String> eitherSchema;
                if (root.eitherSchema.isFirst()) {
                    eitherSchema = Pather.pathSchema(root.rootSchema, reference.getPointer().reversed(), root.resolver);
                } else {
                    eitherSchema = Either.makeSecond("[recursive failure]");
                }
                return new PathTuple(eitherSchema, root.rootSchema, node, reference, root.resolver);
            } else if (size >= 0) {
                final JsonNode node = root.node.get(pos);
                final Reference reference = root.reference.cons(Part.asIndex(pos));
                final Either<SchemaLike, String> eitherSchema;
                if (root.eitherSchema.isFirst()) {
                    eitherSchema = Pather.pathSchema(root.rootSchema, reference.getPointer().reversed(), root.resolver);
                } else {
                    eitherSchema = Either.makeSecond("[recursive failure]");
                }
                ++pos;
                return new PathTuple(eitherSchema, root.rootSchema, node, reference, root.resolver);
            } else {
                throw new NoSuchElementException();
            }
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }
}
