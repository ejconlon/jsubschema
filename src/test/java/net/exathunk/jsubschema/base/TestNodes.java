package net.exathunk.jsubschema.base;

import net.exathunk.jsubschema.gen.Assembler;
import net.exathunk.jsubschema.gen.ClassRep;
import net.exathunk.jsubschema.gen.SchemaRepper;
import net.exathunk.jsubschema.schema.Loader;
import net.exathunk.jsubschema.schema.schema.Schema;
import net.exathunk.jsubschema.schema.schema.SchemaFactory;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Test;

import java.io.IOException;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * charolastra 11/13/12 7:18 PM
 */
public class TestNodes {

    final String[][] properties = new String[][] {
            {"{\"foo\":1,\"bar\":false}", "foo,bar"},
            {"{}", ""},
            {"[1,2,3]", ""}
    };

    @Test
    public void testProperties() throws IOException {
        for (String[] ss : properties) {
            JsonNode node = Util.parse(ss[0]);
            Set<String> as = Util.propsForNode(node);
            Set<String> bs = Util.asSet(Util.split(ss[1], ","));
            assertEquals(as, bs);
        }
    }

    public void runTestSchema(String name) throws IOException, TypeException {
        JsonNode node = Loader.loadSchemaNode(name);
        System.out.println(node);

        Binder<Schema> binder = new JacksonBinder<Schema>(new ObjectMapper(), new SchemaFactory());
        Schema schema = binder.bind(node);
        System.out.println(schema);
    }

    @Test
    public void testSchema() throws IOException, TypeException {
        for (String name : Util.split("schema,address,event,geo,link,card", ",")) {
        //for (String name : Util.split("schema", ",")) {
            runTestSchema(name);
        }
    }

    @Test
    public void testloadSession() throws IOException, TypeException {
        Session session = Session.loadDefaultSession();
        System.out.println(session);
    }

    @Test
    public void testGenSchema() throws IOException, TypeException {
        final Session session = Session.loadDefaultSession();
        final Schema schema = session.schemas.get("http://exathunk.net/schemas/schema");
        assertNotNull(schema);
        final ClassRep classRep = SchemaRepper.makeClass(schema, "net.exathunk.jsubschema.genschema");
        final String classString = Assembler.writeClass(classRep);
        System.out.println(classString);
        final ClassRep factoryRep = SchemaRepper.makeFactory(schema, "net.exathunk.jsubschema.genschema");
        final String factoryString = Assembler.writeClass(factoryRep);
        System.out.println(factoryString);
    }

}
