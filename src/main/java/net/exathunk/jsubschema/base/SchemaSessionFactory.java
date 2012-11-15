package net.exathunk.jsubschema.base;

import org.codehaus.jackson.JsonNode;

import java.io.IOException;

/**
 * charolastra 11/14/12 3:44 PM
 */
public class SchemaSessionFactory {
    Session makeSession() throws IOException, TypeException {
        final Session session = new Session();

        session.binders.put(Schema.class, new AutomaticSchemaBinder());

        for (String name : Loader.listSchemas()) {
            final JsonNode node = Loader.loadSchemaNode(name);
            final Schema schema = (Schema)session.binders.get(Schema.class).bind(node);
            if (schema.id == null) throw new TypeException("Null id in "+name);
            session.schemas.put(schema.id, schema);
        }

        return session;
    }
}
