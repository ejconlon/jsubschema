package net.exathunk.jsubschema.genschema;

import java.util.List;
import java.util.Map;
import java.io.Serializable;
import org.codehaus.jackson.annotate.JsonProperty;

public class Schema implements Cloneable, Serializable, SchemaLike {

    private String type;

    private String description;

    private String format;

    private Map<String, Schema> properties;

    private Map<String, Schema> declarations;

    private String id;

    private String __dollar__ref;

    private Schema items;

    private Boolean required;

    private List<String> requires;

    private List<String> forbids;

    @Override
    public boolean hasType() {
        return null != type;
    }

    @Override
    @JsonProperty("type")
    public String getType() {
        return type;
    }

    @Override
    @JsonProperty("type")
    public void setType(String type) {
        this.type = type;
    }

    @Override
    public boolean hasDescription() {
        return null != description;
    }

    @Override
    @JsonProperty("description")
    public String getDescription() {
        return description;
    }

    @Override
    @JsonProperty("description")
    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean hasFormat() {
        return null != format;
    }

    @Override
    @JsonProperty("format")
    public String getFormat() {
        return format;
    }

    @Override
    @JsonProperty("format")
    public void setFormat(String format) {
        this.format = format;
    }

    @Override
    public boolean hasProperties() {
        return null != properties;
    }

    @Override
    @JsonProperty("properties")
    public Map<String, Schema> getProperties() {
        return properties;
    }

    @Override
    @JsonProperty("properties")
    public void setProperties(Map<String, Schema> properties) {
        this.properties = properties;
    }

    @Override
    public boolean hasDeclarations() {
        return null != declarations;
    }

    @Override
    @JsonProperty("declarations")
    public Map<String, Schema> getDeclarations() {
        return declarations;
    }

    @Override
    @JsonProperty("declarations")
    public void setDeclarations(Map<String, Schema> declarations) {
        this.declarations = declarations;
    }

    @Override
    public boolean hasId() {
        return null != id;
    }

    @Override
    @JsonProperty("id")
    public String getId() {
        return id;
    }

    @Override
    @JsonProperty("id")
    public void setId(String id) {
        this.id = id;
    }

    @Override
    public boolean has__dollar__ref() {
        return null != __dollar__ref;
    }

    @Override
    @JsonProperty("$ref")
    public String get__dollar__ref() {
        return __dollar__ref;
    }

    @Override
    @JsonProperty("$ref")
    public void set__dollar__ref(String __dollar__ref) {
        this.__dollar__ref = __dollar__ref;
    }

    @Override
    public boolean hasItems() {
        return null != items;
    }

    @Override
    @JsonProperty("items")
    public Schema getItems() {
        return items;
    }

    @Override
    @JsonProperty("items")
    public void setItems(Schema items) {
        this.items = items;
    }

    @Override
    public boolean hasRequired() {
        return null != required;
    }

    @Override
    @JsonProperty("required")
    public Boolean getRequired() {
        return required;
    }

    @Override
    @JsonProperty("required")
    public void setRequired(Boolean required) {
        this.required = required;
    }

    @Override
    public boolean hasRequires() {
        return null != requires;
    }

    @Override
    @JsonProperty("requires")
    public List<String> getRequires() {
        return requires;
    }

    @Override
    @JsonProperty("requires")
    public void setRequires(List<String> requires) {
        this.requires = requires;
    }

    @Override
    public boolean hasForbids() {
        return null != forbids;
    }

    @Override
    @JsonProperty("forbids")
    public List<String> getForbids() {
        return forbids;
    }

    @Override
    @JsonProperty("forbids")
    public void setForbids(List<String> forbids) {
        this.forbids = forbids;
    }

    @Override
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
        return sb.append("}").toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o instanceof SchemaLike) {
            SchemaLike other = (SchemaLike) o;
            if (type == null) { if (other.hasType()) return false; }
            else if (!type.equals(other.getType())) { return false; }
            if (description == null) { if (other.hasDescription()) return false; }
            else if (!description.equals(other.getDescription())) { return false; }
            if (format == null) { if (other.hasFormat()) return false; }
            else if (!format.equals(other.getFormat())) { return false; }
            if (properties == null) { if (other.hasProperties()) return false; }
            else if (!properties.equals(other.getProperties())) { return false; }
            if (declarations == null) { if (other.hasDeclarations()) return false; }
            else if (!declarations.equals(other.getDeclarations())) { return false; }
            if (id == null) { if (other.hasId()) return false; }
            else if (!id.equals(other.getId())) { return false; }
            if (__dollar__ref == null) { if (other.has__dollar__ref()) return false; }
            else if (!__dollar__ref.equals(other.get__dollar__ref())) { return false; }
            if (items == null) { if (other.hasItems()) return false; }
            else if (!items.equals(other.getItems())) { return false; }
            if (required == null) { if (other.hasRequired()) return false; }
            else if (!required.equals(other.getRequired())) { return false; }
            if (requires == null) { if (other.hasRequires()) return false; }
            else if (!requires.equals(other.getRequires())) { return false; }
            if (forbids == null) { if (other.hasForbids()) return false; }
            else if (!forbids.equals(other.getForbids())) { return false; }
            return true;
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        int result = 0;
        result = 31 * result + (type == null ? 0 : type.hashCode());
        result = 31 * result + (description == null ? 0 : description.hashCode());
        result = 31 * result + (format == null ? 0 : format.hashCode());
        result = 31 * result + (properties == null ? 0 : properties.hashCode());
        result = 31 * result + (declarations == null ? 0 : declarations.hashCode());
        result = 31 * result + (id == null ? 0 : id.hashCode());
        result = 31 * result + (__dollar__ref == null ? 0 : __dollar__ref.hashCode());
        result = 31 * result + (items == null ? 0 : items.hashCode());
        result = 31 * result + (required == null ? 0 : required.hashCode());
        result = 31 * result + (requires == null ? 0 : requires.hashCode());
        result = 31 * result + (forbids == null ? 0 : forbids.hashCode());
        return result;
    }

}
