package net.exathunk.jsubschema.gen;

import java.util.List;

/**
 * charolastra 11/14/12 10:19 PM
 */
public class Assembler {

    public static String writeClass(ClassRep c) {
        Stringer s = new Stringer();
        writePackageName(c, s);
        writeImports(c, s);
        writeOpenClass(c, s);
        writeFields(c, s.indent());
        writeClassMethods(c, s.indent());
        writeClose(s);
        return s.toString();
    }

    public static String writeInterface(ClassRep c) {
        Stringer s = new Stringer();
        writePackageName(c, s);
        writeImports(c, s);
        writeOpenInterface(c, s);
        writeInterfaceMethods(c, s.indent());
        writeClose(s);
        return s.toString();
    }

    private static void writeImports(ClassRep c, Stringer s) {
        for (String i : c.imports) {
            s.append("import "+i+";");
            s.end();
        }
        if (!c.imports.isEmpty()) s.end();
    }

    private static void writePackageName(ClassRep classRep, Stringer s) {
        s.append("package "+classRep.packageName+";");
        s.end();
        s.end();
    }

    private static void writeOpenClass(ClassRep classRep, Stringer s) {
        s.append("public class "+classRep.name);
        if (!classRep.extended.isEmpty()) {
            s.cont().append(" extends ");
            writeList(classRep.extended, s);
        }
        if (!classRep.implemented.isEmpty()) {
            s.cont().append(" implements ");
            writeList(classRep.implemented, s);
        }
        s.cont().append(" {");
        s.end();
        s.end();
    }

    private static void writeOpenInterface(ClassRep classRep, Stringer s) {
        s.append("public interface "+classRep.name);
        if (!classRep.extended.isEmpty()) {
            s.cont().append(" extends ");
            writeList(classRep.extended, s);
        }
        s.cont().append(" {");
        s.end();
        s.end();
    }

    private static void writeList(List<String> list, Stringer s) {
        final int len = list.size();
        for (int i = 0; i < len; ++i) {
            s.cont().append(list.get(i));
            if (i < len - 1) {
                s.cont().append(", ");
            }
        }
    }

    private static void writeClose(Stringer s) {
        s.append("}");
        s.end();
    }

    private static void writeFields(ClassRep classRep, Stringer s) {
        for (FieldRep fieldRep : classRep.fields) {
            fieldRep.makeDeclarationString(s);
            s.end();
        }
    }

    private static void writeClassMethods(ClassRep classRep, Stringer s) {
        for (MethodRep methodRep : classRep.methods) {
            methodRep.makeClassString(s);
            s.end();
        }
    }

    private static void writeInterfaceMethods(ClassRep classRep, Stringer s) {
        for (MethodRep methodRep : classRep.methods) {
            methodRep.makeInterfaceString(s);
            s.end();
        }
    }

}
