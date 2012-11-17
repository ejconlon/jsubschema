package net.exathunk.jsubschema.base;

import net.exathunk.jsubschema.Util;
import net.exathunk.jsubschema.functional.Either;
import net.exathunk.jsubschema.functional.Either3;
import net.exathunk.jsubschema.genschema.SchemaLike;
import net.exathunk.jsubschema.pointers.Reference;

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
    public Either3<SchemaLike, String, Reference> resolveRef(Reference ref) {
        Iterator<RefResolver> it = resolvers.iterator();
        while (it.hasNext()) {
            final RefResolver resolver = it.next();
            Either3<SchemaLike, String, Reference> either = resolver.resolveRef(ref);
            if (either.isFirst()) {
                return Either3.makeFirst(either.getFirst());
            } else if (either.isSecond()) {
                return Either3.makeSecond(either.getSecond());
            } else {
                if (ref.equals(either.getThird())) {
                    continue;
                } else {
                    ref = either.getThird();
                    it = resolvers.iterator();
                }
            }
        }
        return Either3.makeSecond("Unresolved ref: "+ref);
    }

    public static Either<SchemaLike, String> resolveRefString(String refString, FullRefResolver fullRefResolver) {
        Either<Reference, String> eitherReference = Reference.fromReferenceString(refString);
        if (eitherReference.isSecond()) return Either.makeSecond(eitherReference.getSecond());
        return recursiveResolve(eitherReference.getFirst(), fullRefResolver);
    }

    public static Either<SchemaLike,String> recursiveResolve(Reference reference, FullRefResolver fullRefResolver) {
        return fullRefResolver.fullyResolveRef(Either3.<SchemaLike, String, Reference>makeThird(reference));
    }

    @Override
    public Either<SchemaLike, String> fullyResolveRef(Either3<SchemaLike, String, Reference> either3) {
        if (either3.isFirst()) {
            return Either.makeFirst(either3.getFirst());
        } else if (either3.isSecond()) {
            return Either.makeSecond(either3.getSecond());
        } else {
            return fullyResolveRef(resolveRef(either3.getThird()));
        }
    }
}
