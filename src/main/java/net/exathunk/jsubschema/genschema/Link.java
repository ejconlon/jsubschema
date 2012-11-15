package net.exathunk.jsubschema.genschema;

import org.codehaus.jackson.annotate.JsonProperty;
import java.util.List;
import java.util.Map;

public class Link {

    @JsonProperty("href")
    public String href;

    @JsonProperty("rel")
    public String rel;

    @JsonProperty("method")
    public String method;

    @JsonProperty("enctype")
    public String enctype;


    public String toString() { 
        StringBuilder sb = new StringBuilder("Link{ ");
        if (href != null) sb.append("href='").append(href).append("', ");
        if (rel != null) sb.append("rel='").append(rel).append("', ");
        if (method != null) sb.append("method='").append(method).append("', ");
        if (enctype != null) sb.append("enctype='").append(enctype).append("', ");
        return sb.append("}").toString();  }

}
