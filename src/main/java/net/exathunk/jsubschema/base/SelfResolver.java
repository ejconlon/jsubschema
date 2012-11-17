package net.exathunk.jsubschema.base;

import net.exathunk.jsubschema.genschema.SchemaLike;

/**
 * charolastra 11/16/12 10:26 PM
 */
public class SelfResolver implements RefResolver {

    private final SchemaLike self;
    private final Reference selfRef;

    public SelfResolver(SchemaLike self) {
        this.self = self;
        this.selfRef = Reference.fromId(self.getId());
    }

    @Override
    public Either<SchemaLike, String> resolveRef(Reference reference, RefResolver root) {
        if (reference.getUrl().isEmpty() || selfRef.getUrl().equals(reference.getUrl())) {
            Either<SchemaLike, String> eitherSchema = Pather.pathSchema(self, reference.getPointer().reversed(), root);
            if (eitherSchema.isSecond()) return Either.makeSecond(eitherSchema.getSecond());
            else return Either.makeFirst(eitherSchema.getFirst());
        } else {
            return Either.makeSecond("URL mismatch: "+selfRef.getUrl()+" "+reference.getUrl());
        }
    }
}
