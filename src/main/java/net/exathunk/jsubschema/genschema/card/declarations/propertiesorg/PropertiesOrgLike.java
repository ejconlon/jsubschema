package net.exathunk.jsubschema.genschema.card.declarations.propertiesorg;

import java.io.Serializable;
import java.util.Set;
import java.util.TreeSet;
import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.map.annotate.JsonDeserialize;

@JsonDeserialize(as = PropertiesOrg.class)
public interface PropertiesOrgLike {

    boolean hasOrganizationName();

    @JsonProperty("organizationName")
    String getOrganizationName();

    @JsonProperty("organizationName")
    void setOrganizationName(String organizationName);

    boolean hasOrganizationUnit();

    @JsonProperty("organizationUnit")
    String getOrganizationUnit();

    @JsonProperty("organizationUnit")
    void setOrganizationUnit(String organizationUnit);

}
