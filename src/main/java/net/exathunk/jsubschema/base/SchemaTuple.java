package net.exathunk.jsubschema.base;

import net.exathunk.jsubschema.functional.Either;
import net.exathunk.jsubschema.functional.Either3;
import net.exathunk.jsubschema.genschema.SchemaLike;
import net.exathunk.jsubschema.pointers.Reference;

import java.util.Iterator;

/**
 * charolastra 11/17/12 2:10 AM
 */
public class SchemaTuple implements Iterable<SchemaTuple> {
    private final Either<SchemaLike, String> eitherSchema;
    private final RefTuple refTuple;
    private final FullRefResolver fullRefResolver;

    public SchemaTuple(SchemaLike schema, RefTuple refTuple, FullRefResolver fullRefResolver) {
        this(Either.<SchemaLike, String>makeFirst(schema), refTuple, fullRefResolver);
    }

    private SchemaTuple(Either<SchemaLike, String> eitherSchema, RefTuple refTuple, FullRefResolver fullRefResolver) {
        this.eitherSchema = eitherSchema;
        this.refTuple = refTuple;
        this.fullRefResolver = fullRefResolver;
    }

    @Override
    public Iterator<SchemaTuple> iterator() {
        return new SchemaTupleIterator(this);
    }

    public Either<SchemaLike, String> getEitherSchema() {
        return eitherSchema;
    }

    public RefTuple getRefTuple() {
        return refTuple;
    }

    private static class SchemaTupleIterator implements Iterator<SchemaTuple> {
        private final SchemaTuple root;
        private final Iterator<RefTuple> it;

        public SchemaTupleIterator(SchemaTuple root) {
            this.root = root;
            it = root.refTuple.iterator();
        }

        @Override
        public boolean hasNext() {
            return it.hasNext();
        }

        @Override
        public SchemaTuple next() {
            final RefTuple next = it.next();
            final Either<SchemaLike, String> eitherSchema = root.fullRefResolver.fullyResolveRef(Either3.<SchemaLike, String, Reference>makeThird(next.getReference()));
            return new SchemaTuple(eitherSchema, next, root.fullRefResolver);
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }
}
