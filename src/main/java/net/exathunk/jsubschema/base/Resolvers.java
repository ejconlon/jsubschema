package net.exathunk.jsubschema.base;

import net.exathunk.jsubschema.genschema.SchemaLike;

/**
 * charolastra 11/16/12 10:03 PM
 */
public class Resolvers {
    public static Either<SchemaLike, String> resolveRefString(String refString, RefResolver resolver) {
        Either<Reference, String> eitherReference = Reference.fromReferenceString(refString);
        if (eitherReference.isSecond()) return Either.makeSecond(eitherReference.getSecond());
        return resolver.resolveRef(eitherReference.getFirst(), resolver);
    }
}
