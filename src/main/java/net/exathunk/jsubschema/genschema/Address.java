package net.exathunk.jsubschema.genschema;

import org.codehaus.jackson.annotate.JsonProperty;
import java.util.List;
import java.util.Map;

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
        StringBuilder sb = new StringBuilder("Address{ ");
        if (post__dash__office__dash__box != null) sb.append("post__dash__office__dash__box='").append(post__dash__office__dash__box).append("', ");
        if (extended__dash__address != null) sb.append("extended__dash__address='").append(extended__dash__address).append("', ");
        if (street__dash__address != null) sb.append("street__dash__address='").append(street__dash__address).append("', ");
        if (locality != null) sb.append("locality='").append(locality).append("', ");
        if (region != null) sb.append("region='").append(region).append("', ");
        if (postal__dash__code != null) sb.append("postal__dash__code='").append(postal__dash__code).append("', ");
        if (country__dash__name != null) sb.append("country__dash__name='").append(country__dash__name).append("', ");
        return sb.append("}").toString();  }

}
