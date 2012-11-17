package net.exathunk.jsubschema.genschema;

import java.util.List;
import java.util.Map;
import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.annotate.JsonTypeInfo;

@JsonTypeInfo(defaultImpl = Address.class, use = JsonTypeInfo.Id.CLASS)
public interface AddressLike {

    boolean hasPost__dash__office__dash__box();

    @JsonProperty("post-office-box")
    String getPost__dash__office__dash__box();

    @JsonProperty("post-office-box")
    void setPost__dash__office__dash__box(String post__dash__office__dash__box);

    boolean hasExtended__dash__address();

    @JsonProperty("extended-address")
    String getExtended__dash__address();

    @JsonProperty("extended-address")
    void setExtended__dash__address(String extended__dash__address);

    boolean hasStreet__dash__address();

    @JsonProperty("street-address")
    String getStreet__dash__address();

    @JsonProperty("street-address")
    void setStreet__dash__address(String street__dash__address);

    boolean hasLocality();

    @JsonProperty("locality")
    String getLocality();

    @JsonProperty("locality")
    void setLocality(String locality);

    boolean hasRegion();

    @JsonProperty("region")
    String getRegion();

    @JsonProperty("region")
    void setRegion(String region);

    boolean hasPostal__dash__code();

    @JsonProperty("postal-code")
    String getPostal__dash__code();

    @JsonProperty("postal-code")
    void setPostal__dash__code(String postal__dash__code);

    boolean hasCountry__dash__name();

    @JsonProperty("country-name")
    String getCountry__dash__name();

    @JsonProperty("country-name")
    void setCountry__dash__name(String country__dash__name);

}
