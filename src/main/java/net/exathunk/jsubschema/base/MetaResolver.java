package net.exathunk.jsubschema.base;

import net.exathunk.jsubschema.Util;
import net.exathunk.jsubschema.functional.Either;
import net.exathunk.jsubschema.functional.Either3;
import net.exathunk.jsubschema.pointers.PointedRef;

import java.util.Iterator;

/**
 * charolastra 11/16/12 10:03 PM
 */
public class MetaResolver implements RefResolver, FullRefResolver {

    private final Iterable<RefResolver> resolvers;

    public MetaResolver(RefResolver... resolvers) {
        this(Util.asList(resolvers));
    }

    public MetaResolver(Iterable<RefResolver> resolvers) {
        this.resolvers = resolvers;
    }

    @Override
    public Either3<SchemaRef, String, PointedRef> resolveRef(PointedRef pointedRef) {
        Iterator<RefResolver> it = resolvers.iterator();
        while (it.hasNext()) {
            final RefResolver resolver = it.next();
            Either3<SchemaRef, String, PointedRef> either = resolver.resolveRef(pointedRef);
            if (either.isFirst()) {
                return Either3.makeFirst(either.getFirst());
            } else if (either.isSecond()) {
                return Either3.makeSecond(either.getSecond());
            } else {
                if (pointedRef.equals(either.getThird())) {
                    continue;
                } else {
                    pointedRef = either.getThird();
                    it = resolvers.iterator();
                }
            }
        }
        return Either3.makeSecond("Unresolved ref: "+pointedRef.toPointedString());
    }

    @Override
    public Either<SchemaRef, String> fullyResolveRef(PointedRef pointedRef) {
        Either3<SchemaRef, String, PointedRef> either3 = resolveRef(pointedRef);
        if (either3.isFirst()) {
            return Either.makeFirst(either3.getFirst());
        } else if (either3.isSecond()) {
            return Either.makeSecond(either3.getSecond());
        } else {
            return fullyResolveRef(either3.getThird());
        }
    }
}
