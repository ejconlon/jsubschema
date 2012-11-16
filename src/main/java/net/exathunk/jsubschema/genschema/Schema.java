package net.exathunk.jsubschema.genschema;

import org.codehaus.jackson.annotate.JsonProperty;
import java.util.List;
import java.util.Map;

public class Schema {

    @JsonProperty("type")
    public String type;

    @JsonProperty("description")
    public String description;

    @JsonProperty("format")
    public String format;

    @JsonProperty("properties")
    public Map<String, Schema> properties;

    @JsonProperty("declarations")
    public Map<String, Schema> declarations;

    @JsonProperty("id")
    public String id;

    @JsonProperty("$ref")
    public String __dollar__ref;

    @JsonProperty("items")
    public Schema items;

    @JsonProperty("required")
    public Boolean required;

    @JsonProperty("requires")
    public List<String> requires;

    @JsonProperty("forbids")
    public List<String> forbids;


    public String toString() { 
        StringBuilder sb = new StringBuilder("Schema{ ");
        if (type != null) sb.append("type='").append(type).append("', ");
        if (description != null) sb.append("description='").append(description).append("', ");
        if (format != null) sb.append("format='").append(format).append("', ");
        if (properties != null) sb.append("properties='").append(properties).append("', ");
        if (declarations != null) sb.append("declarations='").append(declarations).append("', ");
        if (id != null) sb.append("id='").append(id).append("', ");
        if (__dollar__ref != null) sb.append("__dollar__ref='").append(__dollar__ref).append("', ");
        if (items != null) sb.append("items='").append(items).append("', ");
        if (required != null) sb.append("required='").append(required).append("', ");
        if (requires != null) sb.append("requires='").append(requires).append("', ");
        if (forbids != null) sb.append("forbids='").append(forbids).append("', ");
        return sb.append("}").toString();  }

}
