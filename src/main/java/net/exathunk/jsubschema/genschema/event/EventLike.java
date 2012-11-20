package net.exathunk.jsubschema.genschema.event;

import java.io.Serializable;
import java.util.Set;
import java.util.TreeSet;
import net.exathunk.jsubschema.genschema.geo.Geo;
import net.exathunk.jsubschema.genschema.geo.GeoLike;
import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.map.annotate.JsonDeserialize;

@JsonDeserialize(as = Event.class)
public interface EventLike {

    boolean hasDtstart();

    @JsonProperty("dtstart")
    String getDtstart();

    @JsonProperty("dtstart")
    void setDtstart(String dtstart);

    boolean hasDtend();

    @JsonProperty("dtend")
    String getDtend();

    @JsonProperty("dtend")
    void setDtend(String dtend);

    boolean hasSummary();

    @JsonProperty("summary")
    String getSummary();

    @JsonProperty("summary")
    void setSummary(String summary);

    boolean hasLocation();

    @JsonProperty("location")
    String getLocation();

    @JsonProperty("location")
    void setLocation(String location);

    boolean hasUrl();

    @JsonProperty("url")
    String getUrl();

    @JsonProperty("url")
    void setUrl(String url);

    boolean hasDuration();

    @JsonProperty("duration")
    String getDuration();

    @JsonProperty("duration")
    void setDuration(String duration);

    boolean hasRdate();

    @JsonProperty("rdate")
    String getRdate();

    @JsonProperty("rdate")
    void setRdate(String rdate);

    boolean hasRrule();

    @JsonProperty("rrule")
    String getRrule();

    @JsonProperty("rrule")
    void setRrule(String rrule);

    boolean hasCategory();

    @JsonProperty("category")
    String getCategory();

    @JsonProperty("category")
    void setCategory(String category);

    boolean hasDescription();

    @JsonProperty("description")
    String getDescription();

    @JsonProperty("description")
    void setDescription(String description);

    boolean hasGeo();

    @JsonProperty("geo")
    GeoLike getGeo();

    @JsonProperty("geo")
    @JsonDeserialize(as = Geo.class)
    void setGeo(GeoLike geo);

}
