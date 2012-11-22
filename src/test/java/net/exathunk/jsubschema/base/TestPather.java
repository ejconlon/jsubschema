package net.exathunk.jsubschema.base;

import net.exathunk.jsubschema.functional.Either3;
import net.exathunk.jsubschema.genschema.schema.SchemaLike;
import net.exathunk.jsubschema.pointers.Part;
import net.exathunk.jsubschema.pointers.PointedRef;
import net.exathunk.jsubschema.pointers.Pointer;
import net.exathunk.jsubschema.pointers.Reference;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * charolastra 11/22/12 4:23 AM
 */
public class TestPather {

    @Test
    public void testPathSchema() throws IOException, TypeException {
        final Session session = Session.loadDefaultSession();
        final SchemaLike schema = session.schemas.get("http://exathunk.net/schemas/stringmultimap");
        assertNotNull(schema);
        final Reference baseRef = Reference.fromId(schema.getId());

        Either3<SchemaRef, String, PointedRef> goldRootRef = Either3.makeFirst(new SchemaRef(schema, new Reference("http://exathunk.net/schemas/stringmultimap", new Pointer())));
        Either3<SchemaRef, String, PointedRef> testRootRef = Pather.pathSchema(new SchemaRef(schema), new Pointer().reversed());
        assertEquals(goldRootRef, testRootRef);


        Either3<SchemaRef, String, PointedRef> goldSaRef = Either3.makeFirst(new SchemaRef(schema.getDeclarations().get("stringArray"), baseRef.cons(Part.asKey("declarations")).cons(Part.asKey("stringArray"))));
        Either3<SchemaRef, String, PointedRef> testSaRef = Pather.pathSchema(new SchemaRef(schema), new Pointer().reversed().cons(Part.asKey("stringArray")).cons(Part.asKey("declarations")));
        assertEquals(goldSaRef, testSaRef);

        Either3<SchemaRef, String, PointedRef> goldItemsRef = Either3.makeFirst(new SchemaRef(schema.getDeclarations().get("stringArray").getItems(), baseRef.cons(Part.asKey("declarations")).cons(Part.asKey("stringArray")).cons(Part.asKey("items"))));
        Either3<SchemaRef, String, PointedRef> testItemsRef = Pather.pathSchema(new SchemaRef(schema), new Pointer().reversed().cons(Part.asKey("items")).cons(Part.asKey("stringArray")).cons(Part.asKey("declarations")));
        assertEquals(goldItemsRef, testItemsRef);
    }

}
