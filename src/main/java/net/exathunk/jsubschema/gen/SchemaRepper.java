package net.exathunk.jsubschema.gen;

import net.exathunk.jsubschema.Util;
import net.exathunk.jsubschema.genschema.schema.SchemaLike;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

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
            final FieldRep field = makeField(entry.getKey(), entry.getValue(), c.name, basePackageName);
            for (String impStr : field.imports) {
                if (!c.imports.contains(impStr)) {
                    c.imports.add(impStr);
                }
            }
            c.fields.add(field);
            c.methods.add(new GenUtil.HasAccessorGen().genAccessor(field));
            c.methods.add(new GenUtil.GetAccessorGen().genAccessor(field));
            c.methods.add(new GenUtil.SetAccessorGen().genAccessor(field));
        }
        c.methods.add(new GenUtil.ToStringMethodGen().genMethod(c));
        c.methods.add(new GenUtil.EqualsMethodGen().genMethod(c));
        c.methods.add(new GenUtil.HashCodeMethodGen().genMethod(c));

        c.methods.add(new GenUtil.DiffMethodGen().genMethod(c));

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
        interfaceRep.imports.addAll(classRep.imports);
        for (FieldRep field : classRep.fields) {
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

    private static class TContext {
        public String className;
        public Set<String> imports = new TreeSet<String>();
        public String declaredName;
    }

    private static TContext typeOf(SchemaLike schema, String rootClassName, String basePackageName) {
        TContext tContext = new TContext();
        if (!schema.hasType() && schema.has__dollar__ref()) {
            final String dr = schema.get__dollar__ref();
            if (dr.startsWith("#")) {
                if (dr.equals("#")) {
                    tContext.className = rootClassName;
                } else if (dr.startsWith("#/declarations/")) {
                    final String rawClassName = parseClassName(dr);
                    tContext.className = rawClassName+"Like";
                    tContext.declaredName = dr.substring("#/declarations/".length(), dr.length());
                    tContext.imports.add(basePackageName+"."+rootClassName.toLowerCase()+".declarations."+tContext.className);
                    tContext.imports.add(basePackageName+"."+rootClassName.toLowerCase()+".declarations."+rawClassName);
                } else {
                    throw new IllegalArgumentException("Cannot type (reffed): "+schema);
                }
            } else {
                final String rawClassName = parseClassName(schema.get__dollar__ref());
                tContext.className  = rawClassName+"Like";
                tContext.imports.add(basePackageName+"."+rawClassName.toLowerCase()+"."+tContext.className);
                tContext.imports.add(basePackageName+"."+rawClassName.toLowerCase()+"."+rawClassName);
            }
        } else if (schema.getType().equals("object")) {
            final TContext subContext = typeOf(schema.getItems(), rootClassName, basePackageName);
            tContext.className  = "Map<String, "+subContext.className+">";
            tContext.declaredName = subContext.declaredName;
            tContext.imports.addAll(subContext.imports);
            tContext.imports.add("java.util.Map");
        } else if (schema.getType().equals("array")) {
            final TContext subContext = typeOf(schema.getItems(), rootClassName, basePackageName);
            tContext.className  = "List<"+subContext.className+">";
            tContext.declaredName = subContext.declaredName;
            tContext.imports.addAll(subContext.imports);
            tContext.imports.add("java.util.List");
        } else if (schema.getType().equals("string")) {
            tContext.className  = "String";
        } else if (schema.getType().equals("boolean")) {
            tContext.className  = "Boolean";
        } else if (schema.getType().equals("integer")) {
            tContext.className  = "Long";
        } else if (schema.getType().equals("number")) {
            tContext.className = "Double";
        } else {
            throw new IllegalArgumentException("Cannot type: "+schema);
        }
        return tContext;
    }

    private static void addToSet(String newPackage, String newClass, List<String> ss, String selfPackage) {
        if (!newPackage.equals(selfPackage)) {
            final String x = newPackage + "." + newClass;
            if (!ss.contains(x)) {
                ss.add(x);
            }
        }
    }

    private static FieldRep makeField(String key, SchemaLike schema, String rootClassName, String packageName) {
        final FieldRep f = new FieldRep();
        f.visibility = Visibility.PRIVATE;
        f.name = Util.convert(key);
        TContext tContext = typeOf(schema, rootClassName+"Like", packageName);
        f.className = tContext.className;
        f.declaredName = tContext.declaredName;
        f.imports.addAll(tContext.imports);
        return f;
    }

    public static void makeAll(SchemaLike schema, String basePackage, Map<String, ClassRep> genned) {
        final ClassRep classRep = SchemaRepper.makeClass(schema, basePackage);
        putGenned(classRep, genned);
        putGenned(SchemaRepper.makeInterface(schema, basePackage), genned);
        putGenned(SchemaRepper.makeFactory(schema, basePackage), genned);

        for (final FieldRep field : classRep.fields) {
            if (field.declaredName != null) {
                makeAll(schema.getDeclarations().get(field.declaredName), classRep.packageName+".declarations", genned);
            }
        }
    }

    private static void putGenned(ClassRep classRep, Map<String, ClassRep> genned) {
        genned.put(classRep.packageName + "." + classRep.name, classRep);
    }
}
