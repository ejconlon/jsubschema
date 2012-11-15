package net.exathunk.jsubschema.genschema;

import org.codehaus.jackson.annotate.JsonProperty;
import java.util.List;
import java.util.Map;

public class Calendar {

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


    public String toString() { 
        StringBuilder sb = new StringBuilder("Calendar{ ");
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

}
