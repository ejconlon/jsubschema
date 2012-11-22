package net.exathunk.jsubschema.base;

import net.exathunk.jsubschema.genschema.schema.SchemaLike;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertNotNull;

/**
 * charolastra 11/22/12 4:23 AM
 */
public class TestPather {


    @Test
    public void testPather() throws IOException, TypeException {
        final Session session = Session.loadDefaultSession();
        final SchemaLike schema = session.schemas.get("http://exathunk.net/schemas/stringmultimap");
        assertNotNull(schema);





        /*

        (URL) -> (Schema, Reference)

        (Schema, Reference, Pointer) -> (Schema, Reference) | (Reference, Pointer)

         */


        //Either<SchemaRef, String> rootSchemaRef = Either.makeFirst(new SchemaRef(schema, new Reference("http://exathunk.net/schemas/stringmultimap", new Pointer())));
        //assertEquals(rootSchemaRef, resolve(fullRefResolver, "#"));
        //assertEquals(rootSchemaRef, resolve(fullRefResolver, "http://exathunk.net/schemas/stringmultimap#"));

        // DOESN'T WORK: resolve + Pather work from domain paths -> Schema.  Need another function to work from schema paths -> schema.
        //Either<SchemaRef, String> saSchemaRef = Either.<SchemaRef, String>makeFirst(new SchemaRef(schema.getDeclarations().get("stringArray"), baseRef.cons(Part.asKey("declarations")).cons(Part.asKey("stringArray"))));
        //assertEquals(saSchemaRef, resolve(fullRefResolver, "http://exathunk.net/schemas/stringmultimap#/declarations/stringArray"));
        //assertEquals(saSchemaRef, resolve(fullRefResolver, schema.getItems().get__dollar__ref()));

        //assertEquals(schema.getItems(), resolve(fullRefResolver, "#/items"));
    }

}
