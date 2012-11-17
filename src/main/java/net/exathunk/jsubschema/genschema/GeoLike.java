package net.exathunk.jsubschema.genschema;

import java.util.List;
import java.util.Map;
import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.annotate.JsonTypeInfo;

@JsonTypeInfo(defaultImpl = Geo.class, use = JsonTypeInfo.Id.CLASS)
public interface GeoLike {

    boolean hasLatitude();

    @JsonProperty("latitude")
    Double getLatitude();

    @JsonProperty("latitude")
    void setLatitude(Double latitude);

    boolean hasLongitude();

    @JsonProperty("longitude")
    Double getLongitude();

    @JsonProperty("longitude")
    void setLongitude(Double longitude);

}
