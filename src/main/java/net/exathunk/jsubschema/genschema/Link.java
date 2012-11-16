package net.exathunk.jsubschema.genschema;

import org.codehaus.jackson.annotate.JsonProperty;
import java.util.List;
import java.util.Map;
import net.exathunk.jsubschema.gen.GenUtil;

public class Link implements Cloneable {

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
        GenUtil.ToStringContext c = new GenUtil.ToStringContext("Link");
        c.add("href", href);
        c.add("rel", rel);
        c.add("method", method);
        c.add("enctype", enctype);
        return c.finish();  }

}
