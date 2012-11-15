package net.exathunk.jsubschema.gen;

import net.exathunk.jsubschema.base.Util;
import org.codehaus.jackson.JsonNode;

import java.io.*;
import java.net.URL;
import java.util.Set;

/**
 * charolastra 10/24/12 7:53 PM
 */
public class Loader {

    public static String loadString(String path) throws IOException {
        InputStream is = Loader.class.getResourceAsStream(path);
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();
        String line = reader.readLine();
        while (line != null) {
            sb.append(line).append('\n');
            line = reader.readLine();
        }
        reader.close();
        return sb.toString();
    }

    public static JsonNode loadNode(String path) throws IOException {
        final String loaded = loadString(path);
        return Util.parse(loaded);
    }

    public static Set<String> listFiles(String path) throws IOException {
        final URL url = Loader.class.getResource(path);
        File file = new File(url.getFile());
        if (!file.exists() || !file.isDirectory()) {
            throw new IOException("Not a directory: "+file);
        }
        return Util.asSet(file.list());
    }

    public static String loadSchemaString(String name) throws IOException {
        return loadString("/schemas/"+name);
    }

    public static JsonNode loadSchemaNode(String name) throws IOException {
        return loadNode("/schemas/"+name);
    }

    public static Set<String> listSchemas() throws IOException {
        return listFiles("/schemas");
    }
}