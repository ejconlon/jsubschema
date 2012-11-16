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
    public List<AnnotationRep> annotations = new ArrayList<AnnotationRep>();
    public Stringable body;

    private static void writeList(List<FieldRep> list, Stringer sb) {
        final int len = list.size();
        for (int i = 0; i < len; ++i) {
            list.get(i).makeParameterString(sb);
            if (i < len - 1) {
                sb.cont().append(", ");
            }
        }
    }

    public void makeClassString(Stringer sb) {
        for (AnnotationRep annotation : annotations) {
            sb.append(annotation.toString());
            sb.end();
        }
        if (!Visibility.PACKAGE.equals(visibility)) {
            sb.append(visibility.toString().toLowerCase()).append(" ");
        } else {
            sb.append("");
        }
        sb.cont().append(returns.toString()).append(" ").append(name).append("(");
        writeList(parameters, sb);
        sb.cont().append(") {");
        body.makeString(sb.indent());
        sb.cont().append("}");
    }

    public void makeInterfaceString(Stringer sb) {
        sb.append(returns.toString()).append(" ").append(name).append("(");
        writeList(parameters, sb);
        sb.cont().append(");");
    }
}
