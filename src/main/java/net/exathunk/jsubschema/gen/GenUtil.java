package net.exathunk.jsubschema.gen;

import java.util.List;
import java.util.Map;

/**
 * charolastra 11/16/12 2:41 PM
 */
public class GenUtil {

    public static <X> List<X> mergeLists(List<X> a, List<X> b) {
        if (a == null) return b;
        else {
            a.addAll(b);
            return a;
        }
    }

    public static <X> Map<String, X> mergeMaps(Map<String, X> a, Map<String, X> b) {
        if (a == null) return b;
        else {
            a.entrySet().addAll(b.entrySet());
            return a;
        }
    }

    public static class ToStringContext {
        private final StringBuilder sb = new StringBuilder();
        private boolean finished = false;

        public ToStringContext(String name) {
            sb.append(name).append("{ ");
        }

        public ToStringContext add(String name, Object value) {
            assert !finished;
            if (value != null) {
                sb.append(name).append("='").append(value.toString()).append("', ");
            }
            return this;
        }

        public String finish() {
            assert !finished;
            finished = true;
            sb.append("}");
            return sb.toString();
        }
    }

    public static interface ClassMangler {
        void mangleClass(ClassRep classRep);
    }

    public static class ToStringClassMangler implements ClassMangler {
        @Override
        public void mangleClass(ClassRep classRep) {
            final MethodRep method = new MethodRep();
            method.annotations.add(new AnnotationRep("@Override"));
            method.name = "toString";
            method.returns = "String";

            Stringer stringer = new Stringer();
            stringer.end();
            Stringer sb2 = stringer.indent().indent();

            sb2.append("StringBuilder sb = new StringBuilder(\"").append(classRep.name).append("{ \");\n");
            for (FieldRep field : classRep.fields) {
                sb2.append("if (").append(field.name).append(" != null) sb.append(\"");
                sb2.cont().append(field.name).append("='\")").append(".append(").append(field.name).append(").append(\"', \");\n");
            }
            sb2.append("return sb.append(\"}\").toString(); ");

            method.body = stringer.toString();

            classRep.methods.add(method);
        }
    }

    public static class EqualsClassMangler implements ClassMangler {
        @Override
        public void mangleClass(ClassRep classRep) {
            final MethodRep method = new MethodRep();
            method.annotations.add(new AnnotationRep("@Override"));
            method.name = "equals";
            method.returns = "boolean";
            FieldRep otherField = new FieldRep();
            otherField.name = "o";
            otherField.className = "Object";
            method.parameters.add(otherField);

            Stringer sb = new Stringer();
            sb.append("if (this == o) return true;\n");
            sb.append("if (o instanceof ").append(classRep.name).append(") {\n");
            sb.indent().append(classRep.name).append(" other = (").append(classRep.name).append(") o;\n");
            for (FieldRep field : classRep.fields) {
                final String capKey = field.name.substring(0, 1).toUpperCase()+field.name.substring(1);
                final String getCall = "get"+capKey+"Ref()";
                sb.append("if (" + field.name + " == null) { if (other." + field.name + " != null) return false; }\n");
                sb.append("else if (!" + field.name + ".equals(other." + field.name + ")) { return false; }\n");
            }
            sb.indent().append("return true;\n");
            sb.append("} else {\n");
            sb.indent().append("return false;\n");
            sb.append("}\n");

            method.body = sb.toString();

            classRep.methods.add(method);
        }
    }

    public static class HashCodeClassMangler implements ClassMangler {
        @Override
        public void mangleClass(ClassRep classRep) {
            final MethodRep method = new MethodRep();
            method.annotations.add(new AnnotationRep("@Override"));
            method.name = "hashCode";
            method.returns = "int";

            Stringer sb = new Stringer();
            sb.append("int result = 0;\n");
            for (FieldRep field : classRep.fields) {
                sb.append("result = 31 * result + ("+field.name+" == null ? 0 : ").append(field.name).append(".hashCode());\n");
            }
            sb.append("return result;\n");

            method.body = sb.toString();

            classRep.methods.add(method);
        }
    }
}
