package net.exathunk.jsubschema.genschema.proptree;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.map.annotate.JsonDeserialize;

@JsonDeserialize(as = Proptree.class)
public interface ProptreeLike {

    boolean hasChildren();

    @JsonProperty("children")
    List<ProptreeLike> getChildren();

    @JsonProperty("children")
    @JsonDeserialize(contentAs = Proptree.class)
    void setChildren(List<ProptreeLike> children);

    boolean hasProps();

    @JsonProperty("props")
    Map<String, String> getProps();

    @JsonProperty("props")
    void setProps(Map<String, String> props);

}
