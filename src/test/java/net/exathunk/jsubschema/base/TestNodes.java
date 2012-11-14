package net.exathunk.jsubschema.base;

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
    public void testProperties() {
        for (String[] ss : properties) {
            Thing thing = Util.parse(ss[0]);
            Set<String> as = TypeInfo.propsForThing(thing);
            Set<String> bs = Util.asSet(Util.split(ss[1], ","));
            assertEquals(as, bs);
        }
    }

    public void runTestSchema(String name) throws IOException, TypeException {
        Thing thing = Loader.loadSchemaThing(name);
        System.out.println(thing);

        SchemaBuilder builder = new SchemaBuilder(thing);
        Schema schema = builder.build();
        System.out.println(schema);
    }

    @Test
    public void testSchema() throws IOException, TypeException {
        for (String name : Util.split("schema,address,event,geo,link,card", ",")) {
            runTestSchema(name);
        }
    }

}
