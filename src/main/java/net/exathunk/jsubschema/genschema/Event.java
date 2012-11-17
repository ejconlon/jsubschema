package net.exathunk.jsubschema.genschema;

import java.util.List;
import java.util.Map;
import java.io.Serializable;
import org.codehaus.jackson.annotate.JsonProperty;

public class Event implements Cloneable, Serializable, EventLike {

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
    public boolean hasDtstart() {
        return null != dtstart;
    }

    @Override
    public String getDtstart() {
        return dtstart;
    }

    @Override
    public void setDtstart(String dtstart) {
        this.dtstart = dtstart;
    }

    @Override
    public boolean hasDtend() {
        return null != dtend;
    }

    @Override
    public String getDtend() {
        return dtend;
    }

    @Override
    public void setDtend(String dtend) {
        this.dtend = dtend;
    }

    @Override
    public boolean hasSummary() {
        return null != summary;
    }

    @Override
    public String getSummary() {
        return summary;
    }

    @Override
    public void setSummary(String summary) {
        this.summary = summary;
    }

    @Override
    public boolean hasLocation() {
        return null != location;
    }

    @Override
    public String getLocation() {
        return location;
    }

    @Override
    public void setLocation(String location) {
        this.location = location;
    }

    @Override
    public boolean hasUrl() {
        return null != url;
    }

    @Override
    public String getUrl() {
        return url;
    }

    @Override
    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public boolean hasDuration() {
        return null != duration;
    }

    @Override
    public String getDuration() {
        return duration;
    }

    @Override
    public void setDuration(String duration) {
        this.duration = duration;
    }

    @Override
    public boolean hasRdate() {
        return null != rdate;
    }

    @Override
    public String getRdate() {
        return rdate;
    }

    @Override
    public void setRdate(String rdate) {
        this.rdate = rdate;
    }

    @Override
    public boolean hasRrule() {
        return null != rrule;
    }

    @Override
    public String getRrule() {
        return rrule;
    }

    @Override
    public void setRrule(String rrule) {
        this.rrule = rrule;
    }

    @Override
    public boolean hasCategory() {
        return null != category;
    }

    @Override
    public String getCategory() {
        return category;
    }

    @Override
    public void setCategory(String category) {
        this.category = category;
    }

    @Override
    public boolean hasDescription() {
        return null != description;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean hasGeo() {
        return null != geo;
    }

    @Override
    public Geo getGeo() {
        return geo;
    }

    @Override
    public void setGeo(Geo geo) {
        this.geo = geo;
    }

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
        return sb.append("}").toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
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
    public int hashCode() {
        int result = 0;
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
