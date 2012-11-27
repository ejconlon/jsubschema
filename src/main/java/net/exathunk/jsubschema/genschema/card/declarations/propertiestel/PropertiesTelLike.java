package net.exathunk.jsubschema.genschema.card.declarations.propertiestel;

import java.io.Serializable;
import java.util.Set;
import java.util.TreeSet;
import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.map.annotate.JsonDeserialize;

@JsonDeserialize(as = PropertiesTel.class)
public interface PropertiesTelLike {

    boolean hasType();

    @JsonProperty("type")
    String getType();

    @JsonProperty("type")
    void setType(String type);

    boolean hasValue();

    @JsonProperty("value")
    String getValue();

    @JsonProperty("value")
    void setValue(String value);

}
