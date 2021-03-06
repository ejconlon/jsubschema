package net.exathunk.jsubschema;

import net.exathunk.jsubschema.base.*;
import net.exathunk.jsubschema.gendeps.DomainFactory;
import net.exathunk.jsubschema.genschema.schema.SchemaLike;
import net.exathunk.jsubschema.validation.VContext;
import net.exathunk.jsubschema.validation.VError;
import net.exathunk.jsubschema.validation.Validator;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import java.io.IOException;
import java.util.*;

/**
 * charolastra 11/13/12 7:22 PM
 */
public class Util {

    public static <X> Set<X> asSet(Iterator<X> xs) {
        final Set<X> s = new HashSet<X>();
        while (xs.hasNext()) {
            final X next = xs.next();
            s.add(next);
        }
        return s;
    }

    public static <X> Set<X> asSet(Iterable<X> xs) {
        return asSet(xs.iterator());
    }

    public static <X> Set<X> asSet(X... xs) {
        final Set<X> s = new HashSet<X>();
        for (X x : xs) {
            s.add(x);
        }
        return s;
    }

    public static <X> List<X> asList(Iterator<X> xs) {
        final List<X> s = new ArrayList<X>();
        while (xs.hasNext()) {
            final X next = xs.next();
            s.add(next);
        }
        return s;
    }

    public static <X> List<X> asList(Iterable<X> xs) {
        return asList(xs.iterator());
    }

    public static <X> List<X> asList(X... xs) {
        final List<X> s = new ArrayList<X>();
        for (X x : xs) {
            s.add(x);
        }
        return s;
    }

    public static <X> boolean setEquals(Set<X> as, Set<X> bs) {
        if (as == null) return bs == null;
        if (bs == null) return false;
        if (as.size() != bs.size()) return false;
        for (X a : as) {
            if (!bs.contains(a)) return false;
        }
        return true;
    }

    public static void assertTrue(String label, Object a, Object b, boolean asserted) {
        if (!asserted) {
            throw new AssertionError("Assertion failed: label => "+label+"; a => "+a+"; b => "+b);
        }
    }

    public static List<String> split(String source, String delim) {
        String[] parts = source.split(delim);
        List<String> list = new ArrayList<String>(parts.length);
        for (String part : parts) {
            if (part != null && !part.isEmpty()) list.add(part);
        }
        return list;
    }

    public static JsonNode parse(String s) throws IOException {
        return (makeObjectMapper()).readTree(s);
    }

    public static <X> X last(List<X> split) {
        return split.get(split.size()-1);
    }

    public static String capitalize(String s) {
        final String[] parts = s.split("\\W+");
        final StringBuilder sb = new StringBuilder();
        for (String part : parts) {
            sb.append(upperFirst(part));
        }
        return sb.toString();
    }

    public static String camelize(String key) {
        String s = capitalize(key);
        return lowerFirst(s);
    }

    public static String lowerFirst(String s) {
        if (s.isEmpty()) return "";
        return s.substring(0, 1).toLowerCase()+s.substring(1);
    }

    public static String upperFirst(String s) {
        if (s.isEmpty()) return "";
        return s.substring(0, 1).toUpperCase()+s.substring(1);
    }


    private static final Map<String, String> SUBS;

    static {
        SUBS = new TreeMap<String, String>();
        SUBS.put("\\$", "__dollar__");
        SUBS.put("-", "__dash__");
    }

    public static String convert(String name) {
        for (Map.Entry<String, String> e : SUBS.entrySet()) {
            name = name.replaceAll(e.getKey(), e.getValue());
        }
        return name;
    }

    public static String unconvert(String name) {
        for (Map.Entry<String, String> e : SUBS.entrySet()) {
            name = name.replaceAll(e.getValue(), e.getKey());
        }
        return name;
    }

    private static void runValidator(Validator validator, SchemaNode rootNode, VContext context) {
        if (rootNode.getEitherSchema().isFirst()) {
            validator.validate(rootNode, context);
            for (SchemaNode childNode : rootNode) {
                runValidator(validator, childNode, context);
            }
        } else {
            context.errors.add(new VError(rootNode, rootNode.getEitherSchema().getSecond()));
        }
    }

    public static VContext runValidator(Validator validator, SchemaNode rootNode) {
        VContext context = new VContext();
        runValidator(validator, rootNode, context);
        return context;
    }

    public static VContext runValidator(Validator validator, Session session, SchemaLike schema, JsonNode node) {
        FullRefResolver fullRefResolver = new MetaResolver(Util.asList(new SelfResolver(schema), new SessionResolver(session)));
        SchemaNode rootNode = new SchemaNode(schema, new PointedNode(node), fullRefResolver);
        return runValidator(validator, rootNode);
    }

    public static boolean matchesType(JsonNode node, String schemaType) {
        if (schemaType.equals("object")) return node.isObject();
        else if (schemaType.equals("array")) return node.isArray();
        else if (schemaType.equals("string")) return node.isTextual();
        else if (schemaType.equals("boolean")) return node.isBoolean();
        else if (schemaType.equals("integer")) return node.isIntegralNumber();
        else if (schemaType.equals("number")) return node.isFloatingPointNumber() || node.isIntegralNumber();
        else return false;
    }

    public static void assertNotNull(String name, Object x) {
        if (x == null) throw new IllegalArgumentException(name+" cannot be null");
    }

    public static <X extends Iterable<X>> Iterator<X> withSelfDepthFirst(final X x) {
        return new Iterator<X>() {

            private boolean hasYieldedSelf = false;
            private final Iterator<X> rootIt = x.iterator();
            private Iterator<X> childIt;

            private void advance() {
                while ((childIt == null || !childIt.hasNext()) && rootIt.hasNext()) {
                    final X next = rootIt.next();
                    childIt = withSelfDepthFirst(next);
                }
            }

            @Override
            public boolean hasNext() {
                if (!hasYieldedSelf) {
                    return true;
                } else {
                    advance();
                    return (childIt != null) && childIt.hasNext();
                }
            }

            @Override
            public X next() {
                if (!hasYieldedSelf) {
                    hasYieldedSelf = true;
                    return x;
                } else {
                    advance();
                    if (childIt != null) return childIt.next();
                    else throw new NoSuchElementException();
                }
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException();
            }
        };
    }

    public static <X> Iterable<X> onceIterable(final Iterator<X> it) {
        return new Iterable<X>() {
            @Override
            public Iterator<X> iterator() {
                return it;
            }
        };
    }

    public static <X> X quickBind(JsonNode node, DomainFactory<X> factory) throws TypeException {
        JacksonBinder<X> binder = new JacksonBinder<X>(makeObjectMapper(), factory);
        return binder.bind(node);
    }

    public static <X> JsonNode quickUnbind(X domain) throws TypeException {
        JacksonBinder<X> binder = new JacksonBinder<X>(makeObjectMapper(), null);
        return binder.unbind(domain);
    }

    public static ObjectMapper makeObjectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonSerialize.Inclusion.NON_NULL);
        mapper.configure(JsonParser.Feature.ALLOW_COMMENTS, true);
        return mapper;
    }

    public static interface Func<A, B> {
        B runFunc(A a);
    }

    public static <X, Y> List<Y> map(Func<X, Y> func, List<X> xs) {
        List<Y> ys = new ArrayList<Y>(xs.size());
        for (X x : xs) {
            ys.add(func.runFunc(x));
        }
        return ys;
    }
}
