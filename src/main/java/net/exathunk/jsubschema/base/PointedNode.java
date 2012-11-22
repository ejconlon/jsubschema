package net.exathunk.jsubschema.base;

import net.exathunk.jsubschema.pointers.*;
import org.codehaus.jackson.JsonNode;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * charolastra 11/15/12 12:48 PM
 */
public class PointedNode implements Iterable<PointedNode> {
    private final JsonNode node;
    private final Pointer pointer;

    // TODO add pointer too
    public PointedNode(JsonNode node) {
        this(node, new Pointer());
    }

    private PointedNode(JsonNode node, Pointer pointer) {
        this.node = node;
        this.pointer = pointer;
        assert node != null;
        assert pointer != null;
        assert Direction.DOWN.equals(pointer.getDirection());
    }

    @Override
    public Iterator<PointedNode> iterator() {
        return new PathTupleIterator(this);
    }

    public JsonNode getNode() {
        return node;
    }

    public Pointer getPointer() {
        return pointer;
    }

    private static class PathTupleIterator implements Iterator<PointedNode> {
        private final PointedNode root;
        private final Iterator<String> nextFields;
        private final int size;
        private int pos;

        public PathTupleIterator(PointedNode root) {
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
        public PointedNode next() {
            if (nextFields != null) {
                final String nextField = nextFields.next();
                final JsonNode node = root.node.get(nextField);
                final Pointer pointer = root.pointer.cons(Part.asKey(nextField));
                return new PointedNode(node, pointer);
            } else if (size >= 0) {
                final JsonNode node = root.node.get(pos);
                final Pointer pointer = root.pointer.cons(Part.asIndex(pos));
                ++pos;
                return new PointedNode(node, pointer);
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
