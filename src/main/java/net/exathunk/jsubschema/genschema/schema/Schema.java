package net.exathunk.jsubschema.genschema.schema;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import net.exathunk.jsubschema.genschema.schema.declarations.keylist.KeyList;
import net.exathunk.jsubschema.genschema.schema.declarations.keylist.KeyListLike;

public class Schema implements Cloneable, Serializable, SchemaLike {

    private String type;

    private String description;

    private String format;

    private Map<String, SchemaLike> properties;

    private Map<String, SchemaLike> declarations;

    private String id;

    private String __dollar__ref;

    private SchemaLike items;

    private KeyListLike required;

    private Map<String, KeyListLike> dependencies;

    private Map<String, KeyListLike> forbids;

    private String __dollar__schema;

    private List<String> extensions;

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
    public Map<String, SchemaLike> getProperties() {
        return properties;
    }

    @Override
    public void setProperties(Map<String, SchemaLike> properties) {
        this.properties = properties;
    }

    @Override
    public boolean hasDeclarations() {
        return null != declarations;
    }

    @Override
    public Map<String, SchemaLike> getDeclarations() {
        return declarations;
    }

    @Override
    public void setDeclarations(Map<String, SchemaLike> declarations) {
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
    public SchemaLike getItems() {
        return items;
    }

    @Override
    public void setItems(SchemaLike items) {
        this.items = items;
    }

    @Override
    public boolean hasRequired() {
        return null != required;
    }

    @Override
    public KeyListLike getRequired() {
        return required;
    }

    @Override
    public void setRequired(KeyListLike required) {
        this.required = required;
    }

    @Override
    public boolean hasDependencies() {
        return null != dependencies;
    }

    @Override
    public Map<String, KeyListLike> getDependencies() {
        return dependencies;
    }

    @Override
    public void setDependencies(Map<String, KeyListLike> dependencies) {
        this.dependencies = dependencies;
    }

    @Override
    public boolean hasForbids() {
        return null != forbids;
    }

    @Override
    public Map<String, KeyListLike> getForbids() {
        return forbids;
    }

    @Override
    public void setForbids(Map<String, KeyListLike> forbids) {
        this.forbids = forbids;
    }

    @Override
    public boolean has__dollar__schema() {
        return null != __dollar__schema;
    }

    @Override
    public String get__dollar__schema() {
        return __dollar__schema;
    }

    @Override
    public void set__dollar__schema(String __dollar__schema) {
        this.__dollar__schema = __dollar__schema;
    }

    @Override
    public boolean hasExtensions() {
        return null != extensions;
    }

    @Override
    public List<String> getExtensions() {
        return extensions;
    }

    @Override
    public void setExtensions(List<String> extensions) {
        this.extensions = extensions;
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
        if (dependencies != null) sb.append("dependencies='").append(dependencies).append("', ");
        if (forbids != null) sb.append("forbids='").append(forbids).append("', ");
        if (__dollar__schema != null) sb.append("__dollar__schema='").append(__dollar__schema).append("', ");
        if (extensions != null) sb.append("extensions='").append(extensions).append("', ");
        return sb.append("}").toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o instanceof SchemaLike) {
            SchemaLike other = (SchemaLike) o;
            if (type == null) { if (other.hasType()) { return false; } }
            else if (!type.equals(other.getType())) { return false; }
            if (description == null) { if (other.hasDescription()) { return false; } }
            else if (!description.equals(other.getDescription())) { return false; }
            if (format == null) { if (other.hasFormat()) { return false; } }
            else if (!format.equals(other.getFormat())) { return false; }
            if (properties == null) { if (other.hasProperties()) { return false; } }
            else if (!properties.equals(other.getProperties())) { return false; }
            if (declarations == null) { if (other.hasDeclarations()) { return false; } }
            else if (!declarations.equals(other.getDeclarations())) { return false; }
            if (id == null) { if (other.hasId()) { return false; } }
            else if (!id.equals(other.getId())) { return false; }
            if (__dollar__ref == null) { if (other.has__dollar__ref()) { return false; } }
            else if (!__dollar__ref.equals(other.get__dollar__ref())) { return false; }
            if (items == null) { if (other.hasItems()) { return false; } }
            else if (!items.equals(other.getItems())) { return false; }
            if (required == null) { if (other.hasRequired()) { return false; } }
            else if (!required.equals(other.getRequired())) { return false; }
            if (dependencies == null) { if (other.hasDependencies()) { return false; } }
            else if (!dependencies.equals(other.getDependencies())) { return false; }
            if (forbids == null) { if (other.hasForbids()) { return false; } }
            else if (!forbids.equals(other.getForbids())) { return false; }
            if (__dollar__schema == null) { if (other.has__dollar__schema()) { return false; } }
            else if (!__dollar__schema.equals(other.get__dollar__schema())) { return false; }
            if (extensions == null) { if (other.hasExtensions()) { return false; } }
            else if (!extensions.equals(other.getExtensions())) { return false; }
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
        result = 31 * result + (dependencies == null ? 0 : dependencies.hashCode());
        result = 31 * result + (forbids == null ? 0 : forbids.hashCode());
        result = 31 * result + (__dollar__schema == null ? 0 : __dollar__schema.hashCode());
        result = 31 * result + (extensions == null ? 0 : extensions.hashCode());
        return result;
    }

    public Set<String> diff(SchemaLike other) {
            Set<String> s = new TreeSet<String>();
            if (type == null) { if (other == null || other.hasType()) { s.add("type"); } }
            else if (!type.equals(other.getType())) { s.add("type"); }
            if (description == null) { if (other == null || other.hasDescription()) { s.add("description"); } }
            else if (!description.equals(other.getDescription())) { s.add("description"); }
            if (format == null) { if (other == null || other.hasFormat()) { s.add("format"); } }
            else if (!format.equals(other.getFormat())) { s.add("format"); }
            if (properties == null) { if (other == null || other.hasProperties()) { s.add("properties"); } }
            else if (!properties.equals(other.getProperties())) { s.add("properties"); }
            if (declarations == null) { if (other == null || other.hasDeclarations()) { s.add("declarations"); } }
            else if (!declarations.equals(other.getDeclarations())) { s.add("declarations"); }
            if (id == null) { if (other == null || other.hasId()) { s.add("id"); } }
            else if (!id.equals(other.getId())) { s.add("id"); }
            if (__dollar__ref == null) { if (other == null || other.has__dollar__ref()) { s.add("__dollar__ref"); } }
            else if (!__dollar__ref.equals(other.get__dollar__ref())) { s.add("__dollar__ref"); }
            if (items == null) { if (other == null || other.hasItems()) { s.add("items"); } }
            else if (!items.equals(other.getItems())) { s.add("items"); }
            if (required == null) { if (other == null || other.hasRequired()) { s.add("required"); } }
            else if (!required.equals(other.getRequired())) { s.add("required"); }
            if (dependencies == null) { if (other == null || other.hasDependencies()) { s.add("dependencies"); } }
            else if (!dependencies.equals(other.getDependencies())) { s.add("dependencies"); }
            if (forbids == null) { if (other == null || other.hasForbids()) { s.add("forbids"); } }
            else if (!forbids.equals(other.getForbids())) { s.add("forbids"); }
            if (__dollar__schema == null) { if (other == null || other.has__dollar__schema()) { s.add("__dollar__schema"); } }
            else if (!__dollar__schema.equals(other.get__dollar__schema())) { s.add("__dollar__schema"); }
            if (extensions == null) { if (other == null || other.hasExtensions()) { s.add("extensions"); } }
            else if (!extensions.equals(other.getExtensions())) { s.add("extensions"); }
            return s;
    }

}
