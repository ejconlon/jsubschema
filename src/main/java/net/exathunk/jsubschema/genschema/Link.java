package net.exathunk.jsubschema.genschema;

import java.util.List;
import java.util.Map;
import java.io.Serializable;
import java.util.Set;
import java.util.TreeSet;

public class Link implements Cloneable, Serializable, LinkLike {

    private String href;

    private String rel;

    private String method;

    private String enctype;

    @Override
    public boolean hasHref() {
        return null != href;
    }

    @Override
    public String getHref() {
        return href;
    }

    @Override
    public void setHref(String href) {
        this.href = href;
    }

    @Override
    public boolean hasRel() {
        return null != rel;
    }

    @Override
    public String getRel() {
        return rel;
    }

    @Override
    public void setRel(String rel) {
        this.rel = rel;
    }

    @Override
    public boolean hasMethod() {
        return null != method;
    }

    @Override
    public String getMethod() {
        return method;
    }

    @Override
    public void setMethod(String method) {
        this.method = method;
    }

    @Override
    public boolean hasEnctype() {
        return null != enctype;
    }

    @Override
    public String getEnctype() {
        return enctype;
    }

    @Override
    public void setEnctype(String enctype) {
        this.enctype = enctype;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Link{ ");
        if (href != null) sb.append("href='").append(href).append("', ");
        if (rel != null) sb.append("rel='").append(rel).append("', ");
        if (method != null) sb.append("method='").append(method).append("', ");
        if (enctype != null) sb.append("enctype='").append(enctype).append("', ");
        return sb.append("}").toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o instanceof LinkLike) {
            LinkLike other = (LinkLike) o;
            if (href == null) { if (other.hasHref()) { return false; } }
            else if (!href.equals(other.getHref())) { return false; }
            if (rel == null) { if (other.hasRel()) { return false; } }
            else if (!rel.equals(other.getRel())) { return false; }
            if (method == null) { if (other.hasMethod()) { return false; } }
            else if (!method.equals(other.getMethod())) { return false; }
            if (enctype == null) { if (other.hasEnctype()) { return false; } }
            else if (!enctype.equals(other.getEnctype())) { return false; }
            return true;
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        int result = 0;
        result = 31 * result + (href == null ? 0 : href.hashCode());
        result = 31 * result + (rel == null ? 0 : rel.hashCode());
        result = 31 * result + (method == null ? 0 : method.hashCode());
        result = 31 * result + (enctype == null ? 0 : enctype.hashCode());
        return result;
    }

    public Set<String> diff(LinkLike other) {
            Set<String> s = new TreeSet<String>();
            if (href == null) { if (other == null || other.hasHref()) { s.add("href"); } }
            else if (!href.equals(other.getHref())) { s.add("href"); }
            if (rel == null) { if (other == null || other.hasRel()) { s.add("rel"); } }
            else if (!rel.equals(other.getRel())) { s.add("rel"); }
            if (method == null) { if (other == null || other.hasMethod()) { s.add("method"); } }
            else if (!method.equals(other.getMethod())) { s.add("method"); }
            if (enctype == null) { if (other == null || other.hasEnctype()) { s.add("enctype"); } }
            else if (!enctype.equals(other.getEnctype())) { s.add("enctype"); }
            return s;
    }

}
