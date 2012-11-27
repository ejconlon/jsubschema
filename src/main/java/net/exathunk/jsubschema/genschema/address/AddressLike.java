package net.exathunk.jsubschema.genschema.address;

import java.io.Serializable;
import java.util.Set;
import java.util.TreeSet;
import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.map.annotate.JsonDeserialize;

@JsonDeserialize(as = Address.class)
public interface AddressLike {

    boolean hasCountry__dash__name();

    @JsonProperty("country-name")
    String getCountry__dash__name();

    @JsonProperty("country-name")
    void setCountry__dash__name(String country__dash__name);

    boolean hasExtended__dash__address();

    @JsonProperty("extended-address")
    String getExtended__dash__address();

    @JsonProperty("extended-address")
    void setExtended__dash__address(String extended__dash__address);

    boolean hasLocality();

    @JsonProperty("locality")
    String getLocality();

    @JsonProperty("locality")
    void setLocality(String locality);

    boolean hasPost__dash__office__dash__box();

    @JsonProperty("post-office-box")
    String getPost__dash__office__dash__box();

    @JsonProperty("post-office-box")
    void setPost__dash__office__dash__box(String post__dash__office__dash__box);

    boolean hasPostal__dash__code();

    @JsonProperty("postal-code")
    String getPostal__dash__code();

    @JsonProperty("postal-code")
    void setPostal__dash__code(String postal__dash__code);

    boolean hasRegion();

    @JsonProperty("region")
    String getRegion();

    @JsonProperty("region")
    void setRegion(String region);

    boolean hasStreet__dash__address();

    @JsonProperty("street-address")
    String getStreet__dash__address();

    @JsonProperty("street-address")
    void setStreet__dash__address(String street__dash__address);

}
