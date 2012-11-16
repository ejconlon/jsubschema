package net.exathunk.jsubschema.gen;

import java.util.ArrayList;
import java.util.List;

/**
 * charolastra 11/14/12 9:57 PM
 */
public class FieldRep {

    public String name;
    public String className;
    public Visibility visibility = Visibility.PUBLIC;
    public List<AnnotationRep> annotations = new ArrayList<AnnotationRep>();


    public void makeDeclarationString(Stringer s) {
        for (AnnotationRep annotationRep : annotations) {
            s.append(annotationRep.toString());
            s.end();
        }
        if (!Visibility.PACKAGE.equals(visibility)) {
            s.append(visibility.toString().toLowerCase()).append(" ");
        } else {
            s.append("");
        }
        s.cont().append(className).append(" ").append(name).append(";");
        s.end();
    }

    public void makeParameterString(Stringer s) {
        s.cont().append(className).append(" ").append(name);
    }
}
