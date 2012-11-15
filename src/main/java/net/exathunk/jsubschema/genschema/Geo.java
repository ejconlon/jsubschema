package net.exathunk.jsubschema.genschema;

import org.codehaus.jackson.annotate.JsonProperty;
import java.util.List;
import java.util.Map;

public class Geo {

    @JsonProperty("latitude")
    public Double latitude;

    @JsonProperty("longitude")
    public Double longitude;


    public String toString() { 
        StringBuilder sb = new StringBuilder("Geo{ ");
        if (latitude != null) sb.append("latitude='").append(latitude).append("', ");
        if (longitude != null) sb.append("longitude='").append(longitude).append("', ");
        return sb.append("}").toString();  }

}
