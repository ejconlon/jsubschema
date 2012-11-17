package net.exathunk.jsubschema.genschema;

import java.util.List;
import java.util.Map;
import java.io.Serializable;
import org.codehaus.jackson.annotate.JsonProperty;

public class Schema implements Cloneable, Serializable, SchemaLike {

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
    public boolean hasType() {
        return null != type;
    }

    @Override
    public String getType() {
        return type;
    }

    @Override
    public void setType(String type) {
        this.type = type;
    }

    @Override
    public boolean hasDescription() {
        return null != description;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean hasFormat() {
        return null != format;
    }

    @Override
    public String getFormat() {
        return format;
    }

    @Override
    public void setFormat(String format) {
        this.format = format;
    }

    @Override
    public boolean hasProperties() {
        return null != properties;
    }

    @Override
    public Map<String, Schema> getProperties() {
        return properties;
    }

    @Override
    public void setProperties(Map<String, Schema> properties) {
        this.properties = properties;
    }

    @Override
    public boolean hasDeclarations() {
        return null != declarations;
    }

    @Override
    public Map<String, Schema> getDeclarations() {
        return declarations;
    }

    @Override
    public void setDeclarations(Map<String, Schema> declarations) {
        this.declarations = declarations;
    }

    @Override
    public boolean hasId() {
        return null != id;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }

    @Override
    public boolean has__dollar__ref() {
        return null != __dollar__ref;
    }

    @Override
    public String get__dollar__ref() {
        return __dollar__ref;
    }

    @Override
    public void set__dollar__ref(String __dollar__ref) {
        this.__dollar__ref = __dollar__ref;
    }

    @Override
    public boolean hasItems() {
        return null != items;
    }

    @Override
    public Schema getItems() {
        return items;
    }

    @Override
    public void setItems(Schema items) {
        this.items = items;
    }

    @Override
    public boolean hasRequired() {
        return null != required;
    }

    @Override
    public Boolean getRequired() {
        return required;
    }

    @Override
    public void setRequired(Boolean required) {
        this.required = required;
    }

    @Override
    public boolean hasRequires() {
        return null != requires;
    }

    @Override
    public List<String> getRequires() {
        return requires;
    }

    @Override
    public void setRequires(List<String> requires) {
        this.requires = requires;
    }

    @Override
    public boolean hasForbids() {
        return null != forbids;
    }

    @Override
    public List<String> getForbids() {
        return forbids;
    }

    @Override
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
        if (o instanceof Schema) {
            Schema other = (Schema) o;
            if (type == null) { if (other.type != null) return false; }
            else if (!type.equals(other.type)) { return false; }
            if (description == null) { if (other.description != null) return false; }
            else if (!description.equals(other.description)) { return false; }
            if (format == null) { if (other.format != null) return false; }
            else if (!format.equals(other.format)) { return false; }
            if (properties == null) { if (other.properties != null) return false; }
            else if (!properties.equals(other.properties)) { return false; }
            if (declarations == null) { if (other.declarations != null) return false; }
            else if (!declarations.equals(other.declarations)) { return false; }
            if (id == null) { if (other.id != null) return false; }
            else if (!id.equals(other.id)) { return false; }
            if (__dollar__ref == null) { if (other.__dollar__ref != null) return false; }
            else if (!__dollar__ref.equals(other.__dollar__ref)) { return false; }
            if (items == null) { if (other.items != null) return false; }
            else if (!items.equals(other.items)) { return false; }
            if (required == null) { if (other.required != null) return false; }
            else if (!required.equals(other.required)) { return false; }
            if (requires == null) { if (other.requires != null) return false; }
            else if (!requires.equals(other.requires)) { return false; }
            if (forbids == null) { if (other.forbids != null) return false; }
            else if (!forbids.equals(other.forbids)) { return false; }
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
