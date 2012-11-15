package net.exathunk.jsubschema.base;

import net.exathunk.jsubschema.gen.Loader;
import net.exathunk.jsubschema.schema.schema.Schema;
import net.exathunk.jsubschema.schema.schema.SchemaFactory;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

/**
 * charolastra 11/14/12 3:42 PM
 */
public class Session {
    public final ObjectMapper mapper = new ObjectMapper();
    public final Map<String, Schema> schemas = new TreeMap<String, Schema>();
    public final Map<Class, Binder> binders = new HashMap<Class, Binder>();

    public static Session loadDefaultSession() throws IOException, TypeException {
        Session session = new Session();
        session.addBinder(new SchemaFactory());
        for (String name : Loader.listSchemas()) {
            final JsonNode node = Loader.loadSchemaNode(name);
            final Schema schema = (Schema)session.binders.get(Schema.class).bind(node);
            session.addSchema(schema);
        }
        return session;
    }

    public void addSchema(Schema schema) {
        if (schema.id == null) throw new IllegalArgumentException("Null id in "+schema);
        schemas.put(schema.id, schema);
    }

    public void addBinder(DomainFactory factory) {
        binders.put(factory.getDomainClass(), new JacksonBinder(mapper, factory));
    }

    @Override
    public String toString() {
        return "Session{" +
                "schemas=" + schemas.keySet() +
                ", binders=" + binders.keySet() +
                '}';
    }
}
