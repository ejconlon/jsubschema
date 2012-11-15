package net.exathunk.jsubschema.base;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.node.ArrayNode;
import org.codehaus.jackson.node.ObjectNode;

import java.util.*;

/**
 * charolastra 11/13/12 10:22 PM
 */
public class ManualSchemaBinder implements Binder<Schema> {
    private static final Set<String> REQUIRED_PROPS = Util.asSet(Arrays.asList("type"));
    private static final Set<String> OPTIONAL_PROPS = Util.asSet(Arrays.asList(
            "properties", "items", "required", "format", "id", "$ref", "requires", "forbids", "description"));
    private static final Set<String> ALL_PROPS;

    static {
        ALL_PROPS = new TreeSet<String>();
        ALL_PROPS.addAll(REQUIRED_PROPS);
        ALL_PROPS.addAll(OPTIONAL_PROPS);
    }

    public Schema bind(final JsonNode node) throws TypeException {
        final Set<String> props = TypeInfo.propsForNode(node);
        if (!props.containsAll(REQUIRED_PROPS)) {
            throw new TypeException("Missing props: "+node);
        } else if (!ALL_PROPS.containsAll(props)) {
            for (String prop : props) {
                if (!ALL_PROPS.contains(prop)) {
                    throw new TypeException("THIS ONE: "+prop);
                }
            }
            throw new TypeException("Extra props: "+node);
        }
        final Schema s = new Schema();
        final Iterator<Map.Entry<String, JsonNode>> rootMapIt = ((ObjectNode)node).getFields();
        while (rootMapIt.hasNext()) {
            Map.Entry<String, JsonNode> entry = rootMapIt.next();
            final String key = entry.getKey();
            final JsonNode child = entry.getValue();
            if (key.equals("type")) {
                TypeException.assertThat(child.isTextual(), "type must be string");
                s.type = child.asText();
            } else if (key.equals("required")) {
                TypeException.assertThat(child.isBoolean(), "required must be boolean");
                s.required = child.asBoolean();
            } else if (key.equals("id")) {
                TypeException.assertThat(child.isTextual(), "id must be string");
                s.id = child.asText();
            } else if (key.equals("$ref")) {
                TypeException.assertThat(child.isTextual(), "ref must be string");
                s.__dollar__ref = child.asText();
            } else if (key.equals("format")) {
                TypeException.assertThat(child.isTextual(), "format must be string");
                s.format = child.asText();
            } else if (key.equals("description")) {
                TypeException.assertThat(child.isTextual(), "description must be string");
                s.description = child.asText();
            } else if (key.equals("forbids")) {
                TypeException.assertThat(child.isArray(), "forbids must be array");
                List<String> list = new ArrayList<String>();
                for (JsonNode grandChild : (ArrayNode)child) {
                    TypeException.assertThat(grandChild.isTextual(), "forbids item must be string");
                    list.add(grandChild.asText());
                }
                s.forbids = list;
            } else if (key.equals("requires")) {
                TypeException.assertThat(child.isArray(), "requires must be array");
                List<String> list = new ArrayList<String>();
                for (JsonNode grandChild : (ArrayNode)child) {
                    TypeException.assertThat(grandChild.isTextual(), "requires item must be string");
                    list.add(grandChild.asText());
                }
                s.requires = list;
            } else if (key.equals("properties")) {
                TypeException.assertThat(child.isObject(), "properties must be object");
                Map<String, Schema> map = new TreeMap<String, Schema>();
                final Iterator<Map.Entry<String, JsonNode>> childMapIt = ((ObjectNode)child).getFields();
                while (childMapIt.hasNext()) {
                    Map.Entry<String, JsonNode> grandChild = childMapIt.next();
                    final String gcKey = grandChild.getKey();
                    final JsonNode gcNode = grandChild.getValue();
                    Schema s2 = bind(gcNode);
                    map.put(gcKey, s2);
                }
                s.properties = map;
            }
        }
        return s;
    }

    @Override
    public JsonNode unbind(Schema domain) throws TypeException {
        throw new TypeException("NOT IMPLEMENTED");
    }
}
