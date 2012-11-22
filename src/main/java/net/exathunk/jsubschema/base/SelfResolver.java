package net.exathunk.jsubschema.base;

import net.exathunk.jsubschema.functional.Either3;
import net.exathunk.jsubschema.genschema.schema.SchemaLike;
import net.exathunk.jsubschema.pointers.PointedRef;
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
        assert selfRef.getPointer().isEmpty();
    }

    @Override
    public Either3<SchemaRef, String, PointedRef> resolveRef(PointedRef pointedRef) {
        Reference otherReference = pointedRef.getReference();
        if (otherReference.getUrl().isEmpty()) {
            otherReference = new Reference(selfRef.getUrl(), otherReference.getPointer());
        }
        if (selfRef.getUrl().equals(otherReference.getUrl())) {
            return Pather.pathDomain(new SchemaRef(self, otherReference), pointedRef.getPointer());
        } else {
            return Either3.makeThird(pointedRef);
        }
    }
}
