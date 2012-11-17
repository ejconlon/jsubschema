package net.exathunk.jsubschema.genschema;

import java.util.List;
import java.util.Map;
import java.io.Serializable;
import org.codehaus.jackson.annotate.JsonProperty;

public class Event implements Cloneable, Serializable, EventLike {

    public String dtstart;

    public String dtend;

    public String summary;

    public String location;

    public String url;

    public String duration;

    public String rdate;

    public String rrule;

    public String category;

    public String description;

    public Geo geo;

    @Override
    public boolean hasDtstart() {
        return null != dtstart;
    }

    @Override
    @JsonProperty("dtstart")
    public String getDtstart() {
        return dtstart;
    }

    @Override
    @JsonProperty("dtstart")
    public void setDtstart(String dtstart) {
        this.dtstart = dtstart;
    }

    @Override
    public boolean hasDtend() {
        return null != dtend;
    }

    @Override
    @JsonProperty("dtend")
    public String getDtend() {
        return dtend;
    }

    @Override
    @JsonProperty("dtend")
    public void setDtend(String dtend) {
        this.dtend = dtend;
    }

    @Override
    public boolean hasSummary() {
        return null != summary;
    }

    @Override
    @JsonProperty("summary")
    public String getSummary() {
        return summary;
    }

    @Override
    @JsonProperty("summary")
    public void setSummary(String summary) {
        this.summary = summary;
    }

    @Override
    public boolean hasLocation() {
        return null != location;
    }

    @Override
    @JsonProperty("location")
    public String getLocation() {
        return location;
    }

    @Override
    @JsonProperty("location")
    public void setLocation(String location) {
        this.location = location;
    }

    @Override
    public boolean hasUrl() {
        return null != url;
    }

    @Override
    @JsonProperty("url")
    public String getUrl() {
        return url;
    }

    @Override
    @JsonProperty("url")
    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public boolean hasDuration() {
        return null != duration;
    }

    @Override
    @JsonProperty("duration")
    public String getDuration() {
        return duration;
    }

    @Override
    @JsonProperty("duration")
    public void setDuration(String duration) {
        this.duration = duration;
    }

    @Override
    public boolean hasRdate() {
        return null != rdate;
    }

    @Override
    @JsonProperty("rdate")
    public String getRdate() {
        return rdate;
    }

    @Override
    @JsonProperty("rdate")
    public void setRdate(String rdate) {
        this.rdate = rdate;
    }

    @Override
    public boolean hasRrule() {
        return null != rrule;
    }

    @Override
    @JsonProperty("rrule")
    public String getRrule() {
        return rrule;
    }

    @Override
    @JsonProperty("rrule")
    public void setRrule(String rrule) {
        this.rrule = rrule;
    }

    @Override
    public boolean hasCategory() {
        return null != category;
    }

    @Override
    @JsonProperty("category")
    public String getCategory() {
        return category;
    }

    @Override
    @JsonProperty("category")
    public void setCategory(String category) {
        this.category = category;
    }

    @Override
    public boolean hasDescription() {
        return null != description;
    }

    @Override
    @JsonProperty("description")
    public String getDescription() {
        return description;
    }

    @Override
    @JsonProperty("description")
    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean hasGeo() {
        return null != geo;
    }

    @Override
    @JsonProperty("geo")
    public Geo getGeo() {
        return geo;
    }

    @Override
    @JsonProperty("geo")
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
        if (o instanceof EventLike) {
            EventLike other = (EventLike) o;
            if (dtstart == null) { if (other.hasDtstart()) return false; }
            else if (!dtstart.equals(other.getDtstart())) { return false; }
            if (dtend == null) { if (other.hasDtend()) return false; }
            else if (!dtend.equals(other.getDtend())) { return false; }
            if (summary == null) { if (other.hasSummary()) return false; }
            else if (!summary.equals(other.getSummary())) { return false; }
            if (location == null) { if (other.hasLocation()) return false; }
            else if (!location.equals(other.getLocation())) { return false; }
            if (url == null) { if (other.hasUrl()) return false; }
            else if (!url.equals(other.getUrl())) { return false; }
            if (duration == null) { if (other.hasDuration()) return false; }
            else if (!duration.equals(other.getDuration())) { return false; }
            if (rdate == null) { if (other.hasRdate()) return false; }
            else if (!rdate.equals(other.getRdate())) { return false; }
            if (rrule == null) { if (other.hasRrule()) return false; }
            else if (!rrule.equals(other.getRrule())) { return false; }
            if (category == null) { if (other.hasCategory()) return false; }
            else if (!category.equals(other.getCategory())) { return false; }
            if (description == null) { if (other.hasDescription()) return false; }
            else if (!description.equals(other.getDescription())) { return false; }
            if (geo == null) { if (other.hasGeo()) return false; }
            else if (!geo.equals(other.getGeo())) { return false; }
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
