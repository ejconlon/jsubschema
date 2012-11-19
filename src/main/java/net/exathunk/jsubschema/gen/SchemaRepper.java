package net.exathunk.jsubschema.gen;

import net.exathunk.jsubschema.Util;
import net.exathunk.jsubschema.genschema.schema.Schema;
import net.exathunk.jsubschema.genschema.schema.SchemaLike;

import java.util.List;
import java.util.Map;

/**
 * charolastra 11/14/12 10:55 PM
 */
public class SchemaRepper {

    public static ClassRep makeClass(SchemaLike schema, String basePackageName) {
        final ClassRep c = new ClassRep();
        c.type = ClassRep.TYPE.CLASS;
        c.name = parseClassName(schema.getId());
        c.packageName = basePackageName + "." + c.name.toLowerCase();
        c.imports.add("java.io.Serializable");
        c.implemented.add("Cloneable");
        c.implemented.add("Serializable");
        c.implemented.add(c.name+"Like");
        for (Map.Entry<String, SchemaLike> entry : schema.getProperties().entrySet()) {
            final FieldRep field = makeField(entry.getKey(), entry.getValue(), c.name);
            addImports(basePackageName, field, c.imports, c.packageName);
            c.fields.add(field);
            c.methods.add(new GenUtil.HasAccessorGen().genAccessor(field));
            c.methods.add(new GenUtil.GetAccessorGen().genAccessor(field));
            c.methods.add(new GenUtil.SetAccessorGen().genAccessor(field));
        }
        c.methods.add(new GenUtil.ToStringMethodGen().genMethod(c));
        c.methods.add(new GenUtil.EqualsMethodGen().genMethod(c));
        c.methods.add(new GenUtil.HashCodeMethodGen().genMethod(c));

        c.methods.add(new GenUtil.DiffMethodGen().genMethod(c));

        assert c.innerClasses.isEmpty();

        return c;
    }


    public static ClassRep makeInterface(SchemaLike schema, String basePackage) {
        ClassRep classRep = makeClass(schema, basePackage);
        ClassRep interfaceRep = new ClassRep();
        interfaceRep.type = ClassRep.TYPE.INTERFACE;
        interfaceRep.name = classRep.name+"Like";
        interfaceRep.interfaceAnnotations.add(new AnnotationRep("@JsonDeserialize(as = "+classRep.name+".class)"));
        interfaceRep.packageName = classRep.packageName;
        interfaceRep.imports.add("org.codehaus.jackson.annotate.JsonProperty");
        interfaceRep.imports.add("org.codehaus.jackson.map.annotate.JsonDeserialize");
        for (FieldRep field : classRep.fields) {
            addImports(basePackage, field, interfaceRep.imports, interfaceRep.packageName);
            interfaceRep.methods.add(new GenUtil.HasAccessorGen().genAccessor(field));
            interfaceRep.methods.add(new GenUtil.GetAccessorGen().genAccessor(field));
            interfaceRep.methods.add(new GenUtil.SetAccessorGen().genAccessor(field));
        }
        return interfaceRep;
    }

    public static ClassRep makeFactory(SchemaLike schema, String basePackageName) {
        final ClassRep c = new ClassRep();
        c.type = ClassRep.TYPE.CLASS;
        String baseName = parseClassName(schema.getId());
        c.name = baseName+"Factory";
        c.packageName = basePackageName + "." + baseName.toLowerCase();
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
        method.classAnnotations.add(new AnnotationRep("@Override"));
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
        method.classAnnotations.add(new AnnotationRep("@Override"));
        return method;
    }

    private static String parseClassName(String url) {
        return Util.upperFirst(Util.last(Util.split(url, "/")));
    }

    private static String typeOf(SchemaLike schema, String rootClassName) {
        if (!schema.hasType() && schema.has__dollar__ref()) {
            if (schema.get__dollar__ref().equals("#")) return rootClassName;
            else return parseClassName(schema.get__dollar__ref())+"Like";
        } else if (schema.getType().equals("object")) {
            return "Map<String, "+typeOf(schema.getItems(), rootClassName)+">";
        } else if (schema.getType().equals("array")) {
            return "List<"+typeOf(schema.getItems(), rootClassName)+">";
        } else if (schema.getType().equals("string")) {
            return "String";
        } else if (schema.getType().equals("boolean")) {
            return "Boolean";
        } else if (schema.getType().equals("integer")) {
            return "Long";
        } else if (schema.getType().equals("number")) {
            return "Double";
        } else {
            throw new IllegalArgumentException("Cannot type: "+schema);
        }
    }

    private static void addToSet(String newPackage, String newClass, List<String> ss, String selfPackage) {
        if (!newPackage.equals(selfPackage)) {
            final String x = newPackage + "." + newClass;
            if (!ss.contains(x)) {
                ss.add(x);
            }
        }
    }

    private static void addImports(String basePackage, FieldRep field, List<String> imports, String selfPackage) {
        final String s = field.className;
        final String t;
        final boolean isMap = s.startsWith("Map<");
        final boolean isList = s.startsWith("List<");
        if (s.contains("Like")) {
            if (isMap) {
                t = s.substring(s.lastIndexOf(" ")+1, s.indexOf("Like")+4);
            } else if (isList) {
                t = s.substring(s.lastIndexOf("<")+1, s.indexOf("Like")+4);
            } else {
                t = s;
            }
            String newBp = basePackage + "." + t.substring(0, t.length()-4).toLowerCase();
            addToSet(newBp, t, imports, selfPackage);
            addToSet(newBp, t.substring(0, t.length()-4), imports, selfPackage);
        }
        if (isMap) {
            addToSet("java.util", "Map", imports, selfPackage);
        } else if (isList) {
            addToSet("java.util", "List", imports, selfPackage);
        }
    }

    private static FieldRep makeField(String key, SchemaLike schema, String rootClassName) {
        final FieldRep f = new FieldRep();
        f.visibility = Visibility.PRIVATE;
        f.name = Util.convert(key);
        f.className = typeOf(schema, rootClassName+"Like");
        return f;
    }

    public static void makeAll(Schema schema, String basePackage, Map<String, ClassRep> genned) {
        putGenned(SchemaRepper.makeClass(schema, basePackage), genned);
        putGenned(SchemaRepper.makeInterface(schema, basePackage), genned);
        putGenned(SchemaRepper.makeFactory(schema, basePackage), genned);
    }

    private static void putGenned(ClassRep classRep, Map<String, ClassRep> genned) {
        genned.put(classRep.packageName + "." + classRep.name, classRep);
    }
}
