package net.exathunk.jsubschema.base;

import net.exathunk.jsubschema.pointers.Part;
import net.exathunk.jsubschema.pointers.Reference;
import org.codehaus.jackson.JsonNode;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * charolastra 11/15/12 12:48 PM
 */
public class RefTuple implements Iterable<RefTuple> {
    private final JsonNode node;
    private final Reference reference;

    public RefTuple(JsonNode node) {
        this(node, new Reference());
    }

    private RefTuple(JsonNode node, Reference reference) {
        this.node = node;
        this.reference = reference;
        assert node != null;
        assert reference != null;
    }

    @Override
    public Iterator<RefTuple> iterator() {
        return new PathTupleIterator(this);
    }

    public JsonNode getNode() {
        return node;
    }

    public Reference getReference() {
        return reference;
    }

    private static class PathTupleIterator implements Iterator<RefTuple> {
        private final RefTuple root;
        private final Iterator<String> nextFields;
        private final int size;
        private int pos;

        public PathTupleIterator(RefTuple root) {
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
        public RefTuple next() {
            if (nextFields != null) {
                final String nextField = nextFields.next();
                final JsonNode node = root.node.get(nextField);
                final Reference reference = root.reference.cons(Part.asKey(nextField));
                return new RefTuple(node, reference);
            } else if (size >= 0) {
                final JsonNode node = root.node.get(pos);
                final Reference reference = root.reference.cons(Part.asIndex(pos));
                ++pos;
                return new RefTuple(node, reference);
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
