package net.exathunk.jsubschema.genschema;

import org.codehaus.jackson.annotate.JsonProperty;
import java.util.List;
import java.util.Map;
import net.exathunk.jsubschema.gen.GenUtil;

public class Schema implements Cloneable {

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


    @Override
public String toString() { 
        GenUtil.ToStringContext c = new GenUtil.ToStringContext("Schema");
        c.add("type", type);
        c.add("description", description);
        c.add("format", format);
        c.add("properties", properties);
        c.add("declarations", declarations);
        c.add("id", id);
        c.add("__dollar__ref", __dollar__ref);
        c.add("items", items);
        c.add("required", required);
        c.add("requires", requires);
        c.add("forbids", forbids);
        return c.finish();  }

}
