package net.exathunk.jsubschema.base;

import org.codehaus.jackson.map.MapperConfig;
import org.codehaus.jackson.map.PropertyNamingStrategy;
import org.codehaus.jackson.map.introspect.AnnotatedField;
import org.codehaus.jackson.map.introspect.AnnotatedMethod;

import java.util.Map;
import java.util.TreeMap;

/**
 * charolastra 11/14/12 3:54 PM
 */
public class NamingStrategy extends PropertyNamingStrategy {

    /*public static String capitalize(String s) {
        final String[] parts = s.split("\\W+");
        final StringBuilder sb = new StringBuilder();
        for (String part : parts) {
            sb.append(part.substring(0, 1).toUpperCase() + part.substring(1));
        }
        return sb.toString();
    }

    public static String camelize(String key) {
        String s = capitalize(key);
        return s.substring(0, 1).toLowerCase()+s.substring(1);
    }*/

    private static final Map<String, String> SUBS;

    static {
        SUBS = new TreeMap<String, String>();
        SUBS.put("$", "__dollar__");
    }

    public String convert(String name) {
        for (Map.Entry<String, String> e : SUBS.entrySet()) {
            name = name.replaceAll(e.getKey(), e.getValue());
        }
        return name;
    }

    public String unconvert(String name) {
        for (Map.Entry<String, String> e : SUBS.entrySet()) {
            name = name.replaceAll(e.getValue(), e.getKey());
        }
        return name;
    }

    @Override
    public String nameForField(MapperConfig config, AnnotatedField field, String defaultName) {
        return convert(defaultName);
    }

    @Override
    public String nameForGetterMethod(MapperConfig config, AnnotatedMethod method, String defaultName) {
        return convert(defaultName);
    }

    @Override
    public String nameForSetterMethod(MapperConfig config, AnnotatedMethod method, String defaultName) {
        return convert(defaultName);
    }
}
