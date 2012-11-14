package net.exathunk.jsubschema.base;

import java.util.List;
import java.util.Map;

/**
 * charolastra 11/13/12 10:22 PM
 */
public class Schema {

    public String type;

    public String description;

    public Boolean required;

    public String id;

    public String ref;

    public String format;

    public List<String> requires;

    public List<String> forbids;

    public Map<String, Schema> properties;

    @Override
    public String toString() {
        return "Schema{" +
                "type='" + type + '\'' +
                ", description='" + description + '\'' +
                ", required=" + required +
                ", id='" + id + '\'' +
                ", ref='" + ref + '\'' +
                ", format='" + format + '\'' +
                ", requires=" + requires +
                ", forbids=" + forbids +
                ", properties=" + properties +
                '}';
    }
}
