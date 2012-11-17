package net.exathunk.jsubschema.genschema;

import java.util.List;
import java.util.Map;

public interface GeoLike {

    boolean hasLatitude();
    Double getLatitude();
    void setLatitude(Double latitude);
    boolean hasLongitude();
    Double getLongitude();
    void setLongitude(Double longitude);
}
