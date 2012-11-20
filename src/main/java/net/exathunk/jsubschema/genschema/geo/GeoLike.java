package net.exathunk.jsubschema.genschema.geo;

import java.io.Serializable;
import java.util.Set;
import java.util.TreeSet;
import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.map.annotate.JsonDeserialize;

@JsonDeserialize(as = Geo.class)
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
