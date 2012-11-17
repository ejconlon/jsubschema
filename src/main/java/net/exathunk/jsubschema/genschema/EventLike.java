package net.exathunk.jsubschema.genschema;

import java.util.List;
import java.util.Map;

public interface EventLike {

    boolean hasDtstart();
    String getDtstart();
    void setDtstart(String dtstart);
    boolean hasDtend();
    String getDtend();
    void setDtend(String dtend);
    boolean hasSummary();
    String getSummary();
    void setSummary(String summary);
    boolean hasLocation();
    String getLocation();
    void setLocation(String location);
    boolean hasUrl();
    String getUrl();
    void setUrl(String url);
    boolean hasDuration();
    String getDuration();
    void setDuration(String duration);
    boolean hasRdate();
    String getRdate();
    void setRdate(String rdate);
    boolean hasRrule();
    String getRrule();
    void setRrule(String rrule);
    boolean hasCategory();
    String getCategory();
    void setCategory(String category);
    boolean hasDescription();
    String getDescription();
    void setDescription(String description);
    boolean hasGeo();
    Geo getGeo();
    void setGeo(Geo geo);
}
