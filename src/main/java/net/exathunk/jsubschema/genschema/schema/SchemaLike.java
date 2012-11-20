package net.exathunk.jsubschema.genschema.schema;

import net.exathunk.jsubschema.genschema.schema.declarations.stringarray.StringArray;
import net.exathunk.jsubschema.genschema.schema.declarations.stringarray.StringArrayLike;
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

    boolean hasDependencies();

    @JsonProperty("dependencies")
    Map<String, StringArrayLike> getDependencies();

    @JsonProperty("dependencies")
    @JsonDeserialize(contentAs = StringArray.class)
    void setDependencies(Map<String, StringArrayLike> dependencies);

    boolean hasAllows();

    @JsonProperty("allows")
    Map<String, StringArrayLike> getAllows();

    @JsonProperty("allows")
    @JsonDeserialize(contentAs = StringArray.class)
    void setAllows(Map<String, StringArrayLike> allows);

    boolean hasForbidsMap();

    @JsonProperty("forbidsMap")
    Map<String, StringArrayLike> getForbidsMap();

    @JsonProperty("forbidsMap")
    @JsonDeserialize(contentAs = StringArray.class)
    void setForbidsMap(Map<String, StringArrayLike> forbidsMap);

}
