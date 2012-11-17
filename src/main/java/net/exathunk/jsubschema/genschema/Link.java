package net.exathunk.jsubschema.genschema;

import java.util.List;
import java.util.Map;
import java.io.Serializable;
import org.codehaus.jackson.annotate.JsonProperty;

public class Link implements Cloneable, Serializable {

    @JsonProperty("href")
    public String href;

    @JsonProperty("rel")
    public String rel;

    @JsonProperty("method")
    public String method;

    @JsonProperty("enctype")
    public String enctype;

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
        if (o instanceof Link) {
            Link other = (Link) o;
            if (href == null) { if (other.href != null) return false; }
            else if (!href.equals(other.href)) { return false; }
            if (rel == null) { if (other.rel != null) return false; }
            else if (!rel.equals(other.rel)) { return false; }
            if (method == null) { if (other.method != null) return false; }
            else if (!method.equals(other.method)) { return false; }
            if (enctype == null) { if (other.enctype != null) return false; }
            else if (!enctype.equals(other.enctype)) { return false; }
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

}
