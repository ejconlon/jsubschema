package net.exathunk.jsubschema.gen;

import java.util.ArrayList;
import java.util.List;

/**
 * charolastra 11/14/12 9:57 PM
 */
public class MethodRep {
    public String name;
    public Visibility visibility = Visibility.PUBLIC;
    public String returns;
    public List<FieldRep> parameters = new ArrayList<FieldRep>();
    public boolean overrides = false;
    public List<AnnotationRep> annotations = new ArrayList<AnnotationRep>();
    public String body;

    private static void writeList(List<FieldRep> list, StringBuilder s) {
        final int len = list.size();
        for (int i = 0; i < len; ++i) {
            s.append(list.get(0));
            if (i < len - 1) {
                s.append(", ");
            }
        }
    }

    public String toClassString() {
        StringBuilder sb = new StringBuilder();
        for (AnnotationRep annotation : annotations) {
            sb.append(annotation.toString()).append("\n");
        }
        if (!Visibility.PACKAGE.equals(visibility)) {
            sb.append(visibility.toString().toLowerCase()).append(" ");
        }
        sb.append(returns.toString()).append(" ");
        sb.append(name).append("(");
        writeList(parameters, sb);
        sb.append(") { ");
        sb.append(body);
        sb.append(" }");
        return sb.toString();
    }

    public String toInterfaceString() {
        StringBuilder sb = new StringBuilder();
        sb.append(returns.toString()).append(" ");
        sb.append(name).append("(");
        writeList(parameters, sb);
        sb.append(");");
        return sb.toString();
    }
}
