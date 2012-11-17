package net.exathunk.jsubschema.genschema;

import java.util.List;
import java.util.Map;
import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.annotate.JsonTypeInfo;
import org.codehaus.jackson.map.annotate.JsonDeserialize;

@JsonTypeInfo(defaultImpl = Geo.class, use = JsonTypeInfo.Id.NONE)
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
