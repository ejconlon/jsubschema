package net.exathunk.jsubschema.genschema.schema;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import net.exathunk.jsubschema.genschema.schema.declarations.keylist.KeyList;
import net.exathunk.jsubschema.genschema.schema.declarations.keylist.KeyListLike;
import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.map.annotate.JsonDeserialize;

@JsonDeserialize(as = Schema.class)
public interface SchemaLike {

    boolean has__dollar__ref();

    @JsonProperty("$ref")
    String get__dollar__ref();

    @JsonProperty("$ref")
    void set__dollar__ref(String __dollar__ref);

    boolean has__dollar__schema();

    @JsonProperty("$schema")
    String get__dollar__schema();

    @JsonProperty("$schema")
    void set__dollar__schema(String __dollar__schema);

    boolean hasDeclarations();

    @JsonProperty("declarations")
    Map<String, SchemaLike> getDeclarations();

    @JsonProperty("declarations")
    @JsonDeserialize(contentAs = Schema.class)
    void setDeclarations(Map<String, SchemaLike> declarations);

    boolean hasDependencies();

    @JsonProperty("dependencies")
    Map<String, KeyListLike> getDependencies();

    @JsonProperty("dependencies")
    @JsonDeserialize(contentAs = KeyList.class)
    void setDependencies(Map<String, KeyListLike> dependencies);

    boolean hasDescription();

    @JsonProperty("description")
    String getDescription();

    @JsonProperty("description")
    void setDescription(String description);

    boolean hasExtensions();

    @JsonProperty("extensions")
    List<String> getExtensions();

    @JsonProperty("extensions")
    void setExtensions(List<String> extensions);

    boolean hasForbids();

    @JsonProperty("forbids")
    Map<String, KeyListLike> getForbids();

    @JsonProperty("forbids")
    @JsonDeserialize(contentAs = KeyList.class)
    void setForbids(Map<String, KeyListLike> forbids);

    boolean hasFormat();

    @JsonProperty("format")
    String getFormat();

    @JsonProperty("format")
    void setFormat(String format);

    boolean hasId();

    @JsonProperty("id")
    String getId();

    @JsonProperty("id")
    void setId(String id);

    boolean hasItems();

    @JsonProperty("items")
    SchemaLike getItems();

    @JsonProperty("items")
    @JsonDeserialize(as = Schema.class)
    void setItems(SchemaLike items);

    boolean hasProperties();

    @JsonProperty("properties")
    Map<String, SchemaLike> getProperties();

    @JsonProperty("properties")
    @JsonDeserialize(contentAs = Schema.class)
    void setProperties(Map<String, SchemaLike> properties);

    boolean hasRequired();

    @JsonProperty("required")
    KeyListLike getRequired();

    @JsonProperty("required")
    @JsonDeserialize(as = KeyList.class)
    void setRequired(KeyListLike required);

    boolean hasType();

    @JsonProperty("type")
    String getType();

    @JsonProperty("type")
    void setType(String type);

}
