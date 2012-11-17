package net.exathunk.jsubschema.genschema;

import java.util.List;
import java.util.Map;
import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.annotate.JsonTypeInfo;
import org.codehaus.jackson.map.annotate.JsonDeserialize;

@JsonTypeInfo(defaultImpl = Link.class, use = JsonTypeInfo.Id.NONE)
public interface LinkLike {

    boolean hasHref();

    @JsonProperty("href")
    String getHref();

    @JsonProperty("href")
    void setHref(String href);

    boolean hasRel();

    @JsonProperty("rel")
    String getRel();

    @JsonProperty("rel")
    void setRel(String rel);

    boolean hasMethod();

    @JsonProperty("method")
    String getMethod();

    @JsonProperty("method")
    void setMethod(String method);

    boolean hasEnctype();

    @JsonProperty("enctype")
    String getEnctype();

    @JsonProperty("enctype")
    void setEnctype(String enctype);

}
