package net.exathunk.jsubschema.base;

import net.exathunk.jsubschema.functional.Either3;
import net.exathunk.jsubschema.genschema.schema.SchemaLike;
import net.exathunk.jsubschema.pointers.PointedSchemaRef;
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
    public Either3<SchemaRef, String, PointedSchemaRef> resolveRef(PointedSchemaRef pointedSchemaRef) {
        final Reference otherReference = pointedSchemaRef.getSchemaRef().getReference();
        if (otherReference.getUrl().isEmpty() || selfRef.getUrl().equals(otherReference.getUrl())) {
            // TODO account for offset
            return Pather.pathSchema(new PointedSchemaRef(new SchemaRef(self, selfRef), pointedSchemaRef.getPointer()));
        } else {
            return Either3.makeThird(pointedSchemaRef);
        }
    }
}
