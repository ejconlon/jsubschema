package net.exathunk.jsubschema.gen;

import net.exathunk.jsubschema.Util;
import net.exathunk.jsubschema.genschema.schema.SchemaLike;
import net.exathunk.jsubschema.pointers.Part;
import net.exathunk.jsubschema.pointers.Reference;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

/**
 * charolastra 11/14/12 10:55 PM
 */
public class SchemaRepper {

    public static ClassRep makeClass(Reference rootReference, Reference reference, SchemaLike schema, String rootPackage, String classPackage) {
        final ClassRep c = new ClassRep();
        c.type = ClassRep.TYPE.CLASS;
        c.name = parseClassName(reference);
        c.packageName = classPackage;
        c.imports.add("java.io.Serializable");
        c.implemented.add("Cloneable");
        c.implemented.add("Serializable");
        c.implemented.add(c.name+"Like");
        if (schema.getType().equals("object")) {
            if (schema.hasProperties()) {
                for (Map.Entry<String, SchemaLike> entry : schema.getProperties().entrySet()) {
                    final FieldRep field = makeField(entry.getKey(), entry.getValue(), c.name, rootPackage);
                    c.imports.addAll(field.imports);
                    c.fields.add(field);
                    c.methods.add(new GenUtil.HasAccessorGen().genAccessor(field));
                    c.methods.add(new GenUtil.GetAccessorGen().genAccessor(field));
                    c.methods.add(new GenUtil.SetAccessorGen().genAccessor(field));
                }

                c.methods.add(new GenUtil.ToStringMethodGen().genMethod(c));
                c.methods.add(new GenUtil.EqualsMethodGen().genMethod(c));
                c.methods.add(new GenUtil.HashCodeMethodGen().genMethod(c));

                c.methods.add(new GenUtil.DiffMethodGen().genMethod(c));
            } else if (schema.hasItems()) {
                c.imports.add("java.util.Map");
                c.imports.add("java.util.TreeMap");
                final FieldRep virtualField = makeField("VIRTUAL", schema.getItems(), c.name, rootPackage);
                c.imports.addAll(virtualField.imports);
                c.extended.add("TreeMap<String, "+virtualField.className+">");
                c.virtual = virtualField;
            } else {
                throw new IllegalArgumentException("Schema without properties or items!");
            }
        } else if (schema.getType().equals("array")) {
            c.imports.add("java.util.List");
            c.imports.add("java.util.ArrayList");
            final FieldRep virtualField = makeField("VIRTUAL", schema.getItems(), c.name, rootPackage);
            c.imports.addAll(virtualField.imports);
            c.extended.add("ArrayList<"+virtualField.className+">");
            c.virtual = virtualField;
        } else {
            throw new IllegalArgumentException("Invalid root type: "+schema.getType());
        }
        if (schema.hasExtensions()) {
            throw new IllegalArgumentException("TODO extensions not yet supported.");
        }
        return c;
    }


    public static ClassRep makeInterface(Reference rootReference, Reference reference, SchemaLike schema, String rootPackage, String classPackage) {
        ClassRep classRep = makeClass(rootReference, reference, schema, rootPackage, classPackage);
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
        if (schema.getType().equals("object")) {
            if (schema.hasProperties()) {
                // pass
            } else if (schema.hasItems()) {
                interfaceRep.imports.add("java.util.Map");
                final FieldRep virtualField = makeField("VIRTUAL", schema.getItems(), classRep.name, classPackage);
                interfaceRep.imports.addAll(virtualField.imports);
                interfaceRep.extended.add("Map<String, "+virtualField.className+">");
            } else {
                throw new IllegalArgumentException("Schema without properties or items!");
            }
        } else if (schema.getType().equals("array")) {
            interfaceRep.imports.add("java.util.List");
            final FieldRep virtualField = makeField("VIRTUAL", schema.getItems(), classRep.name, classPackage);
            interfaceRep.imports.addAll(virtualField.imports);
            interfaceRep.extended.add("List<"+virtualField.className+">");
        } else {
            throw new IllegalArgumentException("Invalid root type: "+schema.getType());
        }
        return interfaceRep;
    }

    public static ClassRep makeFactory(Reference rootReference, Reference reference, SchemaLike schema, String rootPackage, String classPackage) {
        final ClassRep c = new ClassRep();
        c.type = ClassRep.TYPE.CLASS;
        String baseName = parseClassName(reference);
        c.name = baseName+"Factory";
        c.packageName = classPackage;
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

    public static String parseClassName(Reference reference) {
        if (reference.getPointer().isEmpty()) {
            String[] parts = reference.getUrl().split("/");
            final String last = parts[parts.length-1];
            return Util.capitalize(last);
        } else {
            return Util.capitalize(reference.getPointer().getHead().getKey());
        }
    }

    private static class TContext {
        public String className;
        public Set<String> imports = new TreeSet<String>();
        public String declaredName;
    }

    private static TContext typeOf(SchemaLike schema, String rootClassName, String classPackage) {
        TContext tContext = new TContext();
        if (!schema.hasType() && schema.has__dollar__ref()) {
            final String dr = schema.get__dollar__ref();
            if (dr.startsWith("#")) {
                if (dr.equals("#")) {
                    tContext.className = rootClassName+"Like";
                } else if (dr.startsWith("#/declarations/")) {
                    final String rawClassName = parseClassName(Reference.fromReferenceString(dr).getFirst());
                    tContext.className = rawClassName+"Like";
                    tContext.declaredName = dr.substring("#/declarations/".length(), dr.length());
                    tContext.imports.add(classPackage+".declarations."+rawClassName.toLowerCase()+"."+tContext.className);
                    tContext.imports.add(classPackage+".declarations."+rawClassName.toLowerCase()+"."+rawClassName);
                } else {
                    throw new IllegalArgumentException("Cannot type (reffed): "+schema);
                }
            } else {
                final String rawClassName = parseClassName(Reference.fromReferenceString(schema.get__dollar__ref()).getFirst());
                tContext.className  = rawClassName+"Like";
                // HACK TODO actually pass real global package
                String[] parts = classPackage.split("\\.");
                String pack = "";
                for (int i = 0; i < parts.length - 1; ++i) { pack += parts[i] + "."; }
                pack += rawClassName.toLowerCase()+".";
                tContext.imports.add(pack+tContext.className);
                tContext.imports.add(pack+rawClassName);
            }
        } else if (schema.getType().equals("object")) {
            final TContext subContext = typeOf(schema.getItems(), rootClassName, classPackage);
            tContext.className  = "Map<String, "+subContext.className+">";
            tContext.declaredName = subContext.declaredName;
            tContext.imports.addAll(subContext.imports);
            tContext.imports.add("java.util.Map");
        } else if (schema.getType().equals("array")) {
            final TContext subContext = typeOf(schema.getItems(), rootClassName, classPackage);
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

    private static FieldRep makeField(String key, SchemaLike schema, String rootClassName, String rootPackage) {
        final FieldRep f = new FieldRep();
        f.visibility = Visibility.PRIVATE;
        f.name = Util.convert(key);
        TContext tContext = typeOf(schema, rootClassName, rootPackage);
        f.className = tContext.className;
        f.declaredName = tContext.declaredName;
        f.imports.addAll(tContext.imports);
        return f;
    }

    public static void makeAll(Reference rootReference, Reference reference, SchemaLike rootSchema, SchemaLike schema, String rootClassPackage, String classPackage, Map<String, ClassRep> genned) {
        final ClassRep classRep = SchemaRepper.makeClass(rootReference, reference, schema, rootClassPackage, classPackage);
        putGenned(classRep, genned);
        putGenned(SchemaRepper.makeInterface(rootReference, reference, schema, rootClassPackage, classPackage), genned);
        putGenned(SchemaRepper.makeFactory(rootReference, reference, schema, rootClassPackage, classPackage), genned);

        for (final FieldRep field : classRep.fields) {
            genIt(rootReference, rootSchema, field, rootClassPackage, genned);
        }
        if (classRep.virtual != null) {
            genIt(rootReference, rootSchema, classRep.virtual, rootClassPackage, genned);
        }
    }

    public static void genIt(Reference rootReference, SchemaLike rootSchema, FieldRep field, String rootClassPackage, Map<String, ClassRep> genned) {
        if (field.declaredName != null) {
            final Part part = Part.fromPointerString(field.declaredName);
            final Reference ref = rootReference.cons(Part.asKey("declarations")).cons(part);
            final SchemaLike subSchema = rootSchema.getDeclarations().get(part.getKey());
            final String name = SchemaRepper.parseClassName(ref);
            final String packageName = rootClassPackage + ".declarations." + name.toLowerCase();
            makeAll(rootReference, ref, rootSchema, subSchema, rootClassPackage, packageName, genned);
        }
    }

    private static void putGenned(ClassRep classRep, Map<String, ClassRep> genned) {
        genned.put(classRep.packageName + "." + classRep.name, classRep);
    }

    /*private static boolean hasGenned(ClassRep classRep, Map<String, ClassRep> genned) {
        return genned.containsKey(classRep.packageName + "." + classRep.name);
    }*/
}
