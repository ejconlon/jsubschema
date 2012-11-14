package net.exathunk.jsubschema.base;

import java.util.*;

/**
 * charolastra 11/13/12 10:22 PM
 */
public class SchemaBuilder {
    private final Thing thing;

    public SchemaBuilder(Thing thing) {
        this.thing = thing;
        assert thing != null;
    }

    private static final Set<String> REQUIRED_PROPS = Util.asSet(Arrays.asList("type"));
    private static final Set<String> OPTIONAL_PROPS = Util.asSet(Arrays.asList(
            "properties", "items", "required", "format", "id", "ref", "requires", "forbids", "description"));
    private static final Set<String> ALL_PROPS;

    static {
        ALL_PROPS = new TreeSet<String>();
        ALL_PROPS.addAll(REQUIRED_PROPS);
        ALL_PROPS.addAll(OPTIONAL_PROPS);
    }

    public Schema build() throws TypeException {
        final Set<String> props = TypeInfo.propsForThing(thing);
        if (!props.containsAll(REQUIRED_PROPS)) {
            throw new TypeException("Missing props: "+thing);
        } else if (!ALL_PROPS.containsAll(props)) {
            for (String prop : props) {
                if (!ALL_PROPS.contains(prop)) {
                    throw new TypeException("THIS ONE: "+prop);
                }
            }
            throw new TypeException("Extra props: "+thing);
        }
        final Schema s = new Schema();
        final Map<String, Thing> rootMap = thing.getObject();
        for (Map.Entry<String, Thing> entry : rootMap.entrySet()) {
            final String key = entry.getKey();
            final Thing thing = entry.getValue();
            if (key.equals("type")) {
                TypeException.assertThat(thing.isString(), "type must be string");
                s.type = thing.getString();
            } else if (key.equals("required")) {
                TypeException.assertThat(thing.isBoolean(), "required must be boolean");
                s.required = thing.getBoolean();
            } else if (key.equals("id")) {
                TypeException.assertThat(thing.isString(), "id must be string");
                s.id = thing.getString();
            } else if (key.equals("ref")) {
                TypeException.assertThat(thing.isString(), "ref must be string");
                s.ref = thing.getString();
            } else if (key.equals("format")) {
                TypeException.assertThat(thing.isString(), "format must be string");
                s.format = thing.getString();
            } else if (key.equals("description")) {
                TypeException.assertThat(thing.isString(), "description must be string");
                s.description = thing.getString();
            } else if (key.equals("forbids")) {
                TypeException.assertThat(thing.isArray(), "forbids must be array");
                List<String> list = new ArrayList<String>();
                for (Thing child : thing.getArray()) {
                    TypeException.assertThat(child.isString(), "forbids item must be string");
                    list.add(child.getString());
                }
                s.forbids = list;
            } else if (key.equals("requires")) {
                TypeException.assertThat(thing.isArray(), "requires must be array");
                List<String> list = new ArrayList<String>();
                for (Thing child : thing.getArray()) {
                    TypeException.assertThat(child.isString(), "requires item must be string");
                    list.add(child.getString());
                }
                s.requires = list;
            } else if (key.equals("properties")) {
                TypeException.assertThat(thing.isObject(), "properties must be object");
                Map<String, Schema> map = new TreeMap<String, Schema>();
                for (Map.Entry<String, Thing> child : thing.getObject().entrySet()) {
                    SchemaBuilder builder = new SchemaBuilder(child.getValue());
                    Schema s2 = builder.build();
                    map.put(child.getKey(), s2);
                }
                s.properties = map;
            }
        }
        return s;
    }
}
