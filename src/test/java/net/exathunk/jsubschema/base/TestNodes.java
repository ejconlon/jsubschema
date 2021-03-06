package net.exathunk.jsubschema.base;

import net.exathunk.jsubschema.Util;
import net.exathunk.jsubschema.gen.Loader;
import net.exathunk.jsubschema.genschema.schema.Schema;
import net.exathunk.jsubschema.genschema.schema.SchemaFactory;
import org.codehaus.jackson.JsonNode;
import org.junit.Test;

import java.io.IOException;
import java.util.Set;

import static org.junit.Assert.assertEquals;

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
            Set<String> as = Util.asSet(node.getFieldNames());
            Set<String> bs = Util.asSet(Util.split(ss[1], ","));
            assertEquals(as, bs);
        }
    }

    public void runTestSchema(String name) throws IOException, TypeException {
        JsonNode node = Loader.loadSchemaNode(name);
        System.out.println(node);

        Binder<Schema> binder = new JacksonBinder<Schema>(Util.makeObjectMapper(), new SchemaFactory());
        Schema schema = binder.bind(node);
        System.out.println(schema);
    }

    /*@Test
    public void testSchema() throws IOException, TypeException {
        for (String name : Util.split("schema,address,event,geo,link", ",")) {
        //for (String name : Util.split("schema", ",")) {
            runTestSchema(name);
        }
    }*/

    /*@Test
    public void testloadSession() throws IOException, TypeException {
        Session session = Session.loadDefaultSession();
        System.out.println(session);
    }*/


}
