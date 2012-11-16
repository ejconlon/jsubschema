package net.exathunk.jsubschema.genschema;

import org.codehaus.jackson.annotate.JsonProperty;
import java.util.List;
import java.util.Map;

public class Event implements Cloneable {

    @JsonProperty("dtstart")
    public String dtstart;

    @JsonProperty("dtend")
    public String dtend;

    @JsonProperty("summary")
    public String summary;

    @JsonProperty("location")
    public String location;

    @JsonProperty("url")
    public String url;

    @JsonProperty("duration")
    public String duration;

    @JsonProperty("rdate")
    public String rdate;

    @JsonProperty("rrule")
    public String rrule;

    @JsonProperty("category")
    public String category;

    @JsonProperty("description")
    public String description;

    @JsonProperty("geo")
    public Geo geo;


    @Override
public String toString() { 
        StringBuilder sb = new StringBuilder("Event{ ");
        if (dtstart != null) sb.append("dtstart='").append(dtstart).append("', ");
        if (dtend != null) sb.append("dtend='").append(dtend).append("', ");
        if (summary != null) sb.append("summary='").append(summary).append("', ");
        if (location != null) sb.append("location='").append(location).append("', ");
        if (url != null) sb.append("url='").append(url).append("', ");
        if (duration != null) sb.append("duration='").append(duration).append("', ");
        if (rdate != null) sb.append("rdate='").append(rdate).append("', ");
        if (rrule != null) sb.append("rrule='").append(rrule).append("', ");
        if (category != null) sb.append("category='").append(category).append("', ");
        if (description != null) sb.append("description='").append(description).append("', ");
        if (geo != null) sb.append("geo='").append(geo).append("', ");
        return sb.append("}").toString();  }
    @Override
public boolean equals(Object o
) { if (this == o) return true;
if (o instanceof Event) {
    Event other = (Event) o;
if (dtstart == null) { if (other.dtstart != null) return false; }
else if (!dtstart.equals(other.dtstart)) { return false; }
if (dtend == null) { if (other.dtend != null) return false; }
else if (!dtend.equals(other.dtend)) { return false; }
if (summary == null) { if (other.summary != null) return false; }
else if (!summary.equals(other.summary)) { return false; }
if (location == null) { if (other.location != null) return false; }
else if (!location.equals(other.location)) { return false; }
if (url == null) { if (other.url != null) return false; }
else if (!url.equals(other.url)) { return false; }
if (duration == null) { if (other.duration != null) return false; }
else if (!duration.equals(other.duration)) { return false; }
if (rdate == null) { if (other.rdate != null) return false; }
else if (!rdate.equals(other.rdate)) { return false; }
if (rrule == null) { if (other.rrule != null) return false; }
else if (!rrule.equals(other.rrule)) { return false; }
if (category == null) { if (other.category != null) return false; }
else if (!category.equals(other.category)) { return false; }
if (description == null) { if (other.description != null) return false; }
else if (!description.equals(other.description)) { return false; }
if (geo == null) { if (other.geo != null) return false; }
else if (!geo.equals(other.geo)) { return false; }
    return true;
} else {
    return false;
}
 }
    @Override
public int hashCode() { int result = 0;
result = 31 * result + (dtstart == null ? 0 : dtstart.hashCode());
result = 31 * result + (dtend == null ? 0 : dtend.hashCode());
result = 31 * result + (summary == null ? 0 : summary.hashCode());
result = 31 * result + (location == null ? 0 : location.hashCode());
result = 31 * result + (url == null ? 0 : url.hashCode());
result = 31 * result + (duration == null ? 0 : duration.hashCode());
result = 31 * result + (rdate == null ? 0 : rdate.hashCode());
result = 31 * result + (rrule == null ? 0 : rrule.hashCode());
result = 31 * result + (category == null ? 0 : category.hashCode());
result = 31 * result + (description == null ? 0 : description.hashCode());
result = 31 * result + (geo == null ? 0 : geo.hashCode());
return result;
 }

}
