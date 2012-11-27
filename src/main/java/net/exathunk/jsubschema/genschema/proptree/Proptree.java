package net.exathunk.jsubschema.genschema.proptree;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

public class Proptree implements Cloneable, Serializable, ProptreeLike {

    private List<ProptreeLike> children;

    private Map<String, String> props;

    @Override
    public boolean hasChildren() {
        return null != children;
    }

    @Override
    public List<ProptreeLike> getChildren() {
        return children;
    }

    @Override
    public void setChildren(List<ProptreeLike> children) {
        this.children = children;
    }

    @Override
    public boolean hasProps() {
        return null != props;
    }

    @Override
    public Map<String, String> getProps() {
        return props;
    }

    @Override
    public void setProps(Map<String, String> props) {
        this.props = props;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Proptree{ ");
        if (children != null) sb.append("children='").append(children).append("', ");
        if (props != null) sb.append("props='").append(props).append("', ");
        return sb.append("}").toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o instanceof ProptreeLike) {
            ProptreeLike other = (ProptreeLike) o;
            if (children == null) { if (other.hasChildren()) { return false; } }
            else if (!children.equals(other.getChildren())) { return false; }
            if (props == null) { if (other.hasProps()) { return false; } }
            else if (!props.equals(other.getProps())) { return false; }
            return true;
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        int result = 0;
        result = 31 * result + (children == null ? 0 : children.hashCode());
        result = 31 * result + (props == null ? 0 : props.hashCode());
        return result;
    }

    public Set<String> diff(ProptreeLike other) {
            Set<String> s = new TreeSet<String>();
            if (children == null) { if (other == null || other.hasChildren()) { s.add("children"); } }
            else if (!children.equals(other.getChildren())) { s.add("children"); }
            if (props == null) { if (other == null || other.hasProps()) { s.add("props"); } }
            else if (!props.equals(other.getProps())) { s.add("props"); }
            return s;
    }

}
