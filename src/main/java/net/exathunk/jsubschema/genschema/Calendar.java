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



}