package net.exathunk.jsubschema.genschema.githubprofile.declarations.propertiesprojectsitems;

import java.io.Serializable;
import java.util.Set;
import java.util.TreeSet;
import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.map.annotate.JsonDeserialize;

@JsonDeserialize(as = PropertiesProjectsItems.class)
public interface PropertiesProjectsItemsLike {

    boolean hasLastCommit();

    @JsonProperty("lastCommit")
    String getLastCommit();

    @JsonProperty("lastCommit")
    void setLastCommit(String lastCommit);

    boolean hasNumCommits();

    @JsonProperty("numCommits")
    Long getNumCommits();

    @JsonProperty("numCommits")
    void setNumCommits(Long numCommits);

    boolean hasTitle();

    @JsonProperty("title")
    String getTitle();

    @JsonProperty("title")
    void setTitle(String title);

    boolean hasUrl();

    @JsonProperty("url")
    String getUrl();

    @JsonProperty("url")
    void setUrl(String url);

}
