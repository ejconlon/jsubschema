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



}
