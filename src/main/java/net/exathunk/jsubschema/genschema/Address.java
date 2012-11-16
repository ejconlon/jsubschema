package net.exathunk.jsubschema.genschema;

import org.codehaus.jackson.annotate.JsonProperty;
import java.util.List;
import java.util.Map;
import net.exathunk.jsubschema.gen.GenUtil;

public class Address implements Cloneable {

    @JsonProperty("post-office-box")
    public String post__dash__office__dash__box;

    @JsonProperty("extended-address")
    public String extended__dash__address;

    @JsonProperty("street-address")
    public String street__dash__address;

    @JsonProperty("locality")
    public String locality;

    @JsonProperty("region")
    public String region;

    @JsonProperty("postal-code")
    public String postal__dash__code;

    @JsonProperty("country-name")
    public String country__dash__name;


    @Override
public String toString() { 
        GenUtil.ToStringContext c = new GenUtil.ToStringContext("Address");
        c.add("post__dash__office__dash__box", post__dash__office__dash__box);
        c.add("extended__dash__address", extended__dash__address);
        c.add("street__dash__address", street__dash__address);
        c.add("locality", locality);
        c.add("region", region);
        c.add("postal__dash__code", postal__dash__code);
        c.add("country__dash__name", country__dash__name);
        return c.finish();  }

}
