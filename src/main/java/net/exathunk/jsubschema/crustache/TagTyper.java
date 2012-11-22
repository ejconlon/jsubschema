package net.exathunk.jsubschema.crustache;

import net.exathunk.jsubschema.base.FullRefResolver;
import net.exathunk.jsubschema.base.SchemaRef;
import net.exathunk.jsubschema.functional.Either;
import net.exathunk.jsubschema.functional.Maybe;
import net.exathunk.jsubschema.functional.Pair;
import net.exathunk.jsubschema.genschema.schema.Schema;
import net.exathunk.jsubschema.genschema.schema.SchemaLike;
import net.exathunk.jsubschema.genschema.schema.declarations.keylist.KeyList;
import net.exathunk.jsubschema.pointers.PointedRef;
import net.exathunk.jsubschema.pointers.Reference;

import java.util.ArrayList;
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
            } else if (tag.getType().equals(Tag.Type.PARTIAL)) {
                throw new IllegalArgumentException("PARTIAL CANNOT BE TYPED");
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
            } else if (tag.getType().equals(Tag.Type.PARTIAL)) {
                throw new IllegalArgumentException("PARTIAL CANNOT BE TYPED");
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
        schema.setId(resolver.resolveName(name));
        makeTreeSchemaInner(schema, resolver, tree, true);
        return schema;
    }

    private static void makeTreeSchemaInner(SchemaLike schema, NameResolver resolver, TagTree tree, boolean isOnlyChild) {
        if (tree.getParent().isJust()) {
            Tag parentJust = tree.getParent().getJust();
            if (parentJust.getType().equals(Tag.Type.PARTIAL)) {
                final String extId = resolver.resolveName(parentJust.getLabel());
                if (isOnlyChild) {
                    schema.set__dollar__ref(extId);
                } else {
                    addExtension(schema, extId);
                }
                assert tree.getChildren().isEmpty();
            } else {
                Pair<SchemaLike, Boolean> pair = makeTagSchema(parentJust);
                final SchemaLike parentSchema = pair.getKey();
                addProperty(schema, parentJust.getLabel(), parentSchema);
                if (Boolean.TRUE.equals(pair.getValue())) {
                    addRequired(schema, parentJust.getLabel());
                }

                final String t = parentSchema.getType();
                if (t.equals("object")) {
                    for (TagTree child : tree.getChildren()) {
                        makeTreeSchemaInner(parentSchema, resolver, child, tree.getChildren().size() == 1);
                    }
                    return;
                } else if (t.equals("array")) {
                    if (!tree.getChildren().isEmpty()) {
                        SchemaLike itemSchema = new Schema();
                        itemSchema.setType("object");
                        for (TagTree child : tree.getChildren()) {
                            makeTreeSchemaInner(itemSchema, resolver, child, tree.getChildren().size() == 1);
                        }
                        parentSchema.setItems(itemSchema);
                    }
                    return;
                } else {
                    assert tree.getChildren().isEmpty();
                }
            }
        } else {
            for (TagTree child : tree.getChildren()) {
                makeTreeSchemaInner(schema, resolver, child, tree.getChildren().size() == 1);
            }
        }
    }

    private static void addRequired(SchemaLike schema, String label) {
        if (!schema.hasRequired()) {
            schema.setRequired(new KeyList());
        }
        if (!schema.getRequired().contains(label)) {
            schema.getRequired().add(label);
        }
    }

    private static void addExtension(SchemaLike schema, String extId) {
        if (!schema.hasExtensions()) {
            schema.setExtensions(new ArrayList<String>());
        }
        if (!schema.getExtensions().contains(extId)) {
            schema.getExtensions().add(extId);
        }
    }

    private static void addProperty(SchemaLike schema, String label, SchemaLike prop) {
        if (!schema.hasProperties()) schema.setProperties(new TreeMap<String, SchemaLike>());
        assert !schema.getProperties().containsKey(label);
        schema.getProperties().put(label, prop);
    }

    // Returns list of errors.  Empty list == ok.
    public static List<String> satisfyErrors(SchemaLike schema, TagTree tagTree, FullRefResolver refResolver) {
        List<String> errors = new ArrayList<String>();
        satisfyErrorsInner(schema, tagTree, errors, refResolver);
        return errors;
    }

    private static void satisfyErrorsInner(SchemaLike schema, TagTree tagTree, List<String> errors, FullRefResolver refResolver) {
        if (schema == null || (!schema.hasType() && !schema.has__dollar__ref())) {
            errors.add("Invalid schema: null or null type and ref"+schema);
            return;
        } else if (schema.has__dollar__ref()) {
            Either<Reference, String> eitherRef = Reference.fromReferenceString(schema.get__dollar__ref());
            if (eitherRef.isSecond()) {
                errors.add("Invalid schema: "+eitherRef.getSecond());
                return;
            }
            Either<SchemaRef, String> eitherSchema = refResolver.fullyResolveRef(new PointedRef(eitherRef.getFirst(), eitherRef.getFirst().getPointer().reversed()));
            if (eitherSchema.isSecond()) {
                errors.add("Invalid reference: "+eitherSchema.getSecond());
                return;
            }
            satisfyErrorsInner(eitherSchema.getFirst().getSchema(), tagTree, errors, refResolver);
        } else {
            final String schemaType = schema.getType();
            if (tagTree.getParent().isJust()) {
                final Tag parent = tagTree.getParent().getJust();
                final String label = parent.getLabel();
                final Tag.Type parentType = parent.getType();
                if (parentType.equals(Tag.Type.NORMAL) || parentType.equals(Tag.Type.ESCAPE)) {
                    // These nodes can be any scalar:
                    if (schemaType.equals("string") || schemaType.equals("boolean") ||
                            schemaType.equals("integer") || schemaType.equals("number")) {
                        // OK
                    } else {
                        errors.add(label+": expected schema with scalar type, found: "+schemaType);
                        return;
                    }
                } else if (parentType.equals(Tag.Type.SECTION_START) || parentType.equals(Tag.Type.INVERTED_START)) {
                    boolean hasChildren = tagTree.getChildren().size() > 0;
                    if (schemaType.equals("object")) {
                        checkObject(schema, tagTree, errors, refResolver, label);
                    } else if (schemaType.equals("array")) {
                        if (hasChildren) {
                            if (!schema.hasItems()) {
                                errors.add(label+": missing items");
                                return;
                            } else {
                                satisfyErrorsInner(schema.getItems(), tagTree, errors, refResolver);
                            }
                        }
                    } else if (!hasChildren) {
                        // OK - functions as an "is defined?" operator
                    } else {
                        errors.add(label+": expected container type, found: "+schemaType);
                        return;
                    }
                }
            } else {
                // Root of tree - must be object.
                if (!schemaType.equals("object")) {
                    errors.add("ROOT: expected object type, found: "+schemaType);
                    return;
                }
                checkObject(schema, tagTree, errors, refResolver, "ROOT");
            }
        }
    }

    private static void checkObject(SchemaLike schema, TagTree tagTree, List<String> errors, FullRefResolver refResolver, String label) {
        if (!tagTree.getChildren().isEmpty()) {
            if (!schema.hasProperties()) {
                errors.add(label+": missing properties");
                return;
            } else {
                for (TagTree childTree : tagTree.getChildren()) {
                    final Maybe<Tag> child = childTree.getParent();
                    if (child.isNothing()) {
                        errors.add(label+": null child");
                    } else {
                        final String childLabel = child.getJust().getLabel();
                        if (!schema.getProperties().containsKey(childLabel)) {
                            errors.add(label+": missing key: "+childLabel);
                        } else {
                            satisfyErrorsInner(schema.getProperties().get(childLabel), childTree, errors, refResolver);
                        }
                    }
                }
            }
        }
    }
}
