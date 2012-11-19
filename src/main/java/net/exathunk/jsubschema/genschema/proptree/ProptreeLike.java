package net.exathunk.jsubschema.genschema.proptree;

import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.map.annotate.JsonDeserialize;
import java.util.Map;
import java.util.List;

@JsonDeserialize(as = Proptree.class)
public interface ProptreeLike {

    boolean hasProps();

    @JsonProperty("props")
    Map<String, String> getProps();

    @JsonProperty("props")
    void setProps(Map<String, String> props);

    boolean hasChildren();

    @JsonProperty("children")
    List<ProptreeLike> getChildren();

    @JsonProperty("children")
    @JsonDeserialize(contentAs = Proptree.class)
    void setChildren(List<ProptreeLike> children);

}
