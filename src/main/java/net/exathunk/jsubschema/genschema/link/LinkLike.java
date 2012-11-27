package net.exathunk.jsubschema.genschema.link;

import java.io.Serializable;
import java.util.Set;
import java.util.TreeSet;
import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.map.annotate.JsonDeserialize;

@JsonDeserialize(as = Link.class)
public interface LinkLike {

    boolean hasEnctype();

    @JsonProperty("enctype")
    String getEnctype();

    @JsonProperty("enctype")
    void setEnctype(String enctype);

    boolean hasHref();

    @JsonProperty("href")
    String getHref();

    @JsonProperty("href")
    void setHref(String href);

    boolean hasMethod();

    @JsonProperty("method")
    String getMethod();

    @JsonProperty("method")
    void setMethod(String method);

    boolean hasRel();

    @JsonProperty("rel")
    String getRel();

    @JsonProperty("rel")
    void setRel(String rel);

}
