package net.exathunk.jsubschema.genschema;

import java.util.List;
import java.util.Map;
import java.io.Serializable;

public class Geo implements Cloneable, Serializable, GeoLike {

    private Double latitude;

    private Double longitude;

    @Override
    public boolean hasLatitude() {
        return null != latitude;
    }

    @Override
    public Double getLatitude() {
        return latitude;
    }

    @Override
    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    @Override
    public boolean hasLongitude() {
        return null != longitude;
    }

    @Override
    public Double getLongitude() {
        return longitude;
    }

    @Override
    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

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
        if (o instanceof GeoLike) {
            GeoLike other = (GeoLike) o;
            if (latitude == null) { if (other.hasLatitude()) return false; }
            else if (!latitude.equals(other.getLatitude())) { return false; }
            if (longitude == null) { if (other.hasLongitude()) return false; }
            else if (!longitude.equals(other.getLongitude())) { return false; }
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

}
