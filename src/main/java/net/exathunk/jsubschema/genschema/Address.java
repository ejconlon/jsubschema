package net.exathunk.jsubschema.genschema;

import org.codehaus.jackson.annotate.JsonProperty;
import java.util.List;
import java.util.Map;

public class Address {

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



}
