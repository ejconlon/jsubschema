package net.exathunk.jsubschema.gen;

import net.exathunk.jsubschema.Util;
import net.exathunk.jsubschema.base.TypeException;
import net.exathunk.jsubschema.genschema.Schema;
import net.exathunk.jsubschema.genschema.SchemaFactory;
import org.codehaus.jackson.JsonNode;

import java.io.*;
import java.util.Map;
import java.util.TreeMap;

/**
 * java RunGen src/main/resources/schemas target/generated-sources/gen-javabean/net/exathunk/jsubschema/genschema net.exathunk.jsubschema.genschema
 * charolastra 11/4/12 11:27 AM
 *
 * @goal generate
 * @phase generate-sources
 * @requiresDependencyResolution compile
 * @author charolastra
 * @version $Id$
 */
public class RunGen {

    final String schemaDir;
    final String destDir;
    final String basePackage;

    public RunGen(String schemaDir, String destDir, String basePackage) {
        this.schemaDir = schemaDir;
        this.destDir = destDir;
        this.basePackage = basePackage;
    }

    public static void main(String[] args) throws IOException, TypeException {
        RunGen runGen = new RunGen(args[0], args[1], args[2]);
        runGen.innerExecute();
    }

    public void innerExecute() throws IOException, TypeException {
        final File schemasDir = new File(schemaDir);
        assert schemasDir.isDirectory() && schemasDir.canRead();

        final File dest = new File(destDir);
        assert !dest.exists();
        dest.mkdirs();
        assert dest.isDirectory() && dest.canWrite();

        final Map<String, ClassRep> genned = new TreeMap<String, ClassRep>();

        for (File schemaFile : schemasDir.listFiles()) {
            System.out.println("Generating "+schemaFile);
            final BufferedReader reader = new BufferedReader(new FileReader(schemaFile));
            final StringBuilder contents = new StringBuilder();
            String next = reader.readLine();
            while (next != null) {
                contents.append(next).append("\n");
                next = reader.readLine();
            }
            final JsonNode node = Util.parse(contents.toString());
            final Schema schema = Util.quickBind(node, new SchemaFactory());
            putGenned(genned, SchemaRepper.makeClass(schema, basePackage));
            putGenned(genned, SchemaRepper.makeInterface(schema, basePackage));
            putGenned(genned, SchemaRepper.makeFactory(schema, basePackage));
        }

        for (Map.Entry<String, ClassRep> entry : genned.entrySet()) {
            final String className = entry.getKey();
            final ClassRep classRep = entry.getValue();
            final String contents;
            if (ClassRep.TYPE.INTERFACE.equals(classRep.type)) {
                contents = Assembler.writeInterface(classRep);
            } else {
                contents = Assembler.writeClass(classRep);
            }

            final File f = new File(dest, className+".java");
            FileWriter writer = new FileWriter(f);
            BufferedWriter bufferedWriter = new BufferedWriter(writer);
            bufferedWriter.write(contents, 0, contents.length());
            bufferedWriter.close();
        }

    }

    private static void putGenned(Map<String, ClassRep> genned, ClassRep classRep) {
        genned.put(classRep.name, classRep);
    }

}
