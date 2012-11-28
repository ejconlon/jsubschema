package net.exathunk.jsubschema.genschema.githubprofile.declarations.propertiesprojects;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import net.exathunk.jsubschema.genschema.githubprofile.declarations.propertiesprojectsitems.PropertiesProjectsItems;
import net.exathunk.jsubschema.genschema.githubprofile.declarations.propertiesprojectsitems.PropertiesProjectsItemsLike;
import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.map.annotate.JsonDeserialize;

@JsonDeserialize(as = PropertiesProjects.class)
public interface PropertiesProjectsLike extends List<Map<String, PropertiesProjectsItemsLike>> {

}
