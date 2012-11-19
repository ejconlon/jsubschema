package net.exathunk.jsubschema.base;

import net.exathunk.jsubschema.functional.Either3;
import net.exathunk.jsubschema.genschema.SchemaLike;
import net.exathunk.jsubschema.pointers.Reference;

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
    public Either3<SchemaRef, String, Reference> resolveRef(Reference reference) {
        if (reference.getUrl().isEmpty() || selfRef.getUrl().equals(reference.getUrl())) {
            return Pather.pathSchema(self, reference);
        } else {
            return Either3.makeThird(reference);
        }
    }
}
