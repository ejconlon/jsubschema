package net.exathunk.jsubschema.base;

import net.exathunk.jsubschema.Util;
import net.exathunk.jsubschema.gen.Loader;
import net.exathunk.jsubschema.gendeps.DomainFactory;
import net.exathunk.jsubschema.genschema.schema.Schema;
import net.exathunk.jsubschema.genschema.schema.SchemaFactory;
import net.exathunk.jsubschema.genschema.schema.SchemaLike;
import net.exathunk.jsubschema.validation.SchemaValidator;
import net.exathunk.jsubschema.validation.VContext;
import net.exathunk.jsubschema.validation.VError;
import net.exathunk.jsubschema.validation.Validator;
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
    public final ObjectMapper mapper = Util.makeObjectMapper();
    public final Map<String, SchemaLike> schemas = new TreeMap<String, SchemaLike>();
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

    public VContext validate()  {
        VContext context = new VContext();
        Validator validator = new SchemaValidator();
        for (SchemaLike schema : schemas.values()) {
            try {
                JsonNode node = Util.quickUnbind(schema);
                VContext subContext =  Util.runValidator(validator, this, schemas.get("http://exathunk.net/schemas/schema"), node);
                context.subsume(schema.getId()+" => ", subContext);
            } catch (TypeException e) {
                context.errors.add(new VError("ROOT", "Could not unbind schema "+schema.getId()));
            }
        }
        return context;
    }

    public void addSchema(SchemaLike schema) {
        if (schema.getId() == null || schema.getId().isEmpty()) throw new IllegalArgumentException("Empty id in "+schema);
        schemas.put(schema.getId(), schema);
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
