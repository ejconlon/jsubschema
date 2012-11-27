package net.exathunk.jsubschema.genschema.card.declarations.propertiestel;

import java.io.Serializable;
import java.util.Set;
import java.util.TreeSet;

public class PropertiesTel implements Cloneable, Serializable, PropertiesTelLike {

    private String type;

    private String value;

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
    public boolean hasValue() {
        return null != value;
    }

    @Override
    public String getValue() {
        return value;
    }

    @Override
    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("PropertiesTel{ ");
        if (type != null) sb.append("type='").append(type).append("', ");
        if (value != null) sb.append("value='").append(value).append("', ");
        return sb.append("}").toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o instanceof PropertiesTelLike) {
            PropertiesTelLike other = (PropertiesTelLike) o;
            if (type == null) { if (other.hasType()) { return false; } }
            else if (!type.equals(other.getType())) { return false; }
            if (value == null) { if (other.hasValue()) { return false; } }
            else if (!value.equals(other.getValue())) { return false; }
            return true;
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        int result = 0;
        result = 31 * result + (type == null ? 0 : type.hashCode());
        result = 31 * result + (value == null ? 0 : value.hashCode());
        return result;
    }

    public Set<String> diff(PropertiesTelLike other) {
            Set<String> s = new TreeSet<String>();
            if (type == null) { if (other == null || other.hasType()) { s.add("type"); } }
            else if (!type.equals(other.getType())) { s.add("type"); }
            if (value == null) { if (other == null || other.hasValue()) { s.add("value"); } }
            else if (!value.equals(other.getValue())) { s.add("value"); }
            return s;
    }

}
