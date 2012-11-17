package net.exathunk.jsubschema.genschema;

import java.util.List;
import java.util.Map;
import java.io.Serializable;
import net.exathunk.jsubschema.gendeps.Mergeable;
import org.codehaus.jackson.annotate.JsonProperty;

public class Geo implements Cloneable, Serializable, Mergeable<Geo> {

    @JsonProperty("latitude")
    public Double latitude;

    @JsonProperty("longitude")
    public Double longitude;

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Geo{ ");
        if (latitude != null) sb.append("latitude='").append(latitude).append("', ");
        if (longitude != null) sb.append("longitude='").append(longitude).append("', ");
        return sb.append("}").toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o instanceof Geo) {
            Geo other = (Geo) o;
            if (latitude == null) { if (other.latitude != null) return false; }
            else if (!latitude.equals(other.latitude)) { return false; }
            if (longitude == null) { if (other.longitude != null) return false; }
            else if (!longitude.equals(other.longitude)) { return false; }
            return true;
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        int result = 0;
        result = 31 * result + (latitude == null ? 0 : latitude.hashCode());
        result = 31 * result + (longitude == null ? 0 : longitude.hashCode());
        return result;
    }

    @Override
    public void mergeFrom(Geo other) {
        throw new RuntimeException("TODO");
    }

}
