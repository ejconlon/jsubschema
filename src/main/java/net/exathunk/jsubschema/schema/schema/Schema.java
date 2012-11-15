package net.exathunk.jsubschema.schema.schema;

import org.codehaus.jackson.annotate.JsonProperty;

import java.util.List;
import java.util.Map;

/**
 * charolastra 11/13/12 10:22 PM
 */
public class Schema {

    @JsonProperty("type")
    public String type;

    @JsonProperty("description")
    public String description;

    @JsonProperty("required")
    public Boolean required;

    @JsonProperty("id")
    public String id;

    @JsonProperty("$ref")
    public String __dollar__ref;

    @JsonProperty("format")
    public String format;

    @JsonProperty("requires")
    public List<String> requires;

    @JsonProperty("forbids")
    public List<String> forbids;

    @JsonProperty("properties")
    public Map<String, Schema> properties;
    
    @JsonProperty("items")
    public Schema items;

    @Override
    public String toString() {
        return "Schema{" +
                ((type != null) ? "type='" + type + "', " : "") +
                ((__dollar__ref != null) ? "$ref='" + __dollar__ref + "', " : "") +
                ((format != null) ? "format='" + format + "', " : "") +
                ((required != null) ? "required='" + required + "', " : "") +
                ((id != null) ? "id='" + id + "', " : "") +
                ((requires != null) ? "requires='" + requires + "', " : "") +
                ((forbids != null) ? "forbids='" + forbids + "', " : "") +
                ((properties != null) ? "properties='" + properties + "', " : "") +
                ((items != null) ? "items='" + items + "', " : "") +
                '}';
    }
}
