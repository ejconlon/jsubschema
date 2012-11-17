package net.exathunk.jsubschema.gen;

import java.util.ArrayList;
import java.util.List;

/**
 * charolastra 11/14/12 9:52 PM
 */
public class ClassRep {

    public static enum TYPE { CLASS, INTERFACE };

    public TYPE type;
    public String packageName;
    public String name;
    public List<String> extended = new ArrayList<String>();
    public List<String> implemented = new ArrayList<String>();
    public List<String> imports = new ArrayList<String>();
    public List<FieldRep> fields = new ArrayList<FieldRep>();
    public List<MethodRep> methods = new ArrayList<MethodRep>();
    public List<AnnotationRep> classAnnotations = new ArrayList<AnnotationRep>();
    public List<AnnotationRep> interfaceAnnotations = new ArrayList<AnnotationRep>();
}
