package net.exathunk.jsubschema.gen;

import net.exathunk.jsubschema.Util;
import net.exathunk.jsubschema.base.Normalizer;
import net.exathunk.jsubschema.base.TypeException;
import net.exathunk.jsubschema.genschema.schema.SchemaFactory;
import net.exathunk.jsubschema.genschema.schema.SchemaLike;
import net.exathunk.jsubschema.pointers.Reference;
import org.codehaus.jackson.JsonNode;

import java.io.*;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

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

    private final String schemaDir;
    private final String destDir;
    private final String basePackage;
    private final Set<String> addlImports;

    public RunGen(String schemaDir, String destDir, String basePackage, Set<String> addlImports) {
        this.schemaDir = schemaDir;
        this.destDir = destDir;
        this.basePackage = basePackage;
        this.addlImports = addlImports;
    }

    public static void main(String[] args) throws IOException, TypeException {
        Set<String> addlImports = new TreeSet<String>();
        for (int i = 3; i < args.length; ++i) {
            addlImports.add(args[i]);
        }
        RunGen runGen = new RunGen(args[0], args[1], args[2], addlImports);
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
        //for (File schemaFile : new File[] {new File(schemasDir, "schema")}) {
            System.out.println("Generating "+schemaFile);
            final BufferedReader reader = new BufferedReader(new FileReader(schemaFile));
            final StringBuilder contents = new StringBuilder();
            String next = reader.readLine();
            while (next != null) {
                contents.append(next).append("\n");
                next = reader.readLine();
            }
            final JsonNode node = Util.parse(contents.toString());
            final SchemaLike schema = Util.quickBind(node, new SchemaFactory());
            final SchemaLike normalized = Normalizer.normalize(schema);

            final Reference reference = Reference.fromId(normalized.getId());
            final String name = SchemaRepper.parseClassName(reference);
            final String packageName = basePackage + "." + name.toLowerCase();

            SchemaRepper.makeAll(reference, reference, normalized, normalized, packageName, packageName, genned, addlImports);
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

            String[] parts = className.split("\\.");
            StringBuilder dsb = new StringBuilder(destDir);
            if (!destDir.endsWith("/") && parts.length > 0) dsb.append("/");
            for (int i = 0; i < parts.length - 1; ++i) {
                dsb.append(parts[i]);
                if (i < parts.length - 1) {
                    dsb.append("/");
                }
            }
            final File d = new File(dsb.toString());
            d.mkdirs();
            final File f = new File(d, parts[parts.length-1]+".java");
            FileWriter writer = new FileWriter(f);
            BufferedWriter bufferedWriter = new BufferedWriter(writer);
            bufferedWriter.write(contents, 0, contents.length());
            bufferedWriter.close();
        }

    }

}
