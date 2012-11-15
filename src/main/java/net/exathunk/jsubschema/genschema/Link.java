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



}
