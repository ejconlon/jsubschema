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
}
