package net.exathunk.jsubschema.gen;

import net.exathunk.jsubschema.Util;
import net.exathunk.jsubschema.base.Session;
import net.exathunk.jsubschema.base.TypeException;
import net.exathunk.jsubschema.genschema.geo.Geo;
import net.exathunk.jsubschema.genschema.schema.Schema;
import net.exathunk.jsubschema.genschema.schema.SchemaFactory;
import net.exathunk.jsubschema.genschema.schema.SchemaLike;
import net.exathunk.jsubschema.pointers.Reference;
import org.codehaus.jackson.JsonNode;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * charolastra 11/16/12 11:08 PM
 */
public class TestGen {

    // TODO better assertions :-/
    @Test
    public void testGenSchema() throws IOException, TypeException {
        final Session session = Session.loadDefaultSession();
        final SchemaLike schema = session.schemas.get("http://exathunk.net/schemas/event");
        assertNotNull(schema);
        final ClassRep classRep = SchemaRepper.makeClass(Reference.fromId(schema.getId()), Reference.fromId(schema.getId()), schema, "net.exathunk.jsubschema.genschema.event", "net.exathunk.jsubschema.genschema.event", Util.asSet("foo.bar.GeoLike"));
        final String classString = Assembler.writeClass(classRep);
        assertNotNull(classString);
        //System.out.println(classString);
        final ClassRep factoryRep = SchemaRepper.makeFactory(Reference.fromId(schema.getId()), Reference.fromId(schema.getId()), schema, "net.exathunk.jsubschema.genschema.event", "net.exathunk.jsubschema.genschema.event", Util.asSet("foo.bar.GeoLike"));
        final String factoryString = Assembler.writeClass(factoryRep);
        //System.out.println(factoryString);
        assertNotNull(factoryString);
    }

    @Test
    public void testEquals() {
        final Geo a = new Geo();
        assert a.equals(a);
        assert !a.equals(null);
        assertEquals(Util.asSet("latitude", "longitude"), a.diff(null));

        final Geo b = new Geo();
        assert a.equals(b);
        assert b.equals(a);
        assert a.hashCode() == b.hashCode();
        assert a.toString().equals(b.toString());
        assertEquals(Util.asSet(), a.diff(b));

        a.setLatitude(1.23);
        assert !a.equals(b);
        assert !b.equals(a);
        assert a.hashCode() != b.hashCode();
        assert !a.toString().equals(b.toString());
        assertEquals(Util.asSet("latitude"), a.diff(b));

        b.setLatitude(999.0);
        assert !a.equals(b);
        assert !b.equals(a);
        assert a.hashCode() != b.hashCode();
        assert !a.toString().equals(b.toString());
        assertEquals(Util.asSet("latitude"), a.diff(b));

        b.setLatitude(1.23);
        assert a.equals(b);
        assert b.equals(a);
        assert a.hashCode() == b.hashCode();
        assert a.toString().equals(b.toString());
        assertEquals(Util.asSet(), a.diff(b));

        a.setLongitude(4.56);
        assert !a.equals(b);
        assert !b.equals(a);
        assert a.hashCode() != b.hashCode();
        assert !a.toString().equals(b.toString());
        assertEquals(Util.asSet("longitude"), a.diff(b));

        a.setLatitude(7.89);
        assert !a.equals(b);
        assert !b.equals(a);
        assert a.hashCode() != b.hashCode();
        assert !a.toString().equals(b.toString());
        assertEquals(Util.asSet("latitude", "longitude"), a.diff(b));
    }

    @Test
    public void testArrays() throws IOException, TypeException {
        final String schemaStr = "{\"type\":\"object\", \"forbids\": {\"a\": [\"b\"]}}, \"properties\" : {\"a\": {\"type\":\"integer\"}, \"b\": {\"type\":\"integer\"} }";
        final JsonNode schemaNode = Util.parse(schemaStr);
        final Schema schema = Util.quickBind(schemaNode, new SchemaFactory());
        assertEquals(1, schema.getForbids().size());
        assertEquals(true, schema.getForbids().containsKey("a"));
        assertEquals(1, schema.getForbids().get("a").size());
        assertEquals("b", schema.getForbids().get("a").get(0));
    }

}
