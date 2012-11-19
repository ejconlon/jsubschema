package net.exathunk.jsubschema.genschema;

import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.map.annotate.JsonDeserialize;

import java.util.List;
import java.util.Map;

@JsonDeserialize(as = Schema.class)
public interface SchemaLike {

    boolean hasType();

    @JsonProperty("type")
    String getType();

    @JsonProperty("type")
    void setType(String type);

    boolean hasDescription();

    @JsonProperty("description")
    String getDescription();

    @JsonProperty("description")
    void setDescription(String description);

    boolean hasFormat();

    @JsonProperty("format")
    String getFormat();

    @JsonProperty("format")
    void setFormat(String format);

    boolean hasProperties();

    @JsonProperty("properties")
    Map<String, SchemaLike> getProperties();

    @JsonProperty("properties")
    @JsonDeserialize(contentAs = Schema.class)
    void setProperties(Map<String, SchemaLike> properties);

    boolean hasDeclarations();

    @JsonProperty("declarations")
    Map<String, SchemaLike> getDeclarations();

    @JsonProperty("declarations")
    @JsonDeserialize(contentAs = Schema.class)
    void setDeclarations(Map<String, SchemaLike> declarations);

    boolean hasId();

    @JsonProperty("id")
    String getId();

    @JsonProperty("id")
    void setId(String id);

    boolean has__dollar__ref();

    @JsonProperty("$ref")
    String get__dollar__ref();

    @JsonProperty("$ref")
    void set__dollar__ref(String __dollar__ref);

    boolean hasItems();

    @JsonProperty("items")
    SchemaLike getItems();

    @JsonProperty("items")
    @JsonDeserialize(as = Schema.class)
    void setItems(SchemaLike items);

    boolean hasRequires();

    @JsonProperty("requires")
    List<String> getRequires();

    @JsonProperty("requires")
    void setRequires(List<String> requires);

    boolean hasForbids();

    @JsonProperty("forbids")
    List<String> getForbids();

    @JsonProperty("forbids")
    void setForbids(List<String> forbids);

    boolean hasRequired();

    @JsonProperty("required")
    List<String> getRequired();

    @JsonProperty("required")
    void setRequired(List<String> required);

}
