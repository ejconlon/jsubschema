package net.exathunk.jsubschema.gen;

import java.util.ArrayList;
import java.util.List;

/**
 * charolastra 11/14/12 9:52 PM
 */
public class ClassRep {
    public String packageName;
    public String className;
    public List<String> extended = new ArrayList<String>();
    public List<String> implemented = new ArrayList<String>();
    public List<String> imports = new ArrayList<String>();
    public List<FieldRep> fields = new ArrayList<FieldRep>();
    public List<MethodRep> methods = new ArrayList<MethodRep>();
}