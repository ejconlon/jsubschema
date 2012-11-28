package net.exathunk.jsubschema.genschema.githubprofile;

import java.io.Serializable;
import java.util.Set;
import java.util.TreeSet;
import net.exathunk.jsubschema.genschema.githubprofile.declarations.propertiesprojects.PropertiesProjects;
import net.exathunk.jsubschema.genschema.githubprofile.declarations.propertiesprojects.PropertiesProjectsLike;
import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.map.annotate.JsonDeserialize;

@JsonDeserialize(as = Githubprofile.class)
public interface GithubprofileLike {

    boolean hasEmail();

    @JsonProperty("email")
    String getEmail();

    @JsonProperty("email")
    void setEmail(String email);

    boolean hasProjects();

    @JsonProperty("projects")
    PropertiesProjectsLike getProjects();

    @JsonProperty("projects")
    @JsonDeserialize(as = PropertiesProjects.class)
    void setProjects(PropertiesProjectsLike projects);

}
