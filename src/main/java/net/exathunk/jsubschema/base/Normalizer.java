package net.exathunk.jsubschema.base;

import net.exathunk.jsubschema.genschema.schema.Schema;
import net.exathunk.jsubschema.genschema.schema.SchemaLike;
import net.exathunk.jsubschema.pointers.Part;
import net.exathunk.jsubschema.pointers.Pointer;
import net.exathunk.jsubschema.pointers.Reference;

import java.util.Map;
import java.util.TreeMap;

/**
 * charolastra 11/19/12 10:25 PM
 */
public class Normalizer {

    public static SchemaLike normalize(final SchemaLike schema) {
        return normalizeInner(schema, schema, new Pointer(), 0);
    }

    private static SchemaLike normalizeInner(final SchemaLike root, final SchemaLike schema, final Pointer pointer, final int depth) {
        if (schema.hasItems()) {
            final SchemaLike subSchema = schema.getItems();
            final Pointer subPointer = pointer.cons(Part.asKey("items"));
            if (subSchema.hasItems() || subSchema.hasProperties()) {
                SchemaLike newSubSchema = normalizeInner(root, subSchema, subPointer, depth+1);
                if (depth > 0) newSubSchema = declare(root, newSubSchema, subPointer);
                subSchema.setItems(newSubSchema);
            }
        } else if (schema.hasProperties()) {
            Map<String, SchemaLike> newProperties = new TreeMap<String, SchemaLike>();
            for (Map.Entry<String, SchemaLike> entry : schema.getProperties().entrySet()) {
                final SchemaLike subSchema = entry.getValue();
                final Pointer subPointer = pointer.cons(Part.asKey("properties")).cons(Part.asKey(entry.getKey()));
                if (subSchema.hasItems() || subSchema.hasProperties()) {
                    SchemaLike newSubSchema = normalizeInner(root, subSchema, subPointer, depth+1);
                    if (depth > 0) newSubSchema = declare(root, newSubSchema, subPointer);
                    newProperties.put(entry.getKey(), newSubSchema);
                } else {
                    newProperties.put(entry.getKey(), entry.getValue());
                }
            }
            schema.setProperties(newProperties);
        }
        return schema;
    }

    private static SchemaLike declare(SchemaLike root, SchemaLike schema, Pointer pointer) {
        final Part keyPart = Part.fromPointerString(pointer.toPointerString());
        final Reference ref = new Reference("", new Pointer().cons(Part.asKey("declarations")).cons(keyPart));
        if (!root.hasDeclarations()) {
            root.setDeclarations(new TreeMap<String, SchemaLike>());
        }
        root.getDeclarations().put(keyPart.getKey(), schema);
        SchemaLike newSchema = new Schema();
        newSchema.set__dollar__ref(ref.toReferenceString());
        return newSchema;
    }
}
