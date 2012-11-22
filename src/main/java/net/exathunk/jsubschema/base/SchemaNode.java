package net.exathunk.jsubschema.base;

import net.exathunk.jsubschema.functional.Either;
import net.exathunk.jsubschema.genschema.schema.SchemaLike;
import net.exathunk.jsubschema.pointers.PointedRef;
import net.exathunk.jsubschema.pointers.Reference;

import java.util.Iterator;

/**
 * charolastra 11/17/12 2:10 AM
 */
public class SchemaNode implements Iterable<SchemaNode> {
    private final SchemaRef superRootSchema;
    private final Either<SchemaRef, String> eitherSchema;
    private final PointedNode pointedNode;
    private final FullRefResolver fullRefResolver;

    public SchemaNode(SchemaLike schema, PointedNode pointedNode, FullRefResolver fullRefResolver) {
        this(new SchemaRef(schema, Reference.fromId(schema.getId())), pointedNode, fullRefResolver);
    }

    public SchemaNode(SchemaRef schemaRef, PointedNode pointedNode, FullRefResolver fullRefResolver) {
        this(Either.<SchemaRef, String>makeFirst(schemaRef), pointedNode, fullRefResolver, schemaRef);
    }

    private SchemaNode(Either<SchemaRef, String> eitherSchema, PointedNode pointedNode, FullRefResolver fullRefResolver, SchemaRef superRootSchema) {
        this.superRootSchema = superRootSchema;
        this.eitherSchema = eitherSchema;
        this.pointedNode = pointedNode;
        this.fullRefResolver = fullRefResolver;
        assert superRootSchema != null;
        assert eitherSchema != null;
        assert pointedNode != null;
        assert fullRefResolver != null;
    }

    @Override
    public Iterator<SchemaNode> iterator() {
        return new SchemaTupleIterator(this);
    }

    public Either<SchemaRef, String> getEitherSchema() {
        return eitherSchema;
    }

    public PointedNode getPointedNode() {
        return pointedNode;
    }

    public String toPathString() {
        if (eitherSchema.isFirst()) {
            return eitherSchema.getFirst().getReference().toReferenceString()+";"+pointedNode.getPointer().toPointerString();
        } else {
            return "?;"+pointedNode.getPointer().toPointerString();
        }
    }

    private static class SchemaTupleIterator implements Iterator<SchemaNode> {
        private final SchemaNode root;
        private final Iterator<PointedNode> it;

        public SchemaTupleIterator(SchemaNode root) {
            this.root = root;
            it = root.pointedNode.iterator();
        }

        @Override
        public boolean hasNext() {
            return it.hasNext();
        }

        @Override
        public SchemaNode next() {
            final PointedNode next = it.next();
            if (root.eitherSchema.isFirst()) {
                final Either<SchemaRef, String> eitherSchema = root.fullRefResolver.fullyResolveRef(
                        new PointedRef(root.superRootSchema.getReference(),
                                next.getPointer().reversed()));
                return new SchemaNode(eitherSchema, next, root.fullRefResolver, root.superRootSchema);
            } else {
                return new SchemaNode(root.eitherSchema, next, root.fullRefResolver, root.superRootSchema);
            }
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }
}
