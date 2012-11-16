package net.exathunk.jsubschema.gen;

import net.exathunk.jsubschema.base.Util;
import net.exathunk.jsubschema.genschema.Schema;

import java.util.Map;

/**
 * charolastra 11/14/12 10:55 PM
 */
public class SchemaRepper {

    public static ClassRep makeClass(Schema schema, String basePackageName) {
        final ClassRep c = new ClassRep();
        c.name = parseClassName(schema.id);
        c.packageName = basePackageName;
        for (Map.Entry<String, Schema> entry : schema.properties.entrySet()) {
            final FieldRep field = makeField(entry.getKey(), entry.getValue(), c.name);
            c.fields.add(field);
        }
        c.imports.add("org.codehaus.jackson.annotate.JsonProperty");
        c.imports.add("java.util.List");
        c.imports.add("java.util.Map");
        c.implemented.add("Cloneable");
        new GenUtil.ToStringClassMangler().mangleClass(c);
        new GenUtil.EqualsClassMangler().mangleClass(c);
        new GenUtil.HashCodeClassMangler().mangleClass(c);
        return c;
    }

    public static ClassRep makeFactory(Schema schema, String basePackageName) {
        final ClassRep c = new ClassRep();
        String baseName = parseClassName(schema.id);
        c.name = baseName+"Factory";
        c.packageName = basePackageName;
        c.imports.add("net.exathunk.jsubschema.gendeps.DomainFactory");
        c.methods.add(makeDomainClassMethod(baseName));
        c.methods.add(makeMakeDomainMethod(baseName));
        c.implemented.add("DomainFactory<"+baseName+">");
        return c;
    }

    // Like: public Schema makeDomain() { return new Schema(); }
    private static MethodRep makeMakeDomainMethod(final String className) {
        final MethodRep method = new MethodRep();
        method.visibility = Visibility.PUBLIC;
        method.name = "makeDomain";
        method.body = new Stringable() {
            @Override
            public void makeString(Stringer s) {
                s.append("return new "+className+"();");
                s.end();
            }
        };
        method.returns = className;
        method.annotations.add(new AnnotationRep("@Override"));
        return method;
    }

    // Like: public Class<Schema> getDomainClass() { return Schema.class; }
    private static MethodRep makeDomainClassMethod(final String className) {
        final MethodRep method = new MethodRep();
        method.visibility = Visibility.PUBLIC;
        method.name = "getDomainClass";
        method.body = new Stringable() {
            @Override
            public void makeString(Stringer s) {
                s.append("return "+className+".class;");
                s.end();
            }
        };
        method.returns = "Class<"+className+">";
        method.annotations.add(new AnnotationRep("@Override"));
        return method;
    }

    private static String parseClassName(String url) {
        return Util.upperFirst(Util.last(Util.split(url, "/")));
    }

    private static String typeOf(Schema schema, String rootClassName) {
        if (schema.type.equals("object")) {
            if (schema.__dollar__ref != null) {
                if (schema.__dollar__ref.equals("#")) return rootClassName;
                else return parseClassName(schema.__dollar__ref);
            } else {
                return "Map<String, "+typeOf(schema.items, rootClassName)+">";
            }
        } else if (schema.type.equals("array")) {
            return "List<"+typeOf(schema.items, rootClassName)+">";
        } else if (schema.type.equals("string")) {
            return "String";
        } else if (schema.type.equals("boolean")) {
            return "Boolean";
        } else if (schema.type.equals("integer")) {
            return "Long";
        } else if (schema.type.equals("number")) {
            return "Double";
        } else {
            throw new IllegalArgumentException("Cannot type: "+schema);
        }
    }

    private static FieldRep makeField(String key, Schema schema, String rootClassName) {
        final FieldRep f = new FieldRep();
        f.name = Util.convert(key);
        f.annotations.add(new AnnotationRep("@JsonProperty(\""+key+"\")"));
        f.className = typeOf(schema, rootClassName);
        return f;
    }

}
