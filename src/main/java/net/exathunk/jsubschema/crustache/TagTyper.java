package net.exathunk.jsubschema.crustache;

import net.exathunk.jsubschema.functional.Pair;
import net.exathunk.jsubschema.genschema.schema.Schema;
import net.exathunk.jsubschema.genschema.schema.SchemaLike;
import net.exathunk.jsubschema.genschema.schema.declarations.keylist.KeyList;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * charolastra 11/20/12 12:55 PM
 */
public class TagTyper {

    public static Map<String, String> convertKV(List<String> filters) {
        Map<String, String> m = new TreeMap<String, String>();
        for (String f : filters) {
            String[] kv = f.split(":");
            if (kv.length == 2) {
                m.put(kv[0], kv[1]);
            }
        }
        return m;
    }

    private static Pair<SchemaLike, Boolean> makeTagSchema(Tag tag) {
        Map<String, String> kv = convertKV(tag.getFilters());
        final String stype;
        final String format;
        final boolean required;

        if (kv.containsKey("required")) {
            required = Boolean.parseBoolean(kv.get("required"));
        } else {
            if (tag.getType().equals(Tag.Type.SECTION_START)) {
                required = false;
            } else if (tag.getType().equals(Tag.Type.INVERTED_START)) {
                required = false;
            } else if (tag.getType().equals(Tag.Type.SECTION_END)) {
                throw new IllegalArgumentException("SECTION END CANNOT BE TYPED");
            } else {
                required = true;
            }
        }

        if (kv.containsKey("type")) {
            stype = kv.get("type");
        } else {
            if (tag.getType().equals(Tag.Type.SECTION_START)) {
                stype = "array";
            } else if (tag.getType().equals(Tag.Type.INVERTED_START)) {
                stype = "array";
            } else if (tag.getType().equals(Tag.Type.SECTION_END)) {
                throw new IllegalArgumentException("SECTION END CANNOT BE TYPED");
            } else {
                stype = "string";
            }
        }

        if (kv.containsKey("format")) {
            format = kv.get("format");
        } else {
            if (tag.getType().equals(Tag.Type.ESCAPE)) {
                format = "html";
            } else {
                format = null;
            }
        }

        SchemaLike schema = new Schema();
        schema.setType(stype);
        schema.setFormat(format);

        return new Pair<SchemaLike, Boolean>(schema, required);
    }

    public static SchemaLike makeTreeSchema(String name, TagTree tree, NameResolver resolver) {
        SchemaLike schema = new Schema();
        schema.setType("object");
        schema.setProperties(new TreeMap<String, SchemaLike>());
        schema.setId(resolver.resolveName(name));
        return makeTreeSchemaInner(name, tree, schema, resolver);
    }

    private static SchemaLike makeTreeSchemaInner(String rootName, TagTree tree, SchemaLike schema, NameResolver resolver) {
        boolean addedChildren = false;
        if (tree.getParent().isJust()) {
            Tag parent = tree.getParent().getJust();

            Pair<SchemaLike, Boolean> pair = makeTagSchema(parent);
            final SchemaLike parentSchema = pair.getKey();
            addProperty(schema, parent.getLabel(), parentSchema);
            if (Boolean.TRUE.equals(pair.getValue())) {
                addRequired(schema, parent.getLabel());
            }

            final String t = parentSchema.getType();
            if (t.equals("object")) {
                addedChildren = true;
                for (TagTree child : tree.getChildren()) {
                    makeTreeSchemaInner(rootName, child, parentSchema, resolver);
                }
            } else if (t.equals("array")) {
                addedChildren = true;
                SchemaLike itemSchema = new Schema();
                itemSchema.setType("object");
                itemSchema.setProperties(new TreeMap<String, SchemaLike>());
                for (TagTree child : tree.getChildren()) {
                    makeTreeSchemaInner(rootName, child, itemSchema, resolver);
                }
                parentSchema.setItems(itemSchema);
            }
        }

        if (!addedChildren) {
            for (TagTree child : tree.getChildren()) {
                makeTreeSchemaInner(rootName, child, schema, resolver);
            }
        }

        return schema;
    }

    private static void addRequired(SchemaLike schema, String label) {
        if (!schema.hasRequired()) {
            schema.setRequired(new KeyList());
        }
        if (!schema.getRequired().contains(label)) {
            schema.getRequired().add(label);
        }
    }

    private static void addProperty(SchemaLike schema, String label, SchemaLike prop) {
        assert schema.hasProperties();
        assert !schema.getProperties().containsKey(label);
        schema.getProperties().put(label, prop);
    }

}
