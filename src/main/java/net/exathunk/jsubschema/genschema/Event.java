package net.exathunk.jsubschema.genschema;

import org.codehaus.jackson.annotate.JsonProperty;
import java.util.List;
import java.util.Map;
import net.exathunk.jsubschema.gen.GenUtil;

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
        GenUtil.ToStringContext c = new GenUtil.ToStringContext("Event");
        c.add("dtstart", dtstart);
        c.add("dtend", dtend);
        c.add("summary", summary);
        c.add("location", location);
        c.add("url", url);
        c.add("duration", duration);
        c.add("rdate", rdate);
        c.add("rrule", rrule);
        c.add("category", category);
        c.add("description", description);
        c.add("geo", geo);
        return c.finish();  }

}
