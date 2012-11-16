package net.exathunk.jsubschema.genschema;

import org.codehaus.jackson.annotate.JsonProperty;
import java.util.List;
import java.util.Map;
import net.exathunk.jsubschema.gen.GenUtil;

public class Geo implements Cloneable {

    @JsonProperty("latitude")
    public Double latitude;

    @JsonProperty("longitude")
    public Double longitude;


    @Override
public String toString() { 
        GenUtil.ToStringContext c = new GenUtil.ToStringContext("Geo");
        c.add("latitude", latitude);
        c.add("longitude", longitude);
        return c.finish();  }

}
